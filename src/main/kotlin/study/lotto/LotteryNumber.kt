package study.lotto

@JvmInline
value class LotteryNumber(
    val number: Int,
) : Comparable<LotteryNumber> {
    init {
        require(number in MIN..MAX) { "로또 번호는 $MIN 과 $MAX 사이여야 합니다." }
    }

    override fun toString(): String = number.toString()

    override fun compareTo(other: LotteryNumber): Int = number.compareTo(other.number)

    companion object {
        const val MIN = 1
        const val MAX = 45
        val ALL_NUMBERS = (MIN..MAX).map { LotteryNumber(it) }
    }
}
