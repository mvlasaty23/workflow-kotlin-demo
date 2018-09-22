package at.free23.demo.activity

import at.free23.demo.model.Order
import at.free23.demo.model.OrderProcessVar
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component
class AssignApprovalGroup : JavaDelegate {
	override fun execute(exec: DelegateExecution) {
		val category = exec.getVariable(OrderProcessVar.CATEGORY.key) as Map<String, Any>;
		val order = exec.getVariable(OrderProcessVar.ORDER.key) as Order;
		val country = exec.getVariable(OrderProcessVar.COUNTRY.key) as String;

		val branch = order.creator.group.split("_")[2];
		var responsibleDepartment = category["responsibleDepartment"] as String?;
		if(responsibleDepartment == null || responsibleDepartment.length < 1) {
			responsibleDepartment = "OP";
		}

		val approvalGroups = country+"_"+responsibleDepartment+"_"+branch;
		exec.setVariable(OrderProcessVar.APPROVAL_GROUPS.key, listOf(approvalGroups));
	}
}