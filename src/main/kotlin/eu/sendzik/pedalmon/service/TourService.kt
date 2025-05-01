package eu.sendzik.pedalmon.service

import eu.sendzik.pedalmon.model.Tour
import eu.sendzik.pedalmon.repository.TourRepository
import eu.sendzik.pedalmon.dto.CustomPage
import eu.sendzik.pedalmon.dto.TourDto
import eu.sendzik.pedalmon.dto.TrainingCenterDatabaseDto
import eu.sendzik.pedalmon.dto.toCustomPage
import eu.sendzik.pedalmon.mapper.toDto
import eu.sendzik.pedalmon.mapper.toEntity
import eu.sendzik.pedalmon.util.defaultGeometryFactory
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.LineString
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TourService(
	private val tourRepository: TourRepository,
) {
	fun createTour(tourDto: TourDto): TourDto {
		val tour = tourDto.toEntity()
		return tourRepository.save(tour).toDto()
	}

	fun importTcx(tcx: TrainingCenterDatabaseDto): TourDto {
		val tour = Tour(
			track = getTrackFromTcx(tcx)
		)

		return tourRepository.save(tour).toDto()
	}

	private fun getTrackFromTcx(tcx: TrainingCenterDatabaseDto): LineString {
		val coordinates = tcx.activities.activityList.flatMap { activity ->
			activity.laps.flatMap { lap ->
				lap.tracks.first().trackpoints.map { trackPoint ->
					Coordinate(
						trackPoint.position.longitudeDegrees,
						trackPoint.position.latitudeDegrees
					)
				}
			}
		}

		return LineString(
			CoordinateArraySequenceFactory
				.instance()
				.create(coordinates.toTypedArray()),
			defaultGeometryFactory
		)
	}

	fun getTours(pageable: Pageable): CustomPage<TourDto> {
		return tourRepository.findAll(pageable).toCustomPage().mapTo { it.toDto() }
	}
}
