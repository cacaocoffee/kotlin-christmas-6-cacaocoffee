package christmas.util

object Validator {
    private val DATE_RANGE = 1..31

    fun convertDate(text: String) {
        require(text.toIntOrNull() != null && text.toInt() in DATE_RANGE) { ErrorMessage.INVALID_DATE.getMessage() }
    }


}