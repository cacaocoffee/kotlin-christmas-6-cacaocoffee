package christmas.view

import camp.nextstep.edu.missionutils.Console
import christmas.util.Validator

class InputView(private val text: String = Console.readLine()) {
    fun visitDate(): Int {
        Validator.convertDate(text)
        return text.toInt()
    }
}