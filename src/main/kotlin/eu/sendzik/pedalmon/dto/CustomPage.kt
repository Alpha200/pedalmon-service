package eu.sendzik.pedalmon.dto

import org.springframework.data.domain.Page

data class CustomPage<T>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int,
) {
    fun <U> mapTo(mapper: (T) -> U): CustomPage<U> {
        return CustomPage(
            content.map(mapper),
            pageNumber,
            pageSize,
            totalElements,
            totalPages
        )
    }

    companion object {
        fun <T> from(page: Page<T>): CustomPage<T> {
            return CustomPage(
                content = page.content,
                pageNumber = page.number,
                pageSize = page.size,
                totalElements = page.totalElements,
                totalPages = page.totalPages,
            )
        }
    }
}

fun <T> Page<T>.toCustomPage(): CustomPage<T> {
    return CustomPage.from(this)
}
