package christmas

import camp.nextstep.edu.missionutils.test.NsTest
import christmas.view.InputView
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class InputTest : NsTest() {
    @Test
    fun `날짜에 대한 입력이 잘들어 갔는지 테스트`() {
        assertThat(InputView("3").visitDate()).isEqualTo(3)
    }

    @ParameterizedTest
    @ValueSource(strings = ["45", "a", "-1", "1.4", "0", "32","","\\"])
    fun `날짜에 대한 입력 예외 테스트`(text:String) {
        assertThrows<IllegalArgumentException> {
            InputView(text).visitDate()
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["티본스테이크-2,티본스테이크-1","제로콜라-10","티본스테이크-21","티본스테이크 1,제로콜라 3 ","","티본스테이크,-3",",,","1-티본스테이크,1-제로콜라"])
    fun `음식 주문에 대한 예외 테스트`(text: String) {
        assertThrows<IllegalArgumentException> {
            InputView(text).orderMenu()
        }
    }

    override fun runMain() {
        InputView()
    }
}