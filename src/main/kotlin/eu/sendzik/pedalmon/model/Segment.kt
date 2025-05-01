package eu.sendzik.pedalmon.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.locationtech.jts.geom.LineString
import org.locationtech.jts.geom.Point
import java.util.UUID

@Entity(name = "segment")
class Segment(
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	var id: UUID? = null,

	var name: String,

	@Column(name = "point_start", columnDefinition = "geography")
	var pointStart: Point,

	@Column(name = "point_end", columnDefinition = "geography")
	var pointEnd: Point,

	@Column(name = "path", columnDefinition = "geography")
	var path: LineString,
)
