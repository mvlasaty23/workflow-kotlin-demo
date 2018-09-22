package at.free23.demo.activity

import at.free23.demo.model.Order
import at.free23.demo.model.OrderProcessVar
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component
class ExtractOrderMetadata : JavaDelegate {
	override fun execute(exec: DelegateExecution) {
		val order = exec.getVariable(OrderProcessVar.ORDER.key) as Order;
		
		val countryCode =  order.creator.group.split("_")[0];
		exec.setVariable(OrderProcessVar.COUNTRY.key, countryCode);
		
		val amount = order.positions.map({pos -> pos.amount}).reduce({sum, next -> sum + next});
		exec.setVariable(OrderProcessVar.AMOUNT.key, amount);
		
		val isIntern = order.projectRef == null || order.projectRef.length < 1;
		exec.setVariable(OrderProcessVar.INTERN.key, isIntern);
	}
}
