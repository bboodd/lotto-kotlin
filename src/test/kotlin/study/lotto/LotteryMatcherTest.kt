package study.lotto

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class LotteryMatcherTest :
    ShouldSpec({
        fun createLotteryTicket(vararg numbers: Int): LotteryTicket =
            LotteryTicket(
                numbers
                    .map {
                        LotteryNumber(it)
                    }.toSortedSet(),
            )

        context("LotteryMatcher.match 등수 판별 검증") {
            val winningTicket = createLotteryTicket(1, 2, 3, 4, 5, 6)
            val bonusNumber = LotteryNumber(7)
            val winningLottery = WinningLottery(winningTicket, bonusNumber)

            val contexts =
                listOf(
                    createLotteryTicket(1, 2, 3, 4, 5, 6) to Rank.FIRST,
                    createLotteryTicket(1, 2, 3, 4, 5, 7) to Rank.SECOND,
                    createLotteryTicket(1, 2, 3, 4, 5, 8) to Rank.THIRD,
                    createLotteryTicket(1, 2, 3, 4, 8, 9) to Rank.FOURTH,
                    createLotteryTicket(1, 2, 3, 8, 9, 10) to Rank.FIFTH,
                    createLotteryTicket(1, 2, 8, 9, 10, 11) to Rank.LOSE, // 2개 일치
                    createLotteryTicket(1, 8, 9, 10, 11, 12) to Rank.LOSE, // 1개 일치
                    createLotteryTicket(8, 9, 10, 11, 12, 13) to Rank.LOSE, // 0개 일치
                )

            for ((ticket, expectedRank) in contexts) {
                val matchCount = ticket.getMatchCount(winningLottery.ticket)
                val hasBonus = ticket.getNumbers().contains(bonusNumber)

                should("당첨 번호 ${matchCount}개 일치하고 보너스 번호 일치 여부가 ${hasBonus}이면 ${expectedRank.comment}을 반환한다.") {
                    val result = LotteryMatcher.match(winningLottery, ticket)
                    result shouldBe expectedRank
                }
            }
        }
    })
