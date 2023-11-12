package christmas.util

import christmas.model.Menu
import christmas.model.MenuCategory
import christmas.model.OrderMenu


object Validator {
    private val DATE_RANGE = 1..31
    private val MAX_QUANTITY = 20

    fun convertNumber(text: String) {
        require(text.toIntOrNull() != null && text.toInt() >= 1) { ErrorMessage.INVALID_ORDER.getMessage() }
    }

    fun delimiterInText(text: String, phaser: String) {
        require(phaser in text) { ErrorMessage.INVALID_ORDER.getMessage() }
    }

    fun convertDate(text: String) {
        require(text.toIntOrNull() != null && text.toInt() in DATE_RANGE) { ErrorMessage.INVALID_DATE.getMessage() }
    }

    fun menuExistence(name: String, quantity: String): OrderMenu {
        val menu = Menu.values().find { it.menuName == name }
        require(menu != null) { ErrorMessage.INVALID_ORDER.getMessage() }
        return OrderMenu(menu, quantity.toInt())
    }

    fun menu(orderMenu: List<OrderMenu>) {
        emptyOrder(orderMenu)
        menuMax(orderMenu)
        beverageOnly(orderMenu)
        duplicateMenu(orderMenu)
    }

    private fun emptyOrder(orderMenu: List<OrderMenu>) {
        require(orderMenu.isNotEmpty()) { ErrorMessage.INVALID_ORDER.getMessage() }
    }

    private fun menuMax(orderMenu: List<OrderMenu>) {
        var count = 0
        orderMenu.forEach { count += it.quantity }
        require(count <= MAX_QUANTITY) { ErrorMessage.INVALID_ORDER.getMessage() }
    }

    private fun beverageOnly(orderMenu: List<OrderMenu>) {
        require(!orderMenu.all { it.menu.category == MenuCategory.BEVERAGE }) { ErrorMessage.INVALID_ORDER.getMessage() }
    }

    private fun duplicateMenu(orderMenu: List<OrderMenu>) {
        val duplicateMenus = orderMenu.groupBy { it.menu.menuName }
            .filter { it.value.size > 1 }
            .keys
        require(duplicateMenus.isEmpty()) { ErrorMessage.INVALID_ORDER.getMessage() }
    }
}