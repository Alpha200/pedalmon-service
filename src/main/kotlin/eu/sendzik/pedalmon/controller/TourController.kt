package eu.sendzik.pedalmon.controller

import Gpx
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
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

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
	fun importTourByTcx(@RequestBody tcx: TrainingCenterDatabaseDto): TourDto {
		return tourService.importTcx(tcx)
	}

	@PostMapping("/import/gpx")
	fun importTourByGpx(@RequestBody gpx: Gpx): TourDto {
		return tourService.importGpx(gpx)
	}

	@GetMapping()
	fun getTours(
		@RequestParam("filter.ids") filterIds: List<UUID>?,
		pageable: Pageable,
		): CustomPage<TourDto> {
		return tourService.getTours(filterIds, pageable)
	}

	@GetMapping("{id}")
	fun getTour(@PathVariable id: UUID): TourDto {
		return tourService.getTour(id)
	}

	@GetMapping("bounds/{xMin},{yMin},{xMax},{yMax}/ids")
	fun getTourIdsByBounds(
		@PathVariable xMin: Double,
		@PathVariable yMin: Double,
		@PathVariable xMax: Double,
		@PathVariable yMax: Double,
	): List<UUID> {
		return tourService.getToursByBounds(xMin, yMin, xMax, yMax)
	}
}
