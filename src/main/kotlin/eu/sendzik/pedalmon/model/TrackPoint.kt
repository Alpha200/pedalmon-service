package eu.sendzik.pedalmon.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.ZonedDateTime

@Entity
class TrackPoint(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tourId", nullable = false)
	var tour: Tour? = null,
	@Column(name = "latitude")
	var latitude: Double,
	@Column(name = "longitude")
	var longitude: Double,
	@Column(name = "time")
	var time: ZonedDateTime,
	@Column(name = "heart_rate_bpm")
	var heartRateBpm: Int?,
)
