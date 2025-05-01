package eu.sendzik.pedalmon.dto

import java.time.ZonedDateTime
import java.util.UUID

data class SegmentRecordDto(
	var id: UUID? = null,
	var tour: TourDto? = null,
	var segment: SegmentDto? = null,
	var speedKmh: Double,
	var durationS: Int,
	var time: ZonedDateTime,
)
