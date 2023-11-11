package christmas

import camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest
import camp.nextstep.edu.missionutils.test.NsTest
import christmas.view.InputView
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class InputViewTest :NsTest() {
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


    override fun runMain() {
        InputView()
    }

}