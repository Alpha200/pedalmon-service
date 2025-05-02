package eu.sendzik.pedalmon.service

import eu.sendzik.pedalmon.dto.CustomPage
import eu.sendzik.pedalmon.dto.SegmentDto
import eu.sendzik.pedalmon.dto.toCustomPage
import eu.sendzik.pedalmon.mapper.toDto
import eu.sendzik.pedalmon.mapper.toEntity
import eu.sendzik.pedalmon.repository.SegmentRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SegmentService(
	private val segmentRepository: SegmentRepository,
) {
	fun createSegment(segmentDto: SegmentDto): SegmentDto {
		val segment = segmentDto.toEntity()
		return segmentRepository.save(segment).toDto()
	}

	fun getSegments(pageable: Pageable): CustomPage<SegmentDto> {
		return segmentRepository.findAll(pageable).toCustomPage().mapTo { it.toDto() }
	}

	fun getSegment(segmentId: UUID): SegmentDto {
		return segmentRepository.findById(segmentId)
			.map { it.toDto() }
			.orElseThrow()
	}
}
