package eu.sendzik.pedalmon.mapper

import eu.sendzik.pedalmon.model.Tour
import eu.sendzik.pedalmon.dto.TourDto
import eu.sendzik.pedalmon.util.defaultGeometryFactory
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.LineString
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory

fun TourDto.toEntity(): Tour {
	val tour = Tour(
		id = id,
		track = LineString(
			CoordinateArraySequenceFactory
				.instance()
				.create(track.map { Coordinate(it.latitude, it.longitude) }.toTypedArray()),
			defaultGeometryFactory
		),
		trackPoints = track.map { it.toEntity() }.toMutableList(),
		segmentRecords = segmentRecords.map { it.toEntity() }.toMutableList(),
	)

	return tour
}

fun Tour.toDto(): TourDto {
	return TourDto(
		id = id,
		track = trackPoints.map { it.toDto() }.toMutableList(),
		segmentRecords = segmentRecords.map { it.toDto() }.toMutableList(),
	)
}
