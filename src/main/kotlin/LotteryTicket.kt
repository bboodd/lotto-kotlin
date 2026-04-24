package com.www

data class LotteryTicket(
    val numbers: List<LotteryNumber>
) {
    companion object {
        const val TICKET_SIZE = 6
        const val SEMI_AUTO_MIN_MANUAL_TICKET_SIZE = 1
        const val SEMI_AUTO_MAX_MANUAL_TICKET_SIZE = 6

        fun generate(): LotteryTicket {
            val randomNumbers =
                (LotteryNumber.MIN..LotteryNumber.MAX)
                    .shuffled()
                    .take(TICKET_SIZE)
                    .sorted()
                    .map { LotteryNumber(it) }
            return LotteryTicket(randomNumbers)
        }

        fun generateSemiAuto(fixedNumbers: List<LotteryNumber>): LotteryTicket {
            val remainCount = TICKET_SIZE - fixedNumbers.size
            val availableNumbers =
                (LotteryNumber.MIN..LotteryNumber.MAX).map { LotteryNumber(it) } - fixedNumbers.toSet()
            val randomNumbers = availableNumbers.shuffled().take(remainCount)
            val result = (fixedNumbers + randomNumbers).sortedBy { it.number }
            return LotteryTicket(result)
        }
    }

    init {
        require(numbers.size == TICKET_SIZE) { "로또 번호는 6개여야 합니다." }
    }

    override fun toString(): String = numbers.joinToString(", ")

    fun getMatchCount(ticket: LotteryTicket): Int = this.numbers.intersect(ticket.numbers.toSet()).size
}
