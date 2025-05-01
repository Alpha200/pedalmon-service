package eu.sendzik.pedalmon.mapper

import eu.sendzik.pedalmon.dto.SegmentRecordDto
import eu.sendzik.pedalmon.model.SegmentRecord

fun SegmentRecordDto.toEntity(): SegmentRecord {
	return SegmentRecord(
		id = id,
		tour = tour?.toEntity(),
		segment = segment?.toEntity(),
		speedKmh = speedKmh,
		durationS = durationS,
		time = time,
	)
}

fun SegmentRecord.toDto(mapTour: Boolean = false): SegmentRecordDto {
	return SegmentRecordDto(
		id = id,
		tour = if (mapTour) { tour?.toDto() } else { null },
		segment = segment?.toDto(),
		speedKmh = speedKmh,
		durationS = durationS,
		time = time,
	)
}
