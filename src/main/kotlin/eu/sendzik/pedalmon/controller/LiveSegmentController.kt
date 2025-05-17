package eu.sendzik.pedalmon.controller

import LiveSegmentRequestResultDto
import eu.sendzik.pedalmon.component.PedalmonSec
import eu.sendzik.pedalmon.service.LiveSegmentService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import eu.sendzik.pedalmon.dto.LiveSegmentRequestDto

@RestController
@RequestMapping("/live-segments")
class LiveSegmentController(
	private val liveSegmentService: LiveSegmentService,
	private val pedalmonSec: PedalmonSec,
) {
	@PostMapping("position")
	fun submitPosition(@RequestBody liveSegmentRequestDto: LiveSegmentRequestDto): LiveSegmentRequestResultDto {
		val userId = pedalmonSec.userId
		return liveSegmentService.submitPosition(
			liveSegmentRequestDto.position,
			liveSegmentRequestDto.date,
			userId
		);
	}
}
