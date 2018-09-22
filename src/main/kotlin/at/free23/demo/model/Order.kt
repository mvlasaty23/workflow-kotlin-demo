package at.free23.demo.model

import java.io.Serializable

data class Creator(val id: String = "", val name: String = "", val group: String = "") : Serializable { }

data class Position(val id: String = "", val amount: Double = Double.NaN) : Serializable { }

data class Order(
	val orderNumber: String = "",
	val projectRef: String? = null,
	val creator: Creator = Creator(),
	val positions: Array<Position> = emptyArray<Position>()
) : Serializable { }

enum class OrderProcessVar(val key: String) {
	ORDER("order"),
	AMOUNT("amount"),
	COUNTRY("country"),
	INTERN("isIntern"),
	CATEGORY("category"),
	APPROVAL_GROUPS("approvalGroups")
}
