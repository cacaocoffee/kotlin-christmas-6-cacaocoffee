package christmas.model

data class Calculator(
    val day: Int = 0,
    val orderList: List<OrderMenu> = emptyList()
) {
    var originalPrice: Int = 0
    var weekendDiscount: Int = 0
    var dayDiscount: Int = 0
    var discountedPrice: Int = 0
    var totalEventAmount: Int = 0
    var gift: Boolean = false
    var badge: String = "없음"
    var specialDay: Boolean = false
    var weekend: Boolean = false
    val GIFT = Menu.CHAMPAGNE

    init {
        calculateOriginalPrice()
        if (originalPrice >= MINIMUM_AMOUNT_FOR_EVENT) {
            calculateDay()
            calculateEvent()
            updateBadge(totalEventAmount)
        }
        discountedPrice = calculateDiscountedPrice()
    }

    private fun calculateOriginalPrice() {
        originalPrice = orderList.sumOf { it.menu.price * it.quantity }
    }


    private fun calculateEvent() {
        weekendDiscount = calculateWeekendDiscount() // 요일 할인
        dayDiscount = calculateDayDiscount() // 크리스마스 디데이 할인
        calculateGift() // 증정 여부
        totalEventAmount = calculateTotalDiscountAmount() // 총 혜택 금액
    }

    private fun calculateDayDiscount(): Int {
        return (day - 1) * ADDITIONAL_DISCOUNT_PER_DAY + DEFAULT_DISCOUNT
    }

    private fun calculateDay() {
        weekend = day % 7 in WEEKEND
        specialDay = day % 7 == SPECIAL_DAY || day == CHRISTMAS
    }

    fun updateBadge(number: Int) {
        badge = when {
            number >= BADGE_SANTA -> "산타"
            number >= BADGE_TREE -> "트리"
            number >= BADGE_STAR -> "별"
            else -> "없음"
        }
    }

    private fun calculateDiscountedPrice(): Int {
        if (gift) return originalPrice - totalEventAmount + GIFT.price
        return originalPrice - totalEventAmount
    }

    private fun calculateTotalDiscountAmount(): Int {
        var result = 0
        if (specialDay) result += SPECIAL_DISCOUNT
        if (gift) result += GIFT.price
        result += weekendDiscount + dayDiscount
        return result
    }

    private fun calculateWeekendDiscount(): Int {
        var result = 0
        orderList.forEach {
            if (it.menu.category == MenuCategory.DESSERT && !weekend) result += DAY_DISCOUNT_AMOUNT * it.quantity
            if (it.menu.category == MenuCategory.MAIN && weekend) result += DAY_DISCOUNT_AMOUNT * it.quantity
        }
        return result
    }

    private fun calculateGift() {
        if (originalPrice >= MINIMUM_ORDER_AMOUNT_FOR_GIFT) gift = true
    }

    companion object {
        const val DAY_DISCOUNT_AMOUNT = 2023
        const val BADGE_SANTA = 20_000
        const val BADGE_TREE = 10_000
        const val BADGE_STAR = 5_000
        val WEEKEND = 1..2
        const val SPECIAL_DAY = 3
        const val CHRISTMAS = 25
        const val SPECIAL_DISCOUNT = 1_000
        const val DEFAULT_DISCOUNT = 1_000
        const val ADDITIONAL_DISCOUNT_PER_DAY = 100
        const val MINIMUM_ORDER_AMOUNT_FOR_GIFT = 120_000
        const val MINIMUM_AMOUNT_FOR_EVENT = 10_000
    }
}
