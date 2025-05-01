package eu.sendzik.pedalmon.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.UUID

data class SegmentDto(
	var id: UUID? = null,
	@JsonFormat(shape = JsonFormat.Shape.ARRAY)
	var startPoint: Pair<Double, Double>,
	@JsonFormat(shape = JsonFormat.Shape.ARRAY)
	var endPoint: Pair<Double, Double>,
	@JsonFormat(shape = JsonFormat.Shape.ARRAY)
	var path: List<Pair<Double, Double>>,
)
