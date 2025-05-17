package eu.sendzik.pedalmon.repository

import eu.sendzik.pedalmon.model.LiveSegment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface LiveSegmentRepository : JpaRepository<LiveSegment, UUID>
