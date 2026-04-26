package study.lotto

object LotteryMachine {
    fun issue(strategy: TicketIssueStrategy): LotteryTicket = strategy.issueTicket()
}
