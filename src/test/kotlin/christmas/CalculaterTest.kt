package christmas

import camp.nextstep.edu.missionutils.test.NsTest
import christmas.model.Calculator
import christmas.model.Menu
import christmas.model.OrderMenu
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class CalculaterTest : NsTest() {

    @Test
    fun `할인 전 금액 확인`() {
        assertThat(
            Calculator(
                orderList = listOf(
                    OrderMenu(Menu.BBQ_RIBS, 1),
                    OrderMenu(Menu.T_BONE_STEAK, 1),
                    OrderMenu(Menu.CHOCOLATE_CAKE, 2),
                    OrderMenu(Menu.ZERO_COLA, 1)
                )
            ).originalPrice
        ).isEqualTo(142000)
    }

    @Test
    fun `크리스마스 디데이 할인 `() {
        assertThat(
            Calculator(
                day = 3,
                orderList = listOf(
                    OrderMenu(Menu.BBQ_RIBS, 1),
                    OrderMenu(Menu.T_BONE_STEAK, 1),
                    OrderMenu(Menu.CHOCOLATE_CAKE, 2),
                    OrderMenu(Menu.ZERO_COLA, 1)
                )
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

    @Test
    fun `특별한 날 확인`() {
        assertThat(Calculator(day = 25, orderList = listOf(OrderMenu(Menu.BBQ_RIBS, 1))).specialDay).isEqualTo(true)
        assertThat(Calculator(day = 3, orderList = listOf(OrderMenu(Menu.BBQ_RIBS, 1))).specialDay).isEqualTo(true)
        assertThat(Calculator(day = 1, orderList = listOf(OrderMenu(Menu.BBQ_RIBS, 1))).specialDay).isEqualTo(false)
    }

    @Test
    fun `주말,평일 할인`() {
        assertThat(
            Calculator(
                day = 3,
                orderList = listOf(
                    OrderMenu(Menu.BBQ_RIBS, 1),
                    OrderMenu(Menu.T_BONE_STEAK, 1),
                    OrderMenu(Menu.CHOCOLATE_CAKE, 2),
                    OrderMenu(Menu.ZERO_COLA, 1)
                )
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
                orderList = listOf(
                    OrderMenu(Menu.BBQ_RIBS, 1),
                    OrderMenu(Menu.T_BONE_STEAK, 1),
                    OrderMenu(Menu.CHOCOLATE_CAKE, 2),
                    OrderMenu(Menu.ZERO_COLA, 1)
                )
            ).gift
        ).isEqualTo(true)
    }


    @Test
    fun `총 혜택 금액 확인`() {
        assertThat(
            Calculator(
                day = 3,
                orderList = listOf(
                    OrderMenu(Menu.BBQ_RIBS, 1),
                    OrderMenu(Menu.T_BONE_STEAK, 1),
                    OrderMenu(Menu.CHOCOLATE_CAKE, 2),
                    OrderMenu(Menu.ZERO_COLA, 1)
                )
            ).totalEventAmount
        ).isEqualTo(31246)
    }

    @Test
    fun `할인후 결제 금액 - 증정`() {
        assertThat(
            Calculator(
                day = 3,
                orderList = listOf(
                    OrderMenu(Menu.BBQ_RIBS, 1),
                    OrderMenu(Menu.T_BONE_STEAK, 1),
                    OrderMenu(Menu.CHOCOLATE_CAKE, 2),
                    OrderMenu(Menu.ZERO_COLA, 1)
                )
            ).discountedPrice
        ).isEqualTo(135754)
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

    companion object {
        private val LINE_SEPARATOR = System.lineSeparator()
    }
}
