package study.lotto

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class RankTest :
    ShouldSpec({
        context("Rank.valueOf 등수 판별 검증") {

            should("당첨 번호 일치 개수와 보너스 번호 일치 여부에 맞는 정확한 등수를 반환한다.") {
                Rank.valueOf(matchCount = 6, matchBonus = false) shouldBe Rank.FIRST

                Rank.valueOf(matchCount = 5, matchBonus = true) shouldBe Rank.SECOND

                Rank.valueOf(matchCount = 5, matchBonus = false) shouldBe Rank.THIRD

                Rank.valueOf(matchCount = 4, matchBonus = false) shouldBe Rank.FOURTH

                Rank.valueOf(matchCount = 3, matchBonus = false) shouldBe Rank.FIFTH
            }

            should("당첨 조건에 미치지 못하는 일치 개수면 LOSE(꽝)를 반환한다.") {
                Rank.valueOf(matchCount = 2, matchBonus = false) shouldBe Rank.LOSE
                Rank.valueOf(matchCount = 1, matchBonus = true) shouldBe Rank.LOSE
                Rank.valueOf(matchCount = 0, matchBonus = false) shouldBe Rank.LOSE
            }

            should("enum에 정의되지 않은 조건의 조합이 들어오면 LOSE를 반환한다.") {
                Rank.valueOf(matchCount = 4, matchBonus = true) shouldBe Rank.LOSE

                Rank.valueOf(matchCount = 6, matchBonus = true) shouldBe Rank.LOSE
            }
        }
    })
