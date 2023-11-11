package christmas.view

import camp.nextstep.edu.missionutils.Console

class InputView(private val text: String = Console.readLine()) {
    fun visitDate(): Int {
        return text.toInt()
    }
}