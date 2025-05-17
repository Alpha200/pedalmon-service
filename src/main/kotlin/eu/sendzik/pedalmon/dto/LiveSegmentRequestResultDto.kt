import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class LiveSegmentRequestResultDto(
	val status: LiveSegmentStatus,
	val liveSegmentProgress: LiveSegmentProgress?,
	val liveSegmentResult: LiveSegmentResult?,
)

enum class LiveSegmentStatus {
	@JsonProperty("inactive")
	INACTIVE,
	@JsonProperty("tracking")
	TRACKING,
	@JsonProperty("finished")
	FINISHED,
	@JsonProperty("failed")
	FAILED,
}

data class LiveSegmentProgress(
	val segmentId: UUID,
	val timeElapsedSeconds: Double,
	val distanceMeters: Int,
	val distanceMetersBest: Int,
	val distanceMetersTotal: Int,
)

data class LiveSegmentResult(
	val timeElapsedTotalSeconds: Int,
	val timeElapsedBestSeconds: Int,
	val personalRank: Int,
)
