package eu.sendzik.pedalmon.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

data class TrackPointDto(
	@JsonProperty("lat")
	var latitude: Double,
	@JsonProperty("lon")
	var longitude: Double,
	@JsonProperty("ts")
	var time: ZonedDateTime,
	@JsonProperty("hr")
	var heartRateBpm: Int?,
	@JsonProperty("el")
	var elevation: Double?,
)
