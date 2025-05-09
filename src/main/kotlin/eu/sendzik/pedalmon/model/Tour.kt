package eu.sendzik.pedalmon.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.locationtech.jts.geom.LineString
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "tour")
class Tour(
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	var id: UUID? = null,

	@Column(name = "name")
	var name: String,

	@Column(name = "date")
	var date: ZonedDateTime,

	@Column(name = "average_speed_kmh")
	var averageSpeedKmh: Double,

	@Column(name = "average_heart_rate_bpm")
	var averageHeartRateBpm: Int?,

	@Column(name = "distance_m")
	var distanceMeter: Int,

	@Column(name = "track", columnDefinition = "geography")
	var track: LineString,

	@OneToMany(mappedBy = "tour", cascade = [CascadeType.ALL], orphanRemoval = true)
	var trackPoints: MutableList<TrackPoint>,

	@OneToMany(mappedBy = "tour", cascade = [CascadeType.ALL])
	var segmentRecords: MutableList<SegmentRecord>,

	@Column(name = "user_id")
	var userId: UUID?,
)
