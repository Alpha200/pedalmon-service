package eu.sendzik.pedalmon.component

import eu.sendzik.pedalmon.repository.UserRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID

@Component
class UserUpdateFilter(
    private val userRepository: UserRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		filterChain: FilterChain
    ) {
        val authentication = request.userPrincipal
        if (authentication is JwtAuthenticationToken) {
            val jwtToken = authentication.token
            val userId = UUID.fromString(jwtToken.subject)
            val givenName = jwtToken.getClaimAsString("given_name")
            val familyName = jwtToken.getClaimAsString("family_name")

            userRepository.upsertUser(userId, givenName, familyName)
        }

        filterChain.doFilter(request, response)
    }
}
