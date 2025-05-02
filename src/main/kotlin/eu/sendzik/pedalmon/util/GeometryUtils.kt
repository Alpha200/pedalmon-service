import eu.sendzik.pedalmon.model.TrackPoint
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

fun getAverageSpeedKmh(trackPoints: List<TrackPoint>): Double = trackPoints
	.windowed(size = 2)
	.map { (a, b) ->
		val distance = calculateDistanceMeter(a.latitude, a.longitude, b.latitude, b.longitude)
		val timeTaken = Duration.between(a.time, b.time).toSeconds()
		(distance / 1000.0) / (timeTaken / 3600.0)
	}.filter { it > 1 }
	.average()
