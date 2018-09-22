package at.free23.demo.activity

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.io.Serializable

data class HistoricGreeting(val message: String) : Serializable {
	val dts: LocalDateTime = LocalDateTime.now();
}

interface Demo {
	val greeting: HistoricGreeting;
	fun sayHello(): String;
}

fun demo(): Demo {
	return object: Demo {
		override val greeting = HistoricGreeting("Hi there!");
		override fun sayHello(): String { return this.greeting.message + " " + this.greeting.dts.toLocalTime().toString(); }
	};
}

@Component
class DemoTask : JavaDelegate {
	var LOGGER: Logger = LoggerFactory.getLogger(this.javaClass);
	override fun execute(exec: DelegateExecution) {
		var demoVal = demo();
		LOGGER.info(demoVal.sayHello());
		exec.setVariable("demoVal", demoVal.greeting);
	}
}

@Component
class ProcessorTask : JavaDelegate {
	var LOGGER: Logger = LoggerFactory.getLogger(this.javaClass);
	override fun execute(exec: DelegateExecution) {
		val demoVal : HistoricGreeting = exec.getVariable("demoVal") as HistoricGreeting;
		LOGGER.info("Second task: " + demoVal);
	}
}
