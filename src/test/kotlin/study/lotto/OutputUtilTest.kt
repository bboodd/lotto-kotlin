package study.lotto

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.SortedSet

class OutputUtilTest :
    ShouldSpec({
        fun captureOutput(block: () -> Unit): String {
            val originalOutput = System.out
            val outputStream = ByteArrayOutputStream()
            System.setOut(PrintStream(outputStream))
            try {
                block()
            } finally {
                System.setOut(originalOutput)
            }
            return outputStream.toString().trim()
        }

        fun createLotteryTicket(vararg numbers: Int): LotteryTicket =
            LotteryTicket(
                numbers
                    .map {
                        LotteryNumber(it)
                    }.toSortedSet(),
            )

        context("고정 메시지 출력 검증") {
            should("printStartMessage는 시작 환영 메시지를 출력한다.") {
                val output = captureOutput { OutputUtil.printStartMessage() }
                output shouldContain "Lottery started"
            }

            should("printInvalidInput은 에러 메시지를 출력한다.") {
                val output = captureOutput { OutputUtil.printInvalidInput() }
                output shouldBe "잘못된 입력입니다 다시 입력해 주세요."
            }

            should("printInsufficientBalance는 잔액 부족 메시지를 출력한다.") {
                val output = captureOutput { OutputUtil.printInsufficientBalance() }
                output shouldBe "잔액이 부족합니다."
            }

            should("printDepositPrompt는 입금 안내 메시지를 출력한다.") {
                val output = captureOutput { OutputUtil.printDepositPrompt() }
                output shouldBe "얼마를 입금하시겠습니까?"
            }

            should("printRoundInputPrompt는 회차 입력 안내 메시지를 출력한다.") {
                val output = captureOutput { OutputUtil.printRoundInputPrompt() }
                output shouldBe "몇 회차 번호를 확인 하시겠습니까?"
            }

            should("printStrategyPrompt는 전략별 구매 안내 메시지를 출력한다.") {
                val output = captureOutput { OutputUtil.printStrategyPrompt() }
                output shouldBe "자동, 수동, 반자동 장수를 차례대로 입력하세요 (예: 2 1 1):"
            }

            should("printCountMismatch는 장수 불일치 에러 메시지를 출력한다.") {
                val output = captureOutput { OutputUtil.printCountMismatch() }
                output shouldBe "입력한 장수의 합이 총 구매 장수와 다릅니다."
            }

            should("printRoundNotAvailable은 회차 확인 불가 메시지를 출력한다.") {
                val output = captureOutput { OutputUtil.printRoundNotAvailable() }
                output shouldBe "현재 회차는 아직 확인 하실 수 없습니다"
            }

            should("printRoundNotFound는 존재하지 않는 회차 메시지를 출력한다.") {
                val output = captureOutput { OutputUtil.printRoundNotFound() }
                output shouldBe "존재하지 않는 회차입니다."
            }

            should("printManualInputPrompt는 수동 번호 입력 안내 메시지를 출력한다.") {
                val output = captureOutput { OutputUtil.printManualInputPrompt() }
                output shouldContain "${LotteryTicket.TICKET_SIZE}개의 번호를 입력해 주세요"
                output shouldContain "${LotteryNumber.MIN}-${LotteryNumber.MAX} 사이의 숫자를"
            }

            should("printSemiAutoInManualInputPrompt는 반자동 입력 안내 메시지를 출력한다.") {
                val output = captureOutput { OutputUtil.printSemiAutoInManualInputPrompt() }
                output shouldContain "수동으로 선택할 번호들을 띄어쓰기로 구분하여 입력해 주세요"
                output shouldContain
                    "${TicketIssueStrategy.SEMI_AUTO_MIN_MANUAL_TICKET_SIZE}개~${TicketIssueStrategy.SEMI_AUTO_MAX_MANUAL_TICKET_SIZE}개"
            }
        }

        context("변수가 포함된 동적 메시지 출력 검증") {
            should("printMenu는 전달받은 잔액과 회차 정보를 포함하여 출력한다.") {
                val output = captureOutput { OutputUtil.printMenu(balance = 15000L, round = 5) }
                output shouldContain "현재 잔액은 15000원 입니다."
                output shouldContain "현재 회차는 5회 입니다"
                output shouldContain "프로그램 종료"
            }

            should("printBuyPrompt는 가격과 현재 잔액을 포함하여 출력한다.") {
                val output = captureOutput { OutputUtil.printBuyPrompt(price = 1000, balance = 5000L) }
                output shouldContain "1장에 1000원"
                output shouldContain "현재 잔액 5000원"
            }

            should("printIssued는 발급 완료 메시지와 남은 잔액을 출력한다.") {
                val output = captureOutput { OutputUtil.printIssued(remaining = 2000L) }
                output shouldBe "티켓이 발급되었습니다. / 남은 잔액 2000원"
            }

            should("printWinningHeader는 회차와 총 구매 개수를 출력한다.") {
                val output = captureOutput { OutputUtil.printWinningHeader(round = 7, count = 10) }
                output shouldContain "7 회차 로또 당첨을 확인합니다."
                output shouldContain "구매한 총 복권 10개"
            }

            should("printTotalPrize는 총 당첨 금액을 텍스트와 함께 출력한다.") {
                val output = captureOutput { OutputUtil.printTotalPrize(amount = 50000L) }
                output shouldBe "총 당첨금액: 50000"
            }

            should("printFileError는 에러 메시지를 포함하여 출력한다.") {
                val output = captureOutput { OutputUtil.printFileError("파일 없음") }
                output shouldBe "파일 관련 오류가 발생했습니다: 파일 없음"
            }
        }

        context("로또 관련 비즈니스 출력 검증") {

            should("printWinningNumbers는 당첨 번호와 보너스 번호를 출력한다.") {
                val dummyTicket = createLotteryTicket(1, 2, 3, 4, 5, 6)
                val winningLottery = WinningLottery(dummyTicket, LotteryNumber(7))

                val output = captureOutput { OutputUtil.printWinningNumbers(winningLottery) }
                output shouldContain "당첨번호:"
                output shouldContain "보너스 번호: 7"
            }

            should("printTicketResult는 구매한 번호와 등수 결과를 출력한다.") {
                val dummyTicket = createLotteryTicket(1, 2, 3, 4, 5, 6)
                val rank = Rank.FIRST

                val output = captureOutput { OutputUtil.printTicketResult(dummyTicket, rank) }
                output shouldContain "구매한 번호:"
                output shouldContain "-> ${rank.comment}"
            }

            should("printTicket은 티켓 번호를 출력한다.") {
                val dummyTicket = createLotteryTicket(1, 2, 3, 4, 5, 6)

                val output = captureOutput { OutputUtil.printTicket(dummyTicket) }
                output shouldContain "[$dummyTicket]"
            }

            should("printHistory는 전달된 히스토리 객체의 회차, 번호, 보너스를 출력한다.") {
                val history =
                    LotteryHistory(
                        round = 1,
                        numbers = listOf(1, 2, 3, 4, 5, 6),
                        bonus = 7,
                    )
                val output = captureOutput { OutputUtil.printHistory(history) }
                output shouldContain "1회차 번호"
                output shouldContain "보너스: 7"
            }

            should("printHistory는 null이 전달되면 null 관련 텍스트를 출력한다.") {
                val output = captureOutput { OutputUtil.printHistory(null) }
                output shouldContain "null회차 번호"
            }
        }
    })
