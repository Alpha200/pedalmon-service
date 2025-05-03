package eu.sendzik.pedalmon.repository

import eu.sendzik.pedalmon.dto.CustomPage
import eu.sendzik.pedalmon.dto.SegmentRecordDto
import eu.sendzik.pedalmon.model.SegmentRecord
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface SegmentRecordRepository : JpaRepository<SegmentRecord, UUID> {
	@Query("""
WITH ranks AS (SELECT DENSE_RANK() OVER (ORDER BY s.duration_s, s.speed_kmh DESC) rank, id
               FROM segment_record s
               WHERE segment_id = :segmentId)
SELECT rank
FROM ranks
WHERE id = :segmentRecordId
""", nativeQuery = true)
	fun getRankForSegmentRecord(segmentRecordId: UUID, segmentId: UUID): Int

	@Query("SELECT COUNT(*) FROM SegmentRecord s WHERE s.segment.id = :segmentId")
	fun getSegmentRecordCount(segmentId: UUID): Int

	fun getSegmentRecordsBySegmentIdOrderByDurationSAscSpeedKmhDesc(segmentId: UUID, pageable: Pageable): Page<SegmentRecord>
}
