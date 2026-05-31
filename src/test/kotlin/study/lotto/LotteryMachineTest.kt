package study.lotto

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class LotteryMachineTest :
    ShouldSpec({
        context("LotteryMachine 티켓 발급 검증") {

            should("전달받은 발급 전략(Strategy)을 실행하여 로또 티켓을 반환한다.") {
                val expectedNumbers = listOf(1, 2, 3, 4, 5, 6).map { LotteryNumber(it) }
                val strategy = ManualTicketIssueStrategy(expectedNumbers)

                val ticket = LotteryMachine.issue(strategy)

                val ticketNumbers = ticket.getNumbers().toList()

                ticketNumbers.size shouldBe LotteryTicket.TICKET_SIZE
                ticketNumbers shouldBe expectedNumbers
            }
        }
    })
