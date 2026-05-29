package study.lotto

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class WinningLotteryTest :
    ShouldSpec({
        context("WinningLottery 생성") {

            fun createLotteryTicket(vararg numbers: Int): LotteryTicket =
                LotteryTicket(
                    numbers
                        .map {
                            LotteryNumber(it)
                        }.toSortedSet(),
                )

            should("당첨 번호와 보너스 번호가 중복되지 않으면 정상적으로 객체가 생성된다.") {
                val ticket = createLotteryTicket(1, 2, 3, 4, 5, 6)
                val bonus = LotteryNumber(7)

                val winningLottery = WinningLottery(ticket, bonus)

                winningLottery.ticket shouldBe ticket
                winningLottery.bonus shouldBe bonus
            }

            should("보너스 번호가 당첨 번호에 포함되어 있으면 IllegalArgumentException이 발생한다.") {
                val ticket = createLotteryTicket(1, 2, 3, 4, 5, 6)
                val bonus = LotteryNumber(6)

                val exception =
                    shouldThrow<IllegalArgumentException> {
                        WinningLottery(ticket, bonus)
                    }

                exception.message shouldBe "보너스 번호는 당첨 번호와 중복될 수 없습니다."
            }
        }
    })
