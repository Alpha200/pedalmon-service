package eu.sendzik.pedalmon.service

import calculateDistanceMeter
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

		for (trackPoint: TrackPoint in tour.trackPoints) {
			if (startTrackPoint == null) {
				if(calculateDistanceMeter(
						trackPoint.latitude,
						trackPoint.longitude,
						segment.pointStart.y,
						segment.pointStart.x
					) <= 10) {
					startTrackPoint = trackPoint
				}
			} else {
				if(calculateDistanceMeter(
						trackPoint.latitude,
						trackPoint.longitude,
						segment.pointEnd.y,
						segment.pointEnd.x
					) <= 10) {
					val duration = Duration.between(startTrackPoint.time, trackPoint.time).toSeconds()

					result.add(
						SegmentRecord(
							speedKmh = 0.0,
							durationS = duration.toInt(),
							time = startTrackPoint.time,
							segment = segment,
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
}
