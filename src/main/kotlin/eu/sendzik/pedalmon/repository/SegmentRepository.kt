package eu.sendzik.pedalmon.repository

import eu.sendzik.pedalmon.model.Segment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SegmentRepository : JpaRepository<Segment, UUID>
