package christmas

import camp.nextstep.edu.missionutils.test.NsTest
import christmas.view.InputView
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class InputTest : NsTest() {
    @Test
    fun `날짜에 대한 입력이 잘들어 갔는지 테스트`() {
        assertThat(InputView("3").visitDate()).isEqualTo(3)
    }

    @Test
    fun `날짜에 대한 입력 예외 테스트`() {
        assertThrows<IllegalArgumentException> {
            InputView("45").visitDate()
        }
        assertThrows<IllegalArgumentException> {
            InputView("a").visitDate()
        }
        assertThrows<IllegalArgumentException> {
            InputView("-1").visitDate()
        }
        assertThrows<IllegalArgumentException> {
            InputView("1.4").visitDate()
        }
    }

    @Test
    fun `음식 주문에 대한 예외 테스트`() {
        assertThrows<IllegalArgumentException> {
            InputView("티본스테이크-2,티본스테이크-1").orderMenu()
        }
        assertThrows<IllegalArgumentException> {
            InputView("제로콜라-10").orderMenu()
        }
        assertThrows<IllegalArgumentException> {
            InputView("티본스테이크-21").orderMenu()
        }
        assertThrows<IllegalArgumentException> {
            InputView("").orderMenu()
        }
        assertThrows<IllegalArgumentException> {
            InputView("하늘을 우러러 한 점 부끄럼 없기를-3,").orderMenu()
        }
        assertThrows<IllegalArgumentException> {
            InputView("티본스테이크 1,제로콜라 3 ").orderMenu()
        }
        assertThrows<IllegalArgumentException> {
            InputView(",,").orderMenu()
        }
        assertThrows<IllegalArgumentException> {
            InputView("1-티본스테이크,1-제로콜라").orderMenu()
        }

    }

    override fun runMain() {
        InputView()
    }
}