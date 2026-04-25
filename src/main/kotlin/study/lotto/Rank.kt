package study.lotto

enum class Rank(
    val matchCount: Int,
    val prize: Int,
    val comment: String,
) {
    FIRST(6, 2100000000, "1등"),
    SECOND(5, 60000000, "2등"),
    THIRD(5, 1500000, "3등"),
    FOURTH(4, 50000, "4등"),
    FIFTH(3, 5000, "5등"),
    LOSE(0, 0, "꽝"),
    ;

    companion object {
        fun valueOf(
            matchCount: Int,
            matchBonus: Boolean,
        ): Rank =
            when (matchCount) {
                FIRST.matchCount -> FIRST
                SECOND.matchCount -> if (matchBonus) SECOND else THIRD
                FOURTH.matchCount -> FOURTH
                FIFTH.matchCount -> FIFTH
                else -> LOSE
            }
    }
}
