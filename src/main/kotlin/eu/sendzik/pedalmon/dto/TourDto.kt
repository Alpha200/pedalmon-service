package eu.sendzik.pedalmon.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.UUID

data class TourDto (
	val id: UUID? = null,
	@JsonFormat(shape = JsonFormat.Shape.ARRAY)
	val track: List<Pair<Double, Double>>
)
