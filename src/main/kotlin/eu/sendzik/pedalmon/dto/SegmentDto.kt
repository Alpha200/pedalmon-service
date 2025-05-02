package eu.sendzik.pedalmon.dto

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.Size
import java.util.UUID

data class SegmentDto(
	var id: UUID? = null,
	@field:Size(max = 100)
	var name: String,
	@JsonFormat(shape = JsonFormat.Shape.ARRAY)
	var path: List<Pair<Double, Double>>,
)
