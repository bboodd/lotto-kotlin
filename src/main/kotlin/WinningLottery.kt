package com.www

data class WinningLottery(
    val ticket: LotteryTicket,
    val bonus: LotteryNumber
) {
    companion object {
        fun generate(): WinningLottery {
            val winningTicket = LotteryTicket.generate()
            val usedNumbers = winningTicket.numbers.map { it.number }.toSet()
            val availableNumbers = (LotteryNumber.MIN..LotteryNumber.MAX).filter { it !in usedNumbers }
            val bonusNumber = LotteryNumber(availableNumbers.random())
            return WinningLottery(winningTicket, bonusNumber)
        }
    }

    fun match(ticket: LotteryTicket): Rank {
        val matchCount = this.ticket.getMatchCount(ticket)
        val matchBonus = ticket.numbers.contains(this.bonus)
        return Rank.valueOf(matchCount, matchBonus)
    }
}

enum class Rank(val matchCount: Int, val prize: Int, val comment: String) {
    FIRST(6, 2100000000, "1등"),
    SECOND(5, 60000000, "2등"),
    THIRD(5, 1500000, "3등"),
    FOURTH(4, 50000, "4등"),
    FIFTH(3, 5000, "5등"),
    LOSE(0, 0, "꽝");

    companion object {
        fun valueOf(matchCount: Int, matchBonus: Boolean): Rank {
            return when (matchCount) {
                6 -> FIRST
                5 -> if (matchBonus) SECOND else THIRD
                4 -> FOURTH
                3 -> FIFTH
                else -> LOSE
            }
        }
    }
}
