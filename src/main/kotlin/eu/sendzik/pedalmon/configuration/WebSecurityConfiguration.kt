package eu.sendzik.pedalmon.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration {
	@Bean
	fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
		return http
			.securityMatcher("/**")
			.csrf{ it.disable() }
			.cors { }
			.authorizeHttpRequests { it
				.requestMatchers("/actuator/health/liveness","/actuator/health/readiness").permitAll()
				.anyRequest().authenticated()
			}
			.oauth2ResourceServer { it.jwt(Customizer.withDefaults()) }
			.build()
	}
}
