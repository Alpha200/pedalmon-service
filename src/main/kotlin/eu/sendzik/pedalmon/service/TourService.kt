package eu.sendzik.pedalmon.service

import eu.sendzik.pedalmon.model.Tour
import eu.sendzik.pedalmon.repository.TourRepository
import eu.sendzik.pedalmon.dto.CustomPage
import eu.sendzik.pedalmon.dto.TourDto
import eu.sendzik.pedalmon.dto.TrainingCenterDatabaseDto
import eu.sendzik.pedalmon.dto.toCustomPage
import eu.sendzik.pedalmon.mapper.toDto
import eu.sendzik.pedalmon.mapper.toEntity
import eu.sendzik.pedalmon.model.TrackPoint
import eu.sendzik.pedalmon.util.defaultGeometryFactory
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.LineString
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class TourService(
	private val tourRepository: TourRepository,
	private val segmentRecordService: SegmentRecordService
) {
	fun createTour(tourDto: TourDto): TourDto {
		val tour = tourDto.toEntity()
		return tourRepository.save(tour).toDto()
	}

	fun importTcx(tcx: TrainingCenterDatabaseDto): TourDto {
		var tour = tourRepository.save(
			Tour(
				track = getTrackFromTcx(tcx),
				trackPoints = mutableListOf(),
				segmentRecords = mutableListOf(),
			)
		)

		tour.trackPoints = getTrackPointsFromTcx(tcx).toMutableList()
		tour.trackPoints.forEach { it.tour = tour }
		tour = tourRepository.save(tour)

		tour.segmentRecords = segmentRecordService
			.determineSegmentRecordsForTour(tour)
			.toMutableList()
		tour.segmentRecords.forEach { it.tour = tour }

		tour = tourRepository.save(tour)
		return tour.toDto()
	}

	private fun getTrackPointsFromTcx(tcx: TrainingCenterDatabaseDto): List<TrackPoint> {
		return tcx.activities.activityList.flatMap { activity ->
			activity.laps.flatMap { lap ->
				lap.tracks.first().trackpoints.map { trackPoint ->
					TrackPoint(
						latitude = trackPoint.position.latitudeDegrees,
						longitude = trackPoint.position.longitudeDegrees,
						time = trackPoint.time,
						heartRateBpm = trackPoint.heartRateBpm?.value,
					)
				}
			}

		}
	}

	private fun getTrackFromTcx(tcx: TrainingCenterDatabaseDto): LineString {
		val coordinates = tcx.activities.activityList.flatMap { activity ->
			activity.laps.flatMap { lap ->
				lap.tracks.first().trackpoints.map { trackPoint ->
					Coordinate(
						trackPoint.position.longitudeDegrees,
						trackPoint.position.latitudeDegrees
					)
				}
			}
		}

		return LineString(
			CoordinateArraySequenceFactory
				.instance()
				.create(coordinates.toTypedArray()),
			defaultGeometryFactory
		)
	}

	fun getTours(pageable: Pageable): CustomPage<TourDto> {
		return tourRepository.findAll(pageable).toCustomPage().mapTo { it.toDto() }
	}

	fun getTour(id: UUID): TourDto {
		return tourRepository
			.findById(id)
			.map { tour -> tour.toDto() }
			.orElseThrow()
	}
}
