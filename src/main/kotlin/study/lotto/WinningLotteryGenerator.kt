package study.lotto

import study.lotto.LotteryNumber.Companion.ALL_NUMBERS

object WinningLotteryGenerator {
    fun generate(): WinningLottery {
        val winningTicket = AutoTicketIssueStrategy().issueTicket()
        val bonusNumber = (ALL_NUMBERS - winningTicket.numbers).random()
        return WinningLottery(winningTicket, bonusNumber)
    }
}
