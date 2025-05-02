package eu.sendzik.pedalmon.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "segment_record")
class SegmentRecord(
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	var id: UUID? = null,
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tourId", nullable = false)
	var tour: Tour? = null,
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "segmentId", nullable = false)
	var segment: Segment? = null,
	@Column(name = "speed_kmh")
	var speedKmh: Double,
	@Column(name = "duration_s")
	var durationS: Int,
	@Column(name = "time")
	var time: ZonedDateTime,
	@Column(name = "rank_created")
	var rankCreated: Int?,
)
