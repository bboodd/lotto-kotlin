package study.lotto

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import study.lotto.LotteryNumber.Companion.ALL_NUMBERS
import java.util.SortedSet

class LotteryTicketTest :
    ShouldSpec({

        fun createLotteryNumbers(vararg numbers: Int): SortedSet<LotteryNumber> =
            numbers
                .map {
                    LotteryNumber(it)
                }.toSortedSet()

        context("LotteryTicket 생성") {
            should("6개의 번호로 생성된다.") {
                val numbers = createLotteryNumbers(1, 2, 3, 4, 5, 6)
                val ticket = LotteryTicket(numbers)
                ticket.toString() shouldBe "1, 2, 3, 4, 5, 6"
            }

            should("정렬되어 생성된다.") {
                val numbers = createLotteryNumbers(6, 5, 4, 3, 2, 1)
                val ticket = LotteryTicket(numbers)

                ticket.toString() shouldBe "1, 2, 3, 4, 5, 6"
            }

            should("번호가 6개 미만일시 예외가 발생한다.") {
                val lessNumbers = createLotteryNumbers(1, 2, 3, 4, 5)

                val exception =
                    shouldThrow<IllegalArgumentException> {
                        LotteryTicket(lessNumbers)
                    }
                exception.message shouldBe "로또 번호는 6개여야 합니다."
            }

            should("번호가 6개 초과일시 예외가 발생한다.") {
                val moreNumbers = createLotteryNumbers(1, 2, 3, 4, 5, 6, 7)

                val exception =
                    shouldThrow<IllegalArgumentException> {
                        LotteryTicket(moreNumbers)
                    }
                exception.message shouldBe "로또 번호는 6개여야 합니다."
            }
        }

        context("LotteryTicket.getMatchCount") {
            val baseTicket = LotteryTicket(createLotteryNumbers(1, 2, 3, 4, 5, 6))

            should("두 로또 티켓의 번호가 모두 일치하면 6을 반환한다.") {
                val targetTicket = LotteryTicket(createLotteryNumbers(1, 2, 3, 4, 5, 6))
                baseTicket.getMatchCount(targetTicket) shouldBe 6
            }

            should("일부 번호만 일치하는 경우 일치하는 개수를 반환한다.") {
                val targetTicket = LotteryTicket(createLotteryNumbers(1, 2, 3, 20, 30, 40)) // 1, 2, 3 일치
                baseTicket.getMatchCount(targetTicket) shouldBe 3
            }

            should("일치하는 번호가 하나도 없으면 0을 반환한다.") {
                val targetTicket = LotteryTicket(createLotteryNumbers(11, 12, 13, 14, 15, 16))
                baseTicket.getMatchCount(targetTicket) shouldBe 0
            }
        }
    })
