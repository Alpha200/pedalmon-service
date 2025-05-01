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
			Coordinate(startPoint.first, startPoint.second).toCoordinateSequence(),
			defaultGeometryFactory
		),
		pointEnd = Point(
			Coordinate(endPoint.first, endPoint.second).toCoordinateSequence(),
			defaultGeometryFactory
		),
		path = LineString(
			CoordinateArraySequenceFactory
				.instance()
				.create(path.map {Coordinate(it.first, it.second) }.toTypedArray()),
			defaultGeometryFactory
		)
	)
}

fun Segment.toDto(): SegmentDto {
	return SegmentDto(
		id = id,
		startPoint = Pair(pointStart.y, pointStart.x),
		endPoint = Pair(pointEnd.y, pointEnd.x),
		path = path.coordinates.map {
			Pair(it.x, it.y)
		}
	)
}
