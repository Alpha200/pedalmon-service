package eu.sendzik.pedalmon.component

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import java.util.UUID

@RequestScope
@Component
class PedalmonSec {
	fun getUserId(): UUID {
		val authentication = SecurityContextHolder.getContext().authentication
		return UUID.randomUUID()
	}
}
