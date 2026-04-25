package study.lotto

import java.util.SortedSet

@JvmInline
value class LotteryTicket(
    val numbers: SortedSet<LotteryNumber>,
) {
    companion object {
        const val TICKET_SIZE = 6
        const val SEMI_AUTO_MIN_MANUAL_TICKET_SIZE = 1
        const val SEMI_AUTO_MAX_MANUAL_TICKET_SIZE = 6
    }

    init {
        require(numbers.size == TICKET_SIZE) { OutputUtil.printIncorrectLotteryTicketSize() }
    }

    override fun toString(): String = numbers.joinToString(", ")

    fun getMatchCount(ticket: LotteryTicket): Int = this.numbers.intersect(ticket.numbers).size
}
