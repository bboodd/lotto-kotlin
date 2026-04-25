package study.lotto

data class StrategyCounts(
    val auto: Int,
    val manual: Int,
    val semi: Int,
)

object LotteryMachine {
    fun issue(counts: StrategyCounts): List<LotteryTicket> =
        mutableListOf<LotteryTicket>().apply {
            addAll(List(counts.auto) { AutoTicketIssueStrategy().issueTicket() })
            addAll(List(counts.manual) { ManualTicketIssueStrategy().issueTicket() })
            addAll(List(counts.semi) { SemiAutoTicketIssueStrategy().issueTicket() })
        }
}
