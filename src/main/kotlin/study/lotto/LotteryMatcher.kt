package study.lotto

object LotteryMatcher {
    fun match(
        winningLottery: WinningLottery,
        userTicket: LotteryTicket,
    ): Rank {
        val matchCount = winningLottery.ticket.getMatchCount(userTicket)
        val matchBonus = userTicket.numbers.contains(winningLottery.bonus)

        return Rank.valueOf(matchCount, matchBonus)
    }
}
