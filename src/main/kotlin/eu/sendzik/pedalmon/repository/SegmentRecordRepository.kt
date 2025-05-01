package eu.sendzik.pedalmon.repository

import eu.sendzik.pedalmon.model.SegmentRecord
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SegmentRecordRepository : JpaRepository<SegmentRecord, UUID>
