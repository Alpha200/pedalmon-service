package eu.sendzik.pedalmon.component

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import java.util.UUID

@RequestScope
@Component
class PedalmonSec {
	fun getUserId(): UUID {
		val authentication = SecurityContextHolder.getContext().authentication as JwtAuthenticationToken
		return UUID.fromString(authentication.token.subject)
	}
}
