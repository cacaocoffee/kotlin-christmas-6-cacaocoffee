package christmas.model

data class Calculator(
    var day: Int = 0,
    var orderList: List<OrderMenu> = emptyList(),
    var originalPrice: Int = 0,
    var weekendDiscount: Int = 0,
    var dayDiscount: Int = 0,
    var discountedPrice: Int = 0,
    var totalDiscountedAmount: Int = 0,

    var gift: Boolean = false,
    var badge: String = "없음",
    var specialDay: Boolean = false,
    var weekend: Boolean = false
) {
    init {
        calculateDay()
        calculatePrices()
        updateBadge()
    }

    private fun calculatePrices() {
        originalPrice = orderList.sumOf { it.menu.price * it.quantity }
        if (originalPrice >= MINIMUM_AMOUNT_FOR_EVENT) {
            weekendDiscount = calculateWeekendDiscount() // 요일 할인
            dayDiscount = calculateDayDiscount() // 크리스마스 디데이 할인
            calculateGift() // 증정 여부
            totalDiscountedAmount = calculateTotalDiscountAmount() // 총 혜택 금액
        }
        discountedPrice = calculateDiscountedPrice() // 결제 금액

    }

    private fun calculateDayDiscount(): Int {
        return (day - 1) * ADDITIONAL_DISCOUNT_PER_DAY + DEFAULT_DISCOUNT
    }

    private fun calculateDay() {
        weekend = day % 7 in WEEKEND
        specialDay = day % 7 == SPECIAL_DAY || day == CHRISTMAS
    }

    private fun updateBadge() {
        badge = when {
            discountedPrice >= BADGE_SANTA -> "산타"
            discountedPrice >= BADGE_TREE -> "트리"
            discountedPrice >= BADGE_STAR -> "별"
            else -> "없음"
        }
    }

    private fun calculateDiscountedPrice(): Int {
        return originalPrice - totalDiscountedAmount
    }

    private fun calculateTotalDiscountAmount(): Int {
        var result = 0
        if (specialDay) result += SPECIAL_DISCOUNT
        if (gift) result -= Menu.CHAMPAGNE.price
        result += weekendDiscount + dayDiscount
        return result
    }

    private fun calculateWeekendDiscount(): Int {
        var result = 0
        if (originalPrice >= MINIMUM_ORDER_AMOUNT_FOR_DISCOUNT) return result
        orderList.forEach {
            if (it.menu.category == MenuCategory.DESSERT && !weekend) result += 2023
            if (it.menu.category == MenuCategory.MAIN && weekend) result += 2023
        }
        return result
    }

    private fun calculateGift() {
        if (originalPrice >= MINIMUM_ORDER_AMOUNT_FOR_GIFT) gift = true
    }

    companion object {
        const val BADGE_SANTA = 20_000
        const val BADGE_TREE = 10_000
        const val BADGE_STAR = 5_000
        val WEEKEND = 1..2
        const val SPECIAL_DAY = 3
        const val CHRISTMAS = 25
        const val SPECIAL_DISCOUNT = 1_000
        const val DEFAULT_DISCOUNT = 1_000
        const val ADDITIONAL_DISCOUNT_PER_DAY = 100
        const val MINIMUM_ORDER_AMOUNT_FOR_DISCOUNT = 1_000
        const val MINIMUM_ORDER_AMOUNT_FOR_GIFT = 120_000
        const val MINIMUM_AMOUNT_FOR_EVENT = 10_000
    }
}
