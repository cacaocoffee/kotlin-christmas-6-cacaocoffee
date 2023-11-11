package christmas.util

enum class ErrorMessage(private val message: String) {
    INVALID_DATE("유효하지 않은 날짜입니다. 다시 입력해 주세요.");

    fun getMessage(): String = "[ERROR] $message"
}