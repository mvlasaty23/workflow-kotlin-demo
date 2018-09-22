package at.free23.demo.api

import at.free23.demo.model.Order
import org.camunda.bpm.engine.RuntimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

@Component
@XmlAccessorType(XmlAccessType.NONE)
@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
class OrderResource {
	
	val runtimeService: RuntimeService;

	@Autowired
	constructor(runtimeService: RuntimeService) {
		this.runtimeService = runtimeService;
	}
	
	@POST
	@Path("/approval")
	@Consumes(MediaType.APPLICATION_JSON)
	fun startApproval(order: Order): Response {
		val variables = mapOf("order" to order);
		this.runtimeService.startProcessInstanceByKey("OrderApprovalDmn", variables);
		return Response.noContent().build();
	}
}