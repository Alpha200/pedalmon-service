package eu.sendzik.pedalmon.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.locationtech.jts.geom.LineString
import java.util.UUID

@Entity(name = "tour")
class Tour(
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	var id: UUID? = null,

	@Column(name = "track", columnDefinition = "geography")
	var track: LineString,

	@OneToMany(mappedBy = "tour", cascade = [CascadeType.ALL], orphanRemoval = true)
	var trackPoints: MutableList<TrackPoint>,
)
