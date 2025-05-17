package eu.sendzik.pedalmon.dto

import java.time.ZonedDateTime

data class LiveSegmentRequestDto(
	val position: GeoPosition,
	val date: ZonedDateTime,
)
