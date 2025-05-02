package eu.sendzik.pedalmon.mapper

import eu.sendzik.pedalmon.dto.SegmentDto
import eu.sendzik.pedalmon.model.Segment
import eu.sendzik.pedalmon.util.defaultGeometryFactory
import eu.sendzik.pedalmon.util.toCoordinateSequence
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.LineString
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory

fun SegmentDto.toEntity(): Segment {
	return Segment(
		id = id,
		pointStart = Point(
			Coordinate(path.first().first, path.first().second).toCoordinateSequence(),
			defaultGeometryFactory
		),
		pointEnd = Point(
			Coordinate(path.last().first, path.last().second).toCoordinateSequence(),
			defaultGeometryFactory
		),
		path = LineString(
			CoordinateArraySequenceFactory
				.instance()
				.create(path.map {Coordinate(it.first, it.second) }.toTypedArray()),
			defaultGeometryFactory
		),
		name = name,
	)
}

fun Segment.toDto(): SegmentDto {
	return SegmentDto(
		id = id,
		path = path.coordinates.map {
			Pair(it.x, it.y)
		},
		name = name,
	)
}
