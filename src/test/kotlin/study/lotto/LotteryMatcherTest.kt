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

            should("당첨 번호 6개가 모두 일치하면 FIRST(1등)를 반환한다.") {
                val userTicket = createLotteryTicket(1, 2, 3, 4, 5, 6)

                val result = LotteryMatcher.match(winningLottery, userTicket)
                result shouldBe Rank.FIRST
            }

            should("당첨 번호 5개가 일치하고 보너스 번호가 일치하면 SECOND(2등)를 반환한다.") {
                val userTicket = createLotteryTicket(1, 2, 3, 4, 5, 7)

                val result = LotteryMatcher.match(winningLottery, userTicket)
                result shouldBe Rank.SECOND
            }

            should("당첨 번호 5개가 일치하고 보너스 번호가 불일치하면 THIRD(3등)를 반환한다.") {
                val userTicket = createLotteryTicket(1, 2, 3, 4, 5, 8)

                val result = LotteryMatcher.match(winningLottery, userTicket)
                result shouldBe Rank.THIRD
            }

            should("당첨 번호 4개가 일치하면 FOURTH(4등)를 반환한다.") {
                val userTicket = createLotteryTicket(1, 2, 3, 4, 8, 9)

                val result = LotteryMatcher.match(winningLottery, userTicket)
                result shouldBe Rank.FOURTH
            }

            should("당첨 번호 3개가 일치하면 FIFTH(5등)를 반환한다.") {
                val userTicket = createLotteryTicket(1, 2, 3, 8, 9, 10)

                val result = LotteryMatcher.match(winningLottery, userTicket)
                result shouldBe Rank.FIFTH
            }

            should("당첨 번호가 2개 이하로 일치하면 LOSE(꽝)를 반환한다.") {
                val loseCases =
                    listOf(
                        intArrayOf(1, 2, 8, 9, 10, 11), // 2개 일치
                        intArrayOf(1, 8, 9, 10, 11, 12), // 1개 일치
                        intArrayOf(8, 9, 10, 11, 12, 13), // 0개 일치
                    )

                loseCases.forEach { numbers ->
                    val userTicket = createLotteryTicket(*numbers)
                    LotteryMatcher.match(winningLottery, userTicket) shouldBe Rank.LOSE
                }
            }
        }
    })
