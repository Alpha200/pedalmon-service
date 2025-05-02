package eu.sendzik.pedalmon.controller

import eu.sendzik.pedalmon.dto.CustomPage
import eu.sendzik.pedalmon.dto.SegmentDto
import eu.sendzik.pedalmon.dto.SegmentRecordDto
import eu.sendzik.pedalmon.service.SegmentRecordService
import eu.sendzik.pedalmon.service.SegmentService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController()
@RequestMapping("/segments")
class SegmentController(
	private val segmentService: SegmentService,
	private val segmentRecordService: SegmentRecordService,
) {
	@PostMapping()
	fun createSegment(@RequestBody @Valid segment: SegmentDto): SegmentDto {
		return segmentService.createSegment(segment)
	}

	@GetMapping()
	fun getSegments(pageable: Pageable): CustomPage<SegmentDto> {
		return segmentService.getSegments(pageable)
	}

	@GetMapping("{segmentId}")
	fun getSegment(@PathVariable segmentId: UUID): SegmentDto {
		return segmentService.getSegment(segmentId)
	}

	@GetMapping("{segmentId}/records")
	fun getSegmentRecordsForSegment(
		@PathVariable segmentId: UUID,
		pageable: Pageable
	): CustomPage<SegmentRecordDto> {
		return segmentRecordService.getSegmentRecordsForSegment(segmentId, pageable)
	}
}
