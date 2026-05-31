package study.lotto

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class MenuOptionTest :
    ShouldSpec({
        context("MenuOption.from 검증") {

            val validCases =
                listOf(
                    0 to MenuOption.EXIT,
                    1 to MenuOption.DEPOSIT,
                    2 to MenuOption.BUY_TICKET,
                    3 to MenuOption.FIND_HISTORY,
                    4 to MenuOption.CHECK_WINNING,
                )

            validCases.forEach { (input, expectedOption) ->
                should("유효한 값 ${input}이(가) 들어오면 ${expectedOption.name}을(를) 반환한다.") {
                    MenuOption.from(input) shouldBe expectedOption
                }
            }

            val invalidCases = listOf(-1, 5, 999)

            invalidCases.forEach { input ->
                should("정의되지 않은 메뉴 번호 ${input}이(가) 들어오면 null을 반환한다.") {
                    MenuOption.from(input).shouldBeNull()
                }
            }
        }
    })
