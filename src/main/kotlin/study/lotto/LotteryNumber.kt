package study.lotto

@JvmInline
value class LotteryNumber(
    val number: Int,
) : Comparable<LotteryNumber> {
    companion object {
        const val MIN = 1
        const val MAX = 45
    }

    init {
        require(number in MIN..MAX) { OutputUtil.printLotteryNumberNotInRange(MIN, MAX) }
    }

    override fun toString(): String = number.toString()

    override fun compareTo(other: LotteryNumber): Int = number.compareTo(other.number)
}
