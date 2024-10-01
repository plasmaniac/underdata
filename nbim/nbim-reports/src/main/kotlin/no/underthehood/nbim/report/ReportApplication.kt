package no.underthehood.nbim.report

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
@RestController
class ReportApplication

fun main(args: Array<String>) {

	runApplication<ReportApplication>(*args)

}
