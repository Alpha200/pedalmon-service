package eu.sendzik.pedalmon.service

import LiveSegmentProgress
import LiveSegmentRequestResultDto
import LiveSegmentResult
import distanceAlongLineInMeters
import eu.sendzik.pedalmon.dto.GeoPosition
import eu.sendzik.pedalmon.model.LiveSegment
import eu.sendzik.pedalmon.model.Segment
import eu.sendzik.pedalmon.model.SegmentRecord
import eu.sendzik.pedalmon.repository.LiveSegmentRepository
import eu.sendzik.pedalmon.repository.SegmentRecordRepository
import eu.sendzik.pedalmon.repository.SegmentRepository
import eu.sendzik.pedalmon.util.defaultGeometryFactory
import lengthInMeters
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Point
import org.locationtech.jts.linearref.LengthIndexedLine
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import projectTo3857
import java.time.Duration
import java.time.ZonedDateTime
import java.util.UUID
import kotlin.math.roundToInt

@Service
class LiveSegmentService(
	private val liveSegmentRepository: LiveSegmentRepository,
	private val segmentRecordRepository: SegmentRecordRepository,
	private val segmentRepository: SegmentRepository,
) {
	fun submitPosition(
		position: GeoPosition,
		date: ZonedDateTime,
		userId: UUID
	): LiveSegmentRequestResultDto {
		val currentLiveSegment = liveSegmentRepository.findByIdOrNull(userId)

		return if (currentLiveSegment == null) {
			val segment = segmentRepository.findSegmentsWithStart(
				position.x,
				position.y,
				PageRequest.of(0, 1)
			).firstOrNull()

			if (segment != null) {
				// Found a segment start
				startLiveSegment(userId, segment, date)
			} else {
				// No active segment
				LiveSegmentRequestResultDto(
					status = LiveSegmentStatus.INACTIVE,
					liveSegmentProgress = null,
					liveSegmentResult = null,
				)
			}
		} else {
			val reachedEnd = segmentRepository.findSegmentsWithEnd(
				position.x,
				position.y,
				PageRequest.of(0, 1)
			).firstOrNull()?.id == currentLiveSegment.segmentId

			val segmentRecord = getBestSegmentRecord(currentLiveSegment.segmentId)

			if (reachedEnd) {
				finishLiveSegment(date, currentLiveSegment, segmentRecord)
			} else {
				return updateOrAbortLiveSegment(position, currentLiveSegment, date)
			}
		}
	}

	private fun updateOrAbortLiveSegment(
		position: GeoPosition,
		currentLiveSegment: LiveSegment,
		date: ZonedDateTime
	): LiveSegmentRequestResultDto {
		val segment = segmentRepository.findSegmentsThatIntersect(
			position.x,
			position.y,
			PageRequest.of(0, 1)
		).firstOrNull()

		return if (segment != null && segment.id == currentLiveSegment.segmentId) {
			val distanceMeters = distanceAlongLineInMeters(
				segment.path,
				defaultGeometryFactory.createPoint(Coordinate(position.x, position.y))
			)
			val segmentRecord = getBestSegmentRecord(currentLiveSegment.segmentId)

			currentLiveSegment.lastTimestamp = date
			liveSegmentRepository.save(currentLiveSegment)

			LiveSegmentRequestResultDto(
				status = LiveSegmentStatus.TRACKING,
				liveSegmentProgress = LiveSegmentProgress(
					segmentId = currentLiveSegment.segmentId,
					timeElapsedSeconds = Duration.between(currentLiveSegment.startTimestamp, date).toMillis() / 1000.0,
					distanceMeters = distanceMeters.roundToInt(),
					distanceMetersBest = 0,
					distanceMetersTotal = lengthInMeters(segment.path).roundToInt(),
				),
				liveSegmentResult = null,
			)
		} else {
			liveSegmentRepository.delete(currentLiveSegment)
			LiveSegmentRequestResultDto(
				status = LiveSegmentStatus.FAILED,
				liveSegmentProgress = null,
				liveSegmentResult = null,
			)
		}
	}

	private fun finishLiveSegment(
		date: ZonedDateTime,
		currentLiveSegment: LiveSegment,
		segmentRecord: SegmentRecord
	): LiveSegmentRequestResultDto {
		val elapsedTotal = Duration.between(currentLiveSegment.startTimestamp, date).toSeconds().toInt()
		val rank = segmentRecordRepository.getRankForSegmentAndDuration(
			currentLiveSegment.segmentId,
			elapsedTotal
		) + 1
		val result = LiveSegmentRequestResultDto(
			status = LiveSegmentStatus.FINISHED,
			liveSegmentProgress = null,
			liveSegmentResult = LiveSegmentResult(
				timeElapsedTotalSeconds = elapsedTotal,
				timeElapsedBestSeconds = segmentRecord.durationS,
				personalRank = rank
			),
		)

		liveSegmentRepository.delete(currentLiveSegment)

		return result
	}

	private fun startLiveSegment(userId: UUID, segment: Segment, date: ZonedDateTime): LiveSegmentRequestResultDto {
		val liveSegment = LiveSegment(
			userId = userId,
			segmentId = segment.id!!,
			startTimestamp = date,
			lastTimestamp = date,
		)
		liveSegmentRepository.save(liveSegment)

		return LiveSegmentRequestResultDto(
			status = LiveSegmentStatus.TRACKING,
			liveSegmentProgress = LiveSegmentProgress(
				segmentId = segment.id!!,
				timeElapsedSeconds = 0.0,
				distanceMeters = 0,
				distanceMetersBest = 0,
				distanceMetersTotal = lengthInMeters(segment.path).roundToInt(),
			),
			liveSegmentResult = null,
		)
	}

	private fun getBestSegmentRecord(segmentId: UUID): SegmentRecord {
		return segmentRecordRepository.getSegmentRecordsBySegmentIdOrderByDurationSAscSpeedKmhDesc(
			segmentId,
			PageRequest.of(0, 1)
		).first()
	}
}
