package eu.sendzik.pedalmon.mapper

import eu.sendzik.pedalmon.model.Tour
import eu.sendzik.pedalmon.dto.TourDto
import eu.sendzik.pedalmon.util.defaultGeometryFactory
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.LineString
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory

fun TourDto.toEntity(): Tour {
	return Tour(
		id = id,
		track = LineString(
			CoordinateArraySequenceFactory
				.instance()
				.create(track.map {Coordinate(it.first, it.second) }.toTypedArray()),
			defaultGeometryFactory
		)
	)
}

fun Tour.toDto(): TourDto {
	return TourDto(
		id = id,
		track = track.coordinates.map {
			Pair(it.x, it.y)
		}
	)
}
