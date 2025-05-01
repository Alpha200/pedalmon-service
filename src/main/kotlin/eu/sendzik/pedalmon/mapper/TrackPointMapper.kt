package eu.sendzik.pedalmon.mapper

import eu.sendzik.pedalmon.dto.TrackPointDto
import eu.sendzik.pedalmon.model.TrackPoint

fun TrackPointDto.toEntity(): TrackPoint {
	return TrackPoint(
		latitude = latitude,
		longitude = longitude,
		time = time,
		heartRateBpm = heartRateBpm,
	)
}

fun TrackPoint.toDto(): TrackPointDto {
	return TrackPointDto(
		latitude = latitude,
		longitude = longitude,
		time = time,
		heartRateBpm = heartRateBpm,
	)
}
