package study.lotto

enum class MenuOption(
    val value: Int,
) {
    EXIT(0),
    DEPOSIT(1),
    BUY_TICKET(2),
    FIND_HISTORY(3),
    CHECK_WINNING(4),
    ;

    companion object {
        @JvmStatic
        fun from(value: Int): MenuOption? = entries.find { it.value == value }
    }
}
