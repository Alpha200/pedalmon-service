package eu.sendzik.pedalmon.util

import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel


const val SRID_LATITUDE_LONGITUDE = 4326
val defaultGeometryFactory: GeometryFactory by lazy { GeometryFactory(PrecisionModel(), SRID_LATITUDE_LONGITUDE) }
