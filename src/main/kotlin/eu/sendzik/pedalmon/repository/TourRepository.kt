package eu.sendzik.pedalmon.repository

import eu.sendzik.pedalmon.model.Tour
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TourRepository : JpaRepository<Tour, UUID> {
	@Query("SELECT t FROM Tour t WHERE :filterIds IS NULL OR t.id IN (:filterIds) ORDER BY t.date DESC")
	fun findAllByOrderByDateDesc(filterIds: List<UUID>?, pageable: Pageable): Page<Tour>
	@Query("SELECT t.id FROM Tour t WHERE ST_Intersects(t.track, ST_MakeEnvelope(:xMin, :yMin, :yMax, :yMax, 4326))")
	fun findByBounds(xMin: Double, yMin: Double, xMax: Double, yMax: Double, page: PageRequest): List<UUID>
}
