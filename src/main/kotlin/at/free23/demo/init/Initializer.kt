package at.free23.demo.init

import javax.annotation.PostConstruct

abstract class Initializer {
	@PostConstruct
	abstract fun init();
}
