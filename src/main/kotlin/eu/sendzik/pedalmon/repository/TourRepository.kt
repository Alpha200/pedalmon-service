package eu.sendzik.pedalmon.repository

import eu.sendzik.pedalmon.model.Tour
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TourRepository : JpaRepository<Tour, UUID> {
	fun findAllByOrderByDateDesc(pageable: Pageable): Page<Tour>
}
