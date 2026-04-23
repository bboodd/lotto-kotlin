package com.www

data class LotteryNumber(
    val number: Int
) {
    companion object {
        const val MIN = 1
        const val MAX = 45

        fun generate(): LotteryNumber = LotteryNumber((MIN..MAX).random())
    }

    init {
        require(number in MIN..MAX) { "로또 번호는 $MIN 과 $MAX 사이여야 합니다." }
    }
}
