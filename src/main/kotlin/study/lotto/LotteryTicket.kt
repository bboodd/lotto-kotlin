package study.lotto

import java.util.SortedSet

@JvmInline
value class LotteryTicket(
    val numbers: SortedSet<LotteryNumber>,
) {
    init {
        require(numbers.size == TICKET_SIZE) { "로또 번호는 ${TICKET_SIZE}개여야 합니다." }
    }

    override fun toString(): String = numbers.joinToString(", ")

    fun getMatchCount(ticket: LotteryTicket): Int = this.numbers.intersect(ticket.numbers).size

    companion object {
        const val TICKET_SIZE = 6
    }
}
