package christmas.view

import christmas.model.OrderMenu

class OutputView {
    fun printGreeting() {
        println(GREETING_MESSAGE)
    }

    fun printVisitDate() {
        println(VISIT_DATE_PROMPT)
    }

    fun printOrderMenu() {
        println(ORDER_MENU_PROMPT)
    }

    fun printEventBenefit(day: Int) {
        println(EVENT_BENEFIT_PREVIEW.format(day))
    }

    fun printTotalOrder(order: List<OrderMenu>) {
        println()
        println(ORDER_MENU_TITLE)
        order.forEach { printOneOrder(it) }
    }

    private fun printOneOrder(order: OrderMenu) {
        println("${order.menu.menuName} ${order.quantity}개")
    }

    fun printPriceBeforeDiscount(originalPrice: String) {
        println()
        println(PRICE_BEFORE_DISCOUNT_TITLE)
        println("${originalPrice}원")
    }

    fun printEventDescription(descriptorInfo: List<String>) {
        println()
        println(EVENT_DESCRIPTION_TITLE)
        descriptorInfo.forEach { println(it) }
    }

    fun printGift(gift: String) {
        println()
        println(GIFT_MENU_TITLE)
        println(gift)
    }


    fun printTotalEventAmount(amount: String) {
        println()
        println(TOTAL_EVENT_AMOUNT_TITLE)
        println("${amount}원")
    }

    fun printPriceAfterDiscount(price:String) {
        println()
        println(PRICE_AFTER_DISCOUNT_TITLE)
        println("${price}원")
    }

    fun printEventBadge(badge: String) {
        println()
        println(EVENT_BADGE_TITLE)
        println(badge)
    }

    companion object {
        const val GREETING_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다."
        const val VISIT_DATE_PROMPT = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)"
        const val ORDER_MENU_PROMPT = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)"
        const val EVENT_BENEFIT_PREVIEW = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"
        const val ORDER_MENU_TITLE = "<주문 메뉴>"
        const val PRICE_BEFORE_DISCOUNT_TITLE = "<할인 전 총주문 금액>"
        const val GIFT_MENU_TITLE = "<증정 메뉴>"
        const val EVENT_DESCRIPTION_TITLE = "<혜택 내역>"
        const val TOTAL_EVENT_AMOUNT_TITLE = "<총혜택 금액>"
        const val PRICE_AFTER_DISCOUNT_TITLE = "<할인 후 예상 결제 금액>"
        const val EVENT_BADGE_TITLE = "<12월 이벤트 배지>"
    }
}