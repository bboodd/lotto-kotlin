package study.lotto

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class MenuOptionTest :
    ShouldSpec({
        context("MenuOption.from 검증") {
            should("0부터 4까지의 유효한 값이 들어오면 알맞은 MenuOption을 반환한다.") {
                MenuOption.from(0) shouldBe MenuOption.EXIT
                MenuOption.from(1) shouldBe MenuOption.DEPOSIT
                MenuOption.from(2) shouldBe MenuOption.BUY_TICKET
                MenuOption.from(3) shouldBe MenuOption.FIND_HISTORY
                MenuOption.from(4) shouldBe MenuOption.CHECK_WINNING
            }

            should("정의되지 않은 메뉴 번호가 들어오면 null을 반환한다.") {
                MenuOption.from(-1).shouldBeNull()
                MenuOption.from(5).shouldBeNull()
                MenuOption.from(999).shouldBeNull()
            }
        }
    })
