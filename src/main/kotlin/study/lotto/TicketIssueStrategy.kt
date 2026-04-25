package study.lotto

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
    override fun issueTicket(): LotteryTicket = LotteryTicketGenerator.generateAuto()
}

class ManualTicketIssueStrategy(
    private val manualNumber: List<LotteryNumber>,
) : TicketIssueStrategy {
    override fun issueTicket(): LotteryTicket = LotteryTicketGenerator.generateManual(manualNumber)
}

class SemiAutoTicketIssueStrategy(
    private val fixedNumbers: List<LotteryNumber>,
) : TicketIssueStrategy {
    override fun issueTicket(): LotteryTicket = LotteryTicketGenerator.generateSemiAuto(fixedNumbers)
}
