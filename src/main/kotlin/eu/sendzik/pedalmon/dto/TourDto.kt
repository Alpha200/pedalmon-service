package eu.sendzik.pedalmon.dto

import java.time.ZonedDateTime
import java.util.UUID

data class TourDto (
	val id: UUID? = null,
	val name: String,
	val date: ZonedDateTime,
	val averageSpeedKmh: Double,
	val averageHeartRateBpm: Int?,
	val distanceMeter: Int,
	val track: MutableList<TrackPointDto>,
	val segmentRecords: MutableList<SegmentRecordDto>,
)
