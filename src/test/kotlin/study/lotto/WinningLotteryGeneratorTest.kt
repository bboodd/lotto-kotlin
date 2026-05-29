package study.lotto

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class WinningLotteryGeneratorTest :
    ShouldSpec({
        context("WinningLotteryGenerator 생성") {

            should("generate 메서드는 당첨 번호와 중복되지 않는 보너스 번호를 가진 WinningLottery 객체를 반환한다.") {
                val winningLottery = WinningLotteryGenerator.generate()

                winningLottery shouldNotBe null

                val ticketNumbers = winningLottery.ticket.getNumbers()
                val bonusNumber = winningLottery.bonus

                ticketNumbers shouldNotContain bonusNumber
            }

            should("여러 번 생성해도 보너스 번호와 당첨 번호는 절대 중복되지 않는다.") {
                repeat(100) {
                    val winningLottery = WinningLotteryGenerator.generate()
                    val ticketNumbers = winningLottery.ticket.getNumbers()
                    val bonusNumber = winningLottery.bonus

                    ticketNumbers shouldNotContain bonusNumber
                }
            }
        }
    })
