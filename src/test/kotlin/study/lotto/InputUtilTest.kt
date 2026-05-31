package study.lotto

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll

class InputUtilTest :
    ShouldSpec({

        beforeSpec {
            mockkStatic(::readln)
        }

        afterSpec {
            unmockkAll()
        }

        context("단일 숫자 입력 검증") {
            should("getPositiveIntInput은 양수가 들어오면 그대로 반환한다.") {
                every { readln() } returns "5"
                InputUtil.getPositiveIntInput() shouldBe 5
            }

            should("getPositiveIntInput은 문자가 들어오거나 음수면 다음 올바른 양수가 올 때까지 재귀 호출한다.") {
                every { readln() } returns "abc" andThen "-1" andThen "7"
                InputUtil.getPositiveIntInput() shouldBe 7
            }

            should("getZeroWithPositiveIntInput은 0이나 양수가 들어오면 그대로 반환한다.") {
                every { readln() } returns "0"
                InputUtil.getZeroWithPositiveIntInput() shouldBe 0
            }

            should("getZeroWithPositiveIntInput은 음수가 들어오면 올바른 값이 올 때까지 재귀 호출한다.") {
                every { readln() } returns "abc" andThen "-5" andThen "10"
                InputUtil.getZeroWithPositiveIntInput() shouldBe 10
            }
        }

        context("다중 숫자 입력 검증") {
            should("getPositiveIntInputWithSpace는 공백으로 구분된 양수 리스트를 반환한다.") {
                every { readln() } returns "1 2 3 4 5 6"
                val result = InputUtil.getPositiveIntInputWithSpace(6, 6)
                result shouldBe listOf(1, 2, 3, 4, 5, 6)
            }

            should("getPositiveIntInputWithSpace는 입력 개수가 부족하거나 조건에 맞지 않으면 올바른 입력을 다시 받는다.") {
                every { readln() } returns "abc def" andThen "-1 2 3 4 5 6" andThen "1 2 3" andThen "10 20 30 40 50 45"
                val result = InputUtil.getPositiveIntInputWithSpace(6, 6)
                result shouldBe listOf(10, 20, 30, 40, 50, 45)
            }

            should("getZeroWithPositiveIntInputWithSpace는 0을 포함한 숫자 리스트를 반환한다.") {
                every { readln() } returns "0 1 2"
                val result = InputUtil.getZeroWithPositiveIntInputWithSpace(3, 3)
                result shouldBe listOf(0, 1, 2)
            }

            should("getZeroWithPositiveIntInputWithSpace는 잘못된 입력(문자, 음수, 개수 부족) 시 올바른 입력을 다시 받는다.") {
                every { readln() } returns "a b c" andThen "-1 1 2" andThen "1 2" andThen "0 1 2"
                val result = InputUtil.getZeroWithPositiveIntInputWithSpace(3, 3)
                result shouldBe listOf(0, 1, 2)
            }
        }

        context("로또 관련 비즈니스 입력 검증") {
            should("구매할 티켓 수를 입력받을 때 잔액에 딱 맞게 구매하면 예외 없이 수량을 반환한다.") {
                every { readln() } returns "3"
                val result = InputUtil.receiveTotalTicketCount(price = 1000, balance = 3000L)
                result shouldBe 3
            }

            should("구매할 티켓 수를 입력받을 때 잔액이 부족하면 IllegalArgumentException이 발생한다.") {
                every { readln() } returns "5"
                shouldThrow<IllegalArgumentException> {
                    InputUtil.receiveTotalTicketCount(price = 1000, balance = 3000L)
                }
            }

            should("자동, 수동, 반자동 수량의 합이 총 티켓 수와 일치하면 StrategyCounts 객체를 반환한다.") {
                every { readln() } returns "2 2 1"
                val result = InputUtil.receiveStrategyCounts(totalCount = 5)
                result.auto shouldBe 2
                result.manual shouldBe 2
                result.semi shouldBe 1
            }

            should("자동, 수동, 반자동 수량의 합이 총 티켓 수와 맞지 않으면 IllegalArgumentException이 발생한다.") {
                every { readln() } returns "1 1 1"
                shouldThrow<IllegalArgumentException> {
                    InputUtil.receiveStrategyCounts(totalCount = 5)
                }
            }

            should("공백으로 구분된 로또 번호를 받아 LotteryNumber 객체 리스트로 올바르게 변환한다.") {
                every { readln() } returns "5 11 23 34 40 44"
                val lotteryNumbers = InputUtil.receiveLotteryNumbers()
                lotteryNumbers.size shouldBe 6
                lotteryNumbers[0] shouldBe LotteryNumber(5)
                lotteryNumbers[5] shouldBe LotteryNumber(44)
            }

            should("반자동 번호 입력 시 지정된 범위 개수의 LotteryNumber 객체 리스트를 변환한다.") {
                every { readln() } returns "1 2 3"
                val semiAutoNumbers = InputUtil.receiveSemiAutoNumbers()
                semiAutoNumbers.size shouldBe 3
                semiAutoNumbers[0] shouldBe LotteryNumber(1)
            }
        }
    })
