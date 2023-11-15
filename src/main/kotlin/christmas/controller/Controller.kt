package christmas.controller

import christmas.model.Calculator
import christmas.model.Menu
import christmas.model.OrderMenu
import christmas.view.InputView
import christmas.view.OutputView

class Controller {

    fun run() {
        OutputView().printGreeting()
        val calculator = Calculator(day = inputDate(), orderList = inputMenu())
        OutputView().printEventBenefit(calculator.day)
        OutputView().printTotalOrder(calculator.orderList)
        OutputView().printPriceBeforeDiscount(printNumberWithComma(calculator.originalPrice))
        OutputView().printGift(outputGift(calculator.gift))
        OutputView().printEventDescription(totalEvent(calculator))
        OutputView().printTotalEventAmount(totalEventAmount(calculator))
        OutputView().printEventBadge(calculator.badge)
        OutputView().printPriceAfterDiscount(printNumberWithComma(calculator.discountedPrice))
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

    private fun outputGift(gift: Boolean): String {
        return if (gift) "${Menu.CHAMPAGNE.menuName} 1개" else "없음"
    }

    private fun totalEvent(calculator: Calculator): List<String> {
        val result = calculateEvent(calculator)
        if (result.isEmpty()) result.add(NONE)
        return result
    }

    private fun totalEventAmount(calculator: Calculator): String {
        return printNumberWithComma(-1 * calculator.totalEventAmount)
    }

    private fun calculateEvent(calculator: Calculator): MutableList<String> {
        val result = mutableListOf<String>()
        if (calculator.dayDiscount > 0) result.add(event(CHRISTMAS_DAY_EVENT, calculator.dayDiscount))
        if (calculator.weekendDiscount > 0) result.add(weeklyDiscount(calculator))
        if (calculator.specialDay) result.add(event(SPECIAL_DISCOUNT, Calculator.SPECIAL_DISCOUNT))
        if (calculator.gift) result.add(event(GIFT_EVENT, calculator.GIFT.price))
        return result
    }

    private fun weeklyDiscount(calculator: Calculator): String {
        if (calculator.weekend) return event(WEEKEND_DISCOUNT, calculator.weekendDiscount)
        return event(DAY_DISCOUNT, calculator.weekendDiscount)
    }

    private fun event(description: String, discount: Int): String =
        "$description${printNumberWithComma(-1 * discount)}원"

    private fun printNumberWithComma(number: Int): String {
        return String.format("%,d", number)
    }

    companion object {
        const val CHRISTMAS_DAY_EVENT = "크리스마스 디데이 할인: "
        const val WEEKEND_DISCOUNT = "주말 할인: "
        const val DAY_DISCOUNT = "평일 할인: "
        const val SPECIAL_DISCOUNT = "특별 할인: "
        const val GIFT_EVENT = "증정 이벤트: "
        const val NONE = "없음"
    }
}