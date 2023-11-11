package christmas.view

import camp.nextstep.edu.missionutils.Console
import christmas.util.OrderMenu
import christmas.util.Validator

class InputView(private val text: String = Console.readLine()) {
    fun visitDate(): Int {
        Validator.convertDate(text)
        return text.toInt()
    }

    fun orderMenu(): List<OrderMenu> {
        val menuOrders = text.trim().split(delimiterText)
        val result = mutableListOf<OrderMenu>()
        menuOrders.forEach {
            Validator.splitInText(it, delimiterNameQuantity)
            val (name, quantity) = it.split(delimiterNameQuantity)
            Validator.convertNumber(quantity)
            result.add(Validator.menuExistence(name, quantity))
        }
        Validator.menu(result)
        return result
    }

    companion object {
        const val delimiterText = ","
        const val delimiterNameQuantity = ","
    }
}