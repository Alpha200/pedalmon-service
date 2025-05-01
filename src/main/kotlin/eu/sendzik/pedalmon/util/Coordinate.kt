package eu.sendzik.pedalmon.util

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.CoordinateSequence
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory

fun Coordinate.toCoordinateSequence(): CoordinateSequence {
	return CoordinateArraySequenceFactory.instance().create(arrayOf(this))
}
