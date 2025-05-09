package eu.sendzik.pedalmon.service

import calculateDistanceMeter
import eu.sendzik.pedalmon.component.PedalmonSec
import eu.sendzik.pedalmon.dto.CustomPage
import eu.sendzik.pedalmon.dto.SegmentRecordDto
import eu.sendzik.pedalmon.dto.toCustomPage
import eu.sendzik.pedalmon.mapper.toDto
import eu.sendzik.pedalmon.model.Segment
import eu.sendzik.pedalmon.model.SegmentRecord
import eu.sendzik.pedalmon.model.Tour
import eu.sendzik.pedalmon.model.TrackPoint
import eu.sendzik.pedalmon.repository.SegmentRecordRepository
import eu.sendzik.pedalmon.repository.SegmentRepository
import getAverageSpeedKmh
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.UUID

@Service
@Transactional
class SegmentRecordService(
	private val segmentRecordRepository: SegmentRecordRepository,
	private val segmentRepository: SegmentRepository,
	private val pedalmonSec: PedalmonSec,
) {
	fun getSegmentRecords(pageable: Pageable): CustomPage<SegmentRecordDto> {
		return segmentRecordRepository.findAll(pageable).toCustomPage().mapTo { it.toDto() }
	}

	fun determineSegmentRecordsForTour(tour: Tour): List<SegmentRecord> {
		val segments = getSegmentsForTour(tour.id!!)
		return segments.flatMap { getRecordsForSegment(it, tour) }
	}

	private fun getRecordsForSegment(
		segment: Segment,
		tour: Tour
	): List<SegmentRecord> {
		var result = mutableListOf<SegmentRecord>()
		var startTrackPoint: TrackPoint? = null
		var startIndex = 0

		tour.trackPoints.forEachIndexed { index, trackPoint ->
			if (startTrackPoint == null) {
				if(calculateDistanceMeter(
						trackPoint.latitude,
						trackPoint.longitude,
						segment.pointStart.y,
						segment.pointStart.x
					) <= 10) {
					startTrackPoint = trackPoint
					startIndex = index
				}
			} else {
				if(calculateDistanceMeter(
						trackPoint.latitude,
						trackPoint.longitude,
						segment.pointEnd.y,
						segment.pointEnd.x
					) <= 10) {
					val duration = Duration.between(startTrackPoint!!.time, trackPoint.time).toSeconds()
					val speed = getAverageSpeedKmh(tour.trackPoints.slice(startIndex until index + 1))

					result.add(
						SegmentRecord(
							speedKmh = speed,
							durationS = duration.toInt(),
							time = startTrackPoint!!.time,
							segment = segment,
							rankCreated = null,
							userId = pedalmonSec.getUserId(),
						)
					)
					startTrackPoint = null
				}
			}
		}

		return result
	}

	private fun getSegmentsForTour(tourId: UUID): List<Segment> {
		return segmentRepository.findSegmentsByTourId(tourId)
	}

	fun getSegmentRecordsForSegment(segmentId: UUID, pageable: Pageable): CustomPage<SegmentRecordDto> {
		return segmentRecordRepository
			.getSegmentRecordsBySegmentIdOrderByDurationSAscSpeedKmhDesc(segmentId, pageable)
			.toCustomPage()
			.mapTo { it.toDto() }
	}
}
