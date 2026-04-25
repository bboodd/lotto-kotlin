package study.lotto

object LotteryTicketGenerator {
    private val ALL_NUMBERS = (LotteryNumber.MIN..LotteryNumber.MAX).map { LotteryNumber(it) }

    fun generateAuto(): LotteryTicket {
        val lotteryNumbers = ALL_NUMBERS.shuffled().take(LotteryTicket.TICKET_SIZE).toSortedSet()
        return LotteryTicket(lotteryNumbers)
    }

    fun generateManual(inputNumbers: List<LotteryNumber>): LotteryTicket = LotteryTicket(inputNumbers.toSortedSet())

    fun generateSemiAuto(fixedNumbers: List<LotteryNumber>): LotteryTicket {
        val remainCount = LotteryTicket.TICKET_SIZE - fixedNumbers.size
        val randomNumbers =
            (ALL_NUMBERS.toSet() - fixedNumbers.toSet())
                .shuffled()
                .take(remainCount)
        val combined = (fixedNumbers + randomNumbers).toSortedSet()
        return LotteryTicket(combined)
    }

    fun generateWinningLottery(): WinningLottery {
        val winningTicket = generateAuto()
        val bonusNumber = (ALL_NUMBERS - winningTicket.numbers).random()
        return WinningLottery(winningTicket, bonusNumber)
    }
}
