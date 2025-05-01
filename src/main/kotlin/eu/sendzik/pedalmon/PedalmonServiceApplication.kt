package eu.sendzik.pedalmon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PedalmonServiceApplication

fun main(args: Array<String>) {
	runApplication<PedalmonServiceApplication>(*args)
}
