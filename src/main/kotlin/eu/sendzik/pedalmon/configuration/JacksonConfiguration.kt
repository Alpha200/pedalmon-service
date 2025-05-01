package eu.sendzik.pedalmon.configuration

import com.ctc.wstx.stax.WstxInputFactory
import com.ctc.wstx.stax.WstxOutputFactory
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlFactory
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.xml.stream.XMLInputFactory

@Configuration
class JacksonConfiguration {
	@Bean
	fun objectMapper(): ObjectMapper {
		return ObjectMapper().apply {
			registerKotlinModule()
			registerModule(JavaTimeModule())
		}
	}

	@Bean
	fun xmlMapper(): XmlMapper {
		return XmlMapper(
			XmlFactory(
				WstxInputFactory().apply {
					setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false)
				},
				WstxOutputFactory()
			)
		).apply {
			registerKotlinModule()
			registerModule(JavaTimeModule())
			configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
		}
	}
}
