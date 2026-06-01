package study.lotto

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream

class LotteryProgramTest :
    ShouldSpec({
        val originalIn: InputStream = System.`in`
        val originalOut: PrintStream = System.out

        fun mockInput(vararg lines: String) {
            val inputString = lines.joinToString("\n")
            System.setIn(ByteArrayInputStream(inputString.toByteArray()))
        }

        fun captureOutput(block: () -> Unit): String {
            val outputStream = ByteArrayOutputStream()
            System.setOut(PrintStream(outputStream))
            try {
                block()
            } finally {
                System.setOut(originalOut)
            }
            return outputStream.toString().trim()
        }

        afterTest {
            System.setIn(originalIn)
            System.setOut(originalOut)
        }

        context("프로그램 메뉴 흐름 및 예외 제어 검증") {
            should("0번(종료)을 입력하면 entry 재귀 호출을 멈추고 프로그램이 종료된다.") {
                mockInput("0")
                val program = LotteryProgram()

                program.start()
            }

            should("잘못된 메뉴 번호 입력 시 에러 메시지를 출력하고 다시 입력을 받는다.") {
                mockInput("99", "0")
                val program = LotteryProgram()

                val output = captureOutput { program.start() }

                output shouldContain "잘못된 입력입니다 다시 입력해 주세요."
            }
        }

        context("입금 및 티켓 구매 검증") {
            should("입금 메뉴를 통해 지갑(Wallet)에 금액을 정상적으로 충전한다.") {
                mockInput("1", "5000", "0")
                val program = LotteryProgram()

                program.start()

                program.wallet.balance shouldBe 5000L
            }

            should("구매 메뉴를 통해 다양한 전략(자동, 수동, 반자동)으로 로또를 구매할 수 있다.") {
                mockInput(
                    "1",
                    "5000",
                    "2",
                    "3",
                    "1 1 1",
                    "1 2 3 4 5 6",
                    "10 20",
                    "0",
                )
                val program = LotteryProgram()

                program.start()

                program.wallet.balance shouldBe 2000L

                program.tickets.size shouldBe 3
            }
        }

        context("당첨 확인 및 내역 조회 시나리오 검증") {
            should("당첨 확인 시 티켓 결과를 판별하고 현재 회차(lastRound)를 증가시킨다.") {
                mockInput(
                    "1",
                    "1000",
                    "2",
                    "1",
                    "1 0 0",
                    "4",
                    "0",
                )
                val program = LotteryProgram()
                val initialRound = program.lastRound

                program.start()

                program.lastRound shouldBe initialRound + 1
            }

            should("이전 회차 조회 시 입력한 회차에 따라 알맞은 예외 메시지를 출력한다.") {
                val program = LotteryProgram()
                val currentRound = program.lastRound

                mockInput(
                    "3",
                    currentRound.toString(),
                    "3",
                    (currentRound + 1).toString(),
                    "0",
                )

                val output = captureOutput { program.start() }

                output shouldContain "현재 회차는 아직 확인 하실 수 없습니다"
                output shouldContain "존재하지 않는 회차입니다."
            }
        }
    })
