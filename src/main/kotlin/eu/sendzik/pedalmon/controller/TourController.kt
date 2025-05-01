package eu.sendzik.pedalmon.controller

import eu.sendzik.pedalmon.service.TourService
import eu.sendzik.pedalmon.dto.CustomPage
import eu.sendzik.pedalmon.dto.TourDto
import eu.sendzik.pedalmon.dto.TrainingCenterDatabaseDto
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.data.domain.Pageable

@RestController
@RequestMapping("/tours")
class TourController(
	private val tourService: TourService,
) {
	@PostMapping()
	fun createTour(@RequestBody @Valid tour: TourDto): TourDto {
		return tourService.createTour(tour)
	}

	@PostMapping("/import/tcx")
	fun importTour(@RequestBody tcx: TrainingCenterDatabaseDto): TourDto {
		return tourService.importTcx(tcx)
	}

	@GetMapping()
	fun getTours(pageable: Pageable): CustomPage<TourDto> {
		return tourService.getTours(pageable)
	}
}
