package study.lotto

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

class LotteryNumberTest :
    ShouldSpec({
        context("LotteryNumber 생성") {
            should("1과 45 사이의 숫자로 생성된다.") {
                LotteryNumber(1).getNumber() shouldBe 1
                LotteryNumber(45).getNumber() shouldBe 45
            }

            should("45 초과의 숫자의 경우 예외를 반환한다.") {
                val exception = shouldThrow<IllegalArgumentException> { LotteryNumber(LotteryNumber.MAX + 1) }
                exception.message shouldBe "로또 번호는 1 과 45 사이여야 합니다."
            }

            should("1 미만의 숫자의 경우 예외를 반환한다.") {
                val exception = shouldThrow<IllegalArgumentException> { LotteryNumber(LotteryNumber.MIN - 1) }
                exception.message shouldBe "로또 번호는 1 과 45 사이여야 합니다."
            }
        }

        context("LotteryNumber 검증") {
            should("equals") {
                LotteryNumber(1) shouldBe LotteryNumber(1)
            }

            should("compareTo") {
                LotteryNumber(45) shouldBeGreaterThan LotteryNumber(1)
            }

            should("toString") {
                LotteryNumber(1).toString() shouldBe "1"
            }

            should("hashCode") {
                LotteryNumber(1).hashCode() shouldBe LotteryNumber(1).hashCode()
            }

            should("ALL_NUMBERS는 1부터 45까지 총 45개의 숫자를 가진다.") {
                LotteryNumber.ALL_NUMBERS shouldHaveSize 45
                LotteryNumber.ALL_NUMBERS.first() shouldBe LotteryNumber(1)
                LotteryNumber.ALL_NUMBERS.last() shouldBe LotteryNumber(45)
            }
        }
    })
