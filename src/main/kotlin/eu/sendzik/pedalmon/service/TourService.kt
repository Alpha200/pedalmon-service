package eu.sendzik.pedalmon.service

import Gpx
import eu.sendzik.pedalmon.component.PedalmonSec
import eu.sendzik.pedalmon.model.Tour
import eu.sendzik.pedalmon.repository.TourRepository
import eu.sendzik.pedalmon.dto.CustomPage
import eu.sendzik.pedalmon.dto.TourDto
import eu.sendzik.pedalmon.dto.TrainingCenterDatabaseDto
import eu.sendzik.pedalmon.dto.toCustomPage
import eu.sendzik.pedalmon.mapper.toDto
import eu.sendzik.pedalmon.mapper.toEntity
import eu.sendzik.pedalmon.model.TrackPoint
import eu.sendzik.pedalmon.repository.SegmentRecordRepository
import eu.sendzik.pedalmon.util.defaultGeometryFactory
import getAverageSpeedKmh
import jakarta.transaction.Transactional
import lengthInMeters
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.LineString
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.math.roundToInt

@Service
@Transactional
class TourService(
	private val tourRepository: TourRepository,
	private val segmentRecordService: SegmentRecordService,
	private val segmentRecordRepository: SegmentRecordRepository,
	private val pedalmonSec: PedalmonSec,
) {
	fun createTour(tourDto: TourDto): TourDto {
		val tour = tourDto.toEntity()
		return tourRepository.save(tour).toDto()
	}

	fun importTcx(tcx: TrainingCenterDatabaseDto): TourDto {
		val trackPoints = getTrackPointsFromTcx(tcx)
		val averageHeartRate = trackPoints.mapNotNull { it.heartRateBpm }.average()
		return importTourWithTrackPoints(trackPoints, averageHeartRate).toDto()
	}

	fun importGpx(gpx: Gpx): TourDto {
		val trackPoints = getTrackPointsFromGpx(gpx)
		return importTourWithTrackPoints(trackPoints, null).toDto()
	}

	fun getTours(filterIds: List<UUID>?, pageable: Pageable): CustomPage<TourDto> {
		return tourRepository.findAllByOrderByDateDesc(
			filterIds,
			pageable
		).toCustomPage().mapTo { it.toDto() }
	}

	fun getTour(id: UUID): TourDto {
		return tourRepository
			.findById(id)
			.map { tour -> tour.toDto() }
			.orElseThrow()
	}

	private fun importTourWithTrackPoints(
		trackPoints: List<TrackPoint>,
		averageHeartRate: Double?
	): Tour {
		val tourDate = trackPoints.first().time
		val averageHR = if (averageHeartRate != null && averageHeartRate.isFinite()) {
			averageHeartRate.roundToInt()
		} else {
			null
		}

		val track = getTrackFromTrackPoints(trackPoints)

		var tour = tourRepository.save(
			Tour(
				name = "Tour", // TODO: Generate name by start and end point city name
				track = track,
				trackPoints = mutableListOf(),
				segmentRecords = mutableListOf(),
				averageSpeedKmh = getAverageSpeedKmh(trackPoints),
				averageHeartRateBpm = averageHR,
				distanceMeter = lengthInMeters(track).roundToInt(),
				date = tourDate,
				userId = pedalmonSec.userId
			)
		)

		trackPoints.forEach { it.tour = tour }
		tour.trackPoints = trackPoints.toMutableList()
		tour = tourRepository.save(tour)

		tour.segmentRecords = segmentRecordService
			.determineSegmentRecordsForTour(tour)
			.toMutableList()
		tour.segmentRecords.forEach { it.tour = tour }

		tour = tourRepository.save(tour)

		for (segmentRecord in tour.segmentRecords) {
			val count = segmentRecordRepository.getSegmentRecordCount(
				segmentRecord.segment?.id!!
			)

			if (count > 1) {
				segmentRecord.rankCreated = segmentRecordRepository.getRankForSegmentRecord(
					segmentRecord.id!!,
					segmentRecord.segment?.id!!
				)
			}

			segmentRecordRepository.save(segmentRecord)
		}
		return tour
	}

	private fun getTrackPointsFromTcx(tcx: TrainingCenterDatabaseDto): List<TrackPoint> {
		return tcx.activities.activityList.flatMap { activity ->
			activity.laps.flatMap { lap ->
				lap.tracks.first().trackpoints
					.filter { trackPoint -> trackPoint.position != null }
					.map { trackPoint ->
					TrackPoint(
						latitude = trackPoint.position!!.latitudeDegrees,
						longitude = trackPoint.position.longitudeDegrees,
						time = trackPoint.time,
						heartRateBpm = trackPoint.heartRateBpm?.value,
						elevation = trackPoint.altitudeMeters,
					)
				}
			}

		}
	}

	private fun getTrackPointsFromGpx(gpx: Gpx): List<TrackPoint> {
		return gpx.trk.trkseg.trkpt.map { trackPoint ->
			TrackPoint(
				latitude = trackPoint.lat,
				longitude = trackPoint.lon,
				time = trackPoint.time,
				heartRateBpm = null,
				elevation = trackPoint.ele,
			)
		}
	}

	private fun getTrackFromTrackPoints(trackPoints: List<TrackPoint>): LineString {
		val coordinates = trackPoints.map { trackPoint ->
			Coordinate(
				trackPoint.longitude,
				trackPoint.latitude,
			)
		}

		return LineString(
			CoordinateArraySequenceFactory
				.instance()
				.create(coordinates.toTypedArray()),
			defaultGeometryFactory
		)
	}

	fun getToursByBounds(xMin: Double, yMin: Double, xMax: Double, yMax: Double): List<UUID> {
		return tourRepository.findByBounds(xMin, yMin, xMax, yMax, PageRequest.of(0, 20))
	}
}
