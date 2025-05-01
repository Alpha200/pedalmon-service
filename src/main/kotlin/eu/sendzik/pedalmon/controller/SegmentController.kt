package eu.sendzik.pedalmon.controller

import eu.sendzik.pedalmon.dto.CustomPage
import eu.sendzik.pedalmon.dto.SegmentDto
import eu.sendzik.pedalmon.service.SegmentService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/segments")
class SegmentController(
	private val segmentService: SegmentService,
) {
	@PostMapping()
	fun createSegment(@RequestBody segment: SegmentDto): SegmentDto {
		return segmentService.createSegment(segment)
	}

	@GetMapping()
	fun getSegments(pageable: Pageable): CustomPage<SegmentDto> {
		return segmentService.getSegments(pageable)
	}
}
