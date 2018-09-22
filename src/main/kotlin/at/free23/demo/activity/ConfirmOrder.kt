package at.free23.demo.activity

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component
class ConfirmOrder : JavaDelegate {
	override fun execute(exec: DelegateExecution) {
		
	}
}