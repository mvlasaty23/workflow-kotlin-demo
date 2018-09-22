package at.free23.demo.config

import org.camunda.bpm.engine.rest.impl.CamundaRestResources
import org.camunda.bpm.engine.rest.mapper.JacksonConfigurator
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.ResourceConfig
import org.slf4j.Logger
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import javax.ws.rs.ApplicationPath

@Component
@ApplicationPath("/rest")
class CamundaJerseyResourceConfig : ResourceConfig(), InitializingBean {

	val log: Logger = org.slf4j.LoggerFactory.getLogger(this.javaClass);

	override fun afterPropertiesSet() {
		registerCamundaRestResources();
		registerAdditionalResources();
	}

	protected fun registerCamundaRestResources() {
		log.info("Configuring camunda rest api.");

		this.registerClasses(CamundaRestResources.getResourceClasses());
		this.registerClasses(CamundaRestResources.getConfigurationClasses()
				.filter({it !is JacksonConfigurator}).toSet());
		this.register(JacksonFeature::class.java);

		log.info("Finished configuring camunda rest api.");
	}

	/**
	 * Adds custom resources
	 */
	protected fun registerAdditionalResources() {
		log.info("Configuring custom rest api.");
		this.packages("at.free23.demo.api");
		this.register(CustomJacksonConfig::class.java);
		log.info("Finished configuring custom rest api.");
	}

}