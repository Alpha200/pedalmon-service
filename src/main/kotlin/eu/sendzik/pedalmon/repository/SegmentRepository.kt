package eu.sendzik.pedalmon.repository

import eu.sendzik.pedalmon.model.Segment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SegmentRepository : JpaRepository<Segment, UUID> {
	@Query("SELECT s FROM Segment s CROSS JOIN Tour t WHERE t.id = :id AND CAST(ST_COVERS(ST_BUFFER(t.track, 10), s.path) AS boolean)")
	fun findSegmentsByTourId(id: UUID): List<Segment>
	@Query("SELECT s.id FROM Segment s WHERE ST_Intersects(s.path, ST_MakeEnvelope(:xMin, :yMin, :yMax, :yMax, 4326))")
	fun findByBounds(xMin: Double, yMin: Double, xMax: Double, yMax: Double, pageable: Pageable): List<UUID>
	@Query("SELECT s FROM Segment s WHERE :filterIds IS NULL OR s.id IN (:filterIds)")
	fun findAllByIdIn(filterIds: List<UUID>?, pageable: Pageable): Page<Segment>
}
