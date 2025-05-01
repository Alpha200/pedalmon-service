package eu.sendzik.pedalmon.dto

import java.util.UUID

data class TourDto (
	val id: UUID? = null,
	val track: MutableList<TrackPointDto>,
)
