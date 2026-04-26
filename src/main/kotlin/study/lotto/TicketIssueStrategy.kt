package study.lotto

import study.lotto.LotteryNumber.Companion.ALL_NUMBERS

data class StrategyCounts(
    val auto: Int,
    val manual: Int,
    val semi: Int,
)

interface TicketIssueStrategy {
    fun issueTicket(): LotteryTicket

    companion object {
        const val NUMBER_OF_STRATEGIES = 3
        const val SEMI_AUTO_MIN_MANUAL_TICKET_SIZE = 1
        const val SEMI_AUTO_MAX_MANUAL_TICKET_SIZE = 5
    }
}

class AutoTicketIssueStrategy : TicketIssueStrategy {
    override fun issueTicket(): LotteryTicket {
        val numbers =
            ALL_NUMBERS
                .shuffled()
                .take(LotteryTicket.TICKET_SIZE)
                .toSortedSet()
        return LotteryTicket(numbers)
    }
}

class ManualTicketIssueStrategy(
    private val manualNumbers: List<LotteryNumber>,
) : TicketIssueStrategy {
    override fun issueTicket() = LotteryTicket(manualNumbers.toSortedSet())
}

class SemiAutoTicketIssueStrategy(
    private val fixedNumbers: List<LotteryNumber>,
) : TicketIssueStrategy {
    override fun issueTicket(): LotteryTicket {
        val remainCount = LotteryTicket.TICKET_SIZE - fixedNumbers.size
        val randoms =
            (ALL_NUMBERS.toSet() - fixedNumbers.toSet())
                .shuffled()
                .take(remainCount)
        return LotteryTicket((fixedNumbers + randoms).toSortedSet())
    }
}
