package study.lotto

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class RankTest :
    ShouldSpec({
        context("Rank.valueOf 등수 판별 검증") {

            val winningCases =
                listOf(
                    Triple(6, false, Rank.FIRST),
                    Triple(5, true, Rank.SECOND),
                    Triple(5, false, Rank.THIRD),
                    Triple(4, false, Rank.FOURTH),
                    Triple(3, false, Rank.FIFTH),
                )

            winningCases.forEach { (matchCount, matchBonus, expectedRank) ->
                should("일치 개수 ${matchCount}개, 보너스 일치 여부 ${matchBonus}일 때 ${expectedRank.comment}를 반환한다.") {
                    Rank.valueOf(matchCount, matchBonus) shouldBe expectedRank
                }
            }

            val loseCases =
                listOf(
                    Triple(2, false, "당첨 조건에 미치지 못하는 일치 개수 (2개)"),
                    Triple(1, true, "당첨 조건에 미치지 못하는 일치 개수 (1개)"),
                    Triple(0, false, "당첨 조건에 미치지 못하는 일치 개수 (0개)"),
                    Triple(4, true, "enum에 정의되지 않은 조합 (4개 일치 + 보너스 일치)"),
                    Triple(6, true, "enum에 정의되지 않은 조합 (6개 일치 + 보너스 일치)"),
                )

            loseCases.forEach { (matchCount, matchBonus, description) ->
                should("$description -> LOSE(꽝)를 반환한다.") {
                    Rank.valueOf(matchCount, matchBonus) shouldBe Rank.LOSE
                }
            }
        }
    })
