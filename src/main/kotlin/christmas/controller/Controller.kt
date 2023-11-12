package christmas.controller

import christmas.model.Calculator
import christmas.model.OrderMenu
import christmas.view.InputView
import christmas.view.OutputView

class Controller() {

    fun run() {
        OutputView().printGreeting()
        val calculator = Calculator(day = inputDate(), orderList = inputMenu())

    }

    private fun inputDate(): Int{
        OutputView().printVisitDate()
        return try {
            InputView().visitDate()
        } catch (e: IllegalArgumentException){
            println(e.message)
            inputDate()
        }
    }

    private fun inputMenu(): List<OrderMenu> {
        OutputView().printOrderMenu()
        return try {
            InputView().orderMenu()
        } catch (e: IndexOutOfBoundsException) {
            println(e.message)
            inputMenu()
        } catch (e: IllegalArgumentException) {
            println(e.message)
            inputMenu()
        }
    }
}