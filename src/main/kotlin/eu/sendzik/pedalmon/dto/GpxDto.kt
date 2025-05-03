import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.time.ZonedDateTime

data class Gpx(
	@JacksonXmlProperty(isAttribute = true)
	val version: String,
	@JacksonXmlProperty(isAttribute = true)
	val creator: String,
	@JacksonXmlProperty(namespace = "http://www.topografix.com/GPX/1/1")
	val metadata: Metadata,
	@JacksonXmlProperty(namespace = "http://www.topografix.com/GPX/1/1")
	val trk: Track
)

data class Metadata(
	val name: String,
	val author: Author
)

data class Author(
	val link: Link
)

data class Link(
	@JacksonXmlProperty(isAttribute = true)
	val href: String,
	val text: String,
	val type: String
)

data class Track(
	val name: String,
	@JacksonXmlElementWrapper(useWrapping = false)
	val trkseg: TrackSegment
)

data class TrackSegment(
	@JacksonXmlElementWrapper(useWrapping = false)
	val trkpt: List<TrackPoint>
)

data class TrackPoint(
	@JacksonXmlProperty(isAttribute = true)
	val lat: Double,
	@JacksonXmlProperty(isAttribute = true)
	val lon: Double,
	val ele: Double,
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	val time: ZonedDateTime
)
