package christmas.controller

import christmas.model.Calculator
import christmas.model.OrderMenu
import christmas.view.InputView
import christmas.view.OutputView

class Controller {
    fun run() {
        OutputView().printGreeting()
        val calculator = Calculator(day = inputDate(), orderList = inputMenu())

    }

    private fun <T> handleInputException(action: () -> T): T {
        return try {
            action()
        } catch (e: IllegalArgumentException) {
            println(e.message)
            handleInputException(action)
        } catch (e: IndexOutOfBoundsException) {
            println(e.message)
            handleInputException(action)
        }
    }

    private fun inputDate(): Int {
        OutputView().printVisitDate()
        return handleInputException { InputView().visitDate() }
    }

    private fun inputMenu(): List<OrderMenu> {
        OutputView().printOrderMenu()
        return handleInputException { InputView().orderMenu() }
    }

}