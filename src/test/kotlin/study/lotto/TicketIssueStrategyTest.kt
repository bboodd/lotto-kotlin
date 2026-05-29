package study.lotto

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import java.util.SortedSet

class TicketIssueStrategyTest :
    ShouldSpec({
        fun createLotteryNumbers(vararg numbers: Int): List<LotteryNumber> =
            numbers
                .map {
                    LotteryNumber(it)
                }

        context("StrategyCounts 및 상수 검증") {
            should("StrategyCounts 데이터 클래스가 값을 올바르게 할당한다.") {
                val counts = StrategyCounts(auto = 5, manual = 2, semi = 3)

                counts.auto shouldBe 5
                counts.manual shouldBe 2
                counts.semi shouldBe 3
            }

            should("TicketIssueStrategy의 동반 객체 상수 검증.") {
                TicketIssueStrategy.NUMBER_OF_STRATEGIES shouldBe 3
                TicketIssueStrategy.SEMI_AUTO_MIN_MANUAL_TICKET_SIZE shouldBe 1
                TicketIssueStrategy.SEMI_AUTO_MAX_MANUAL_TICKET_SIZE shouldBe 5
            }
        }

        context("AutoTicketIssueStrategy (자동 발급) 검증") {
            should("정해진 사이즈(TICKET_SIZE)만큼의 랜덤한 로또 번호를 오름차순 정렬하여 발급한다.") {
                val strategy = AutoTicketIssueStrategy()
                val ticket = strategy.issueTicket()
                val numbers = ticket.getNumbers()

                numbers.size shouldBe LotteryTicket.TICKET_SIZE

                val sortedNumbers = numbers.sortedBy { it.getNumber() }
                numbers shouldBe sortedNumbers
            }
        }

        context("ManualTicketIssueStrategy (수동 발급) 검증") {
            should("사용자가 입력한 번호 그대로를 포함하며, 오름차순으로 정렬하여 티켓을 발급한다.") {
                val manualNumbers = createLotteryNumbers(45, 2, 18, 9, 30, 11)
                val strategy = ManualTicketIssueStrategy(manualNumbers)

                val ticket = strategy.issueTicket()
                val numbers = ticket.getNumbers()

                val expected = createLotteryNumbers(2, 9, 11, 18, 30, 45)
                numbers shouldBe expected
            }
        }

        context("SemiAutoTicketIssueStrategy (반자동 발급) 검증") {
            should("사용자가 고정한 번호를 포함하고, 나머지는 랜덤으로 채워 오름차순 정렬된 티켓을 발급한다.") {
                val fixedNumbers = createLotteryNumbers(45, 10, 20)
                val strategy = SemiAutoTicketIssueStrategy(fixedNumbers)

                val ticket = strategy.issueTicket()
                val numbers = ticket.getNumbers()

                numbers.size shouldBe LotteryTicket.TICKET_SIZE

                numbers shouldContainAll fixedNumbers

                val sortedNumbers = numbers.sortedBy { it.getNumber() }
                numbers shouldBe sortedNumbers
            }
        }
    })
