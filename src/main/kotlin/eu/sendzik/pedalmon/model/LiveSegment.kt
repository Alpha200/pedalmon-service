package eu.sendzik.pedalmon.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "live_segment")
class LiveSegment(
	@Id
	@Column(name = "user_id")
	var userId: UUID,

	@Column(name = "segment_id")
	var segmentId: UUID,

	@Column(name = "start_timestamp")
	var startTimestamp: ZonedDateTime,

	@Column(name = "last_timestamp")
	var lastTimestamp: ZonedDateTime,
)
