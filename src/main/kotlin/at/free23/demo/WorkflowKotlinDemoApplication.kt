package at.free23.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication

@SpringBootApplication
@EnableProcessApplication
open class WorkflowKotlinDemoApplication	

fun main(args: Array<String>) {
	runApplication<WorkflowKotlinDemoApplication>(*args)
}

