package christmas

import camp.nextstep.edu.missionutils.test.NsTest
import christmas.model.Calculator
import christmas.model.Menu
import christmas.model.OrderMenu
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource


class CalculaterTest : NsTest() {

    private val order = mutableListOf<OrderMenu>()

    @BeforeEach
    fun setUp() {
        order.add(OrderMenu(Menu.BBQ_RIBS, 1))
        order.add(OrderMenu(Menu.T_BONE_STEAK, 1))
        order.add(OrderMenu(Menu.CHOCOLATE_CAKE, 2))
        order.add(OrderMenu(Menu.ZERO_COLA, 1))
    }

    @Test
    fun `할인 전 금액 확인`() {
        assertThat(
            Calculator(
                orderList = order
            ).originalPrice
        ).isEqualTo(142000)
    }

    @Test
    fun `크리스마스 디데이 할인 `() {
        assertThat(
            Calculator(
                day = 3,
                orderList = order
            ).dayDiscount
        ).isEqualTo(1200)
    }


    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31])
    @DisplayName("모든 날짜에 대한 주말 평일 확인")
    fun `주말,평일 확인`(day: Int): String {
        //given
        val calculator = Calculator(day, listOf(OrderMenu(Menu.BBQ_RIBS, 1)))

        //when & then
        if (day % 7 == 2 || day % 7 == 1) return assertThat(calculator.weekend).isEqualTo(true).toString()
        return assertThat(calculator.weekend).isEqualTo(false).toString()
    }

    @ParameterizedTest
    @CsvSource("25,true", "3,true", "1,false")
    fun `특별한 날 확인`(day: Int, expected: Boolean) {
        // given
        val calculator = Calculator(day = day, orderList = order)

        // when & then
        assertThat(calculator.specialDay).isEqualTo(expected)
    }

    @Test
    fun `주말,평일 할인`() {
        assertThat(
            Calculator(
                day = 3,
                orderList = order
            ).weekendDiscount
        ).isEqualTo(4046)
        //주말
        assertThat(
            Calculator(
                day = 1,
                orderList = listOf(
                    OrderMenu(Menu.BBQ_RIBS, 2),
                    OrderMenu(Menu.T_BONE_STEAK, 1),
                    OrderMenu(Menu.CHOCOLATE_CAKE, 2),
                    OrderMenu(Menu.ZERO_COLA, 1)
                )
            ).weekendDiscount
        ).isEqualTo(6069)
    }

    @Test
    fun `증정 확인`() {
        assertThat(
            Calculator(
                orderList = order
            ).gift
        ).isEqualTo(true)
    }


    @Test
    fun `총 혜택 금액 확인`() {
        assertThat(
            Calculator(
                day = 3,
                orderList = order
            ).totalEventAmount
        ).isEqualTo(31246)
    }

    @Test
    fun `할인후 결제 금액 - 증정`() {
        assertThat(
            Calculator(
                day = 3,
                orderList = order
            ).discountedPrice
        ).isEqualTo(135754)
    }

    @ParameterizedTest
    @ValueSource(ints = [20000, 10000, 5000, 4999])
    fun `뱃지 테스트`(price: Int) {
        // given
        val calculator = Calculator(day = 3, orderList = order)

        // when
        calculator.updateBadge(price)
        val expectedBadge = when {
            price >= Calculator.BADGE_SANTA -> "산타"
            price >= Calculator.BADGE_TREE -> "트리"
            price >= Calculator.BADGE_STAR -> "별"
            else -> "없음"
        }

        // then
        assertEquals(expectedBadge, calculator.badge)
    }

    @Test
    fun `할인후 결제 금액 - 증정x`() {
        assertThat(
            Calculator(
                day = 3,
                orderList = listOf(
                    OrderMenu(Menu.CHOCOLATE_CAKE, 1),
                )
            ).discountedPrice
        ).isEqualTo(10777)
    }

    @Test
    fun `할인후 결제 금액 - 할인 x`() {
        assertThat(
            Calculator(
                day = 3,
                orderList = listOf(
                    OrderMenu(Menu.ICE_CREAM, 1),
                )
            ).discountedPrice
        ).isEqualTo(5000)
    }

    override fun runMain() {

    }

}
