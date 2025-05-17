import eu.sendzik.pedalmon.model.TrackPoint
import org.geotools.api.referencing.crs.CoordinateReferenceSystem
import org.geotools.api.referencing.operation.MathTransform
import org.geotools.geometry.jts.JTS
import org.geotools.referencing.CRS
import org.locationtech.jts.geom.LineString
import org.locationtech.jts.geom.Point
import org.locationtech.jts.linearref.LengthIndexedLine
import java.time.Duration
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.math.cos
import kotlin.math.sqrt

// Method for quick distance calculation
fun calculateDistanceMeter(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
	val lat1Rad = Math.toRadians(lat1)
	val lat2Rad = Math.toRadians(lat2)
	val lon1Rad = Math.toRadians(lon1)
	val lon2Rad = Math.toRadians(lon2)

	val x = (lon2Rad - lon1Rad) * cos((lat1Rad + lat2Rad) / 2)
	val y = (lat2Rad - lat1Rad)
	val distance: Double = sqrt(x * x + y * y) * 6371000

	return distance
}

fun lengthInMeters(lineString: LineString): Double {
	// Source CRS: WGS84 (EPSG:4326)
	val sourceCRS: CoordinateReferenceSystem = CRS.decode("EPSG:4326")
	// Target CRS: Web Mercator (EPSG:3857)
	val targetCRS: CoordinateReferenceSystem = CRS.decode("EPSG:3857")
	val transform: MathTransform = CRS.findMathTransform(sourceCRS, targetCRS, true)
	val projected = JTS.transform(lineString, transform) as LineString
	return projected.length
}

fun projectTo3857(geom: org.locationtech.jts.geom.Geometry): org.locationtech.jts.geom.Geometry {
	val sourceCRS = CRS.decode("EPSG:4326")
	val targetCRS = CRS.decode("EPSG:3857")
	val transform = CRS.findMathTransform(sourceCRS, targetCRS, true)
	return JTS.transform(geom, transform)
}

fun distanceAlongLineInMeters(line: LineString, point: Point): Double {
	val projectedLine = projectTo3857(line) as LineString
	val projectedPoint = projectTo3857(point) as Point
	val indexedLine = LengthIndexedLine(projectedLine)
	return indexedLine.project(projectedPoint.coordinate)
}

fun getAverageSpeedKmh(trackPoints: List<TrackPoint>): Double {
	val distanceM = trackPoints.windowed(2).sumOf { (tpA, tpB) ->
		calculateDistanceMeter(
			tpA.latitude,
			tpA.longitude,
			tpB.latitude,
			tpB.longitude
		)
	}

	val timeTakenS = Duration.between(trackPoints.first().time, trackPoints.last().time).toSeconds()
	return (distanceM / 1000.0) / (timeTakenS / 3600.0)
}
