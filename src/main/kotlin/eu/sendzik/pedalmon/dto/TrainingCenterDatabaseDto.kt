package eu.sendzik.pedalmon.dto

import java.time.ZonedDateTime
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class TrainingCenterDatabaseDto(
	@JacksonXmlProperty(localName = "Activities")
	val activities: Activities
)

data class Activities(
	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "Activity")
	val activityList: List<Activity>
)

data class Activity(
	@JacksonXmlProperty(isAttribute = true, localName = "Sport")
	val sport: String,

	@JacksonXmlProperty(localName = "Id")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
	val id: ZonedDateTime,

	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "Lap")
	val laps: List<Lap>
)

data class Lap(
	@JacksonXmlProperty(isAttribute = true, localName = "StartTime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
	val startTime: ZonedDateTime,

	@JacksonXmlProperty(localName = "TotalTimeSeconds")
	val totalTimeSeconds: Double,

	@JacksonXmlProperty(localName = "DistanceMeters")
	val distanceMeters: Double,

	@JacksonXmlProperty(localName = "MaximumSpeed")
	val maximumSpeed: Double,

	@JacksonXmlProperty(localName = "Calories")
	val calories: Int,

	@JacksonXmlProperty(localName = "AverageHeartRateBpm")
	val averageHeartRateBpm: HeartRate,

	@JacksonXmlProperty(localName = "MaximumHeartRateBpm")
	val maximumHeartRateBpm: HeartRate,

	@JacksonXmlProperty(localName = "Intensity")
	val intensity: String,

	@JacksonXmlProperty(localName = "Cadence")
	val cadence: Int,

	@JacksonXmlProperty(localName = "TriggerMethod")
	val triggerMethod: String,

	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "Track")
	val tracks: List<Track>
)

data class Track(
	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "Trackpoint")
	val trackpoints: List<Trackpoint>
)

data class Trackpoint(
	@JacksonXmlProperty(localName = "Time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
	val time: ZonedDateTime,

	@JacksonXmlProperty(localName = "Position")
	val position: Position?,

	@JacksonXmlProperty(localName = "AltitudeMeters")
	val altitudeMeters: Double,

	@JacksonXmlProperty(localName = "DistanceMeters")
	val distanceMeters: Double,

	@JacksonXmlProperty(localName = "HeartRateBpm")
	val heartRateBpm: HeartRate?,

	@JacksonXmlProperty(localName = "Cadence")
	val cadence: Int,
)

data class Position(
	@JacksonXmlProperty(localName = "LatitudeDegrees")
	val latitudeDegrees: Double,

	@JacksonXmlProperty(localName = "LongitudeDegrees")
	val longitudeDegrees: Double
)

data class HeartRate(
	@JacksonXmlProperty(localName = "Value")
	val value: Int
)

data class TPX(
	@JacksonXmlProperty(localName = "Speed")
	val speed: Double
)
