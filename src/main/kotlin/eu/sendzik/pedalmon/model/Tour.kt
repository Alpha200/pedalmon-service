package eu.sendzik.pedalmon.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.locationtech.jts.geom.LineString
import java.util.UUID

@Entity(name = "tour")
class Tour(
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	var id: UUID? = null,

	@Column(name = "track", columnDefinition = "geography")
	var track: LineString
)
