package com.www

data class LotteryTicket(
    val numbers: List<LotteryNumber>
) {
    companion object {
        const val TICKET_SIZE = 6

        fun generate(): LotteryTicket {
            val randomNumbers =
                (LotteryNumber.MIN..LotteryNumber.MAX)
                    .shuffled()
                    .take(TICKET_SIZE)
                    .sorted()
                    .map { LotteryNumber(it) }
            return LotteryTicket(randomNumbers)
        }
    }

    init {
        require(numbers.size == TICKET_SIZE) { "로또 번호는 6개여야 합니다." }
    }

//    override fun toString(): String = numbers.joinToString(", ") { it.number.toString() }

    fun getMatchCount(ticket: LotteryTicket): Int = this.numbers.intersect(ticket.numbers.toSet()).size
}
