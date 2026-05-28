package study.lotto

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.io.File

class LotteryRepositoryTest :
    ShouldSpec({
        val testDir = "test"
        val testFileName = "lottery_history.csv"
        val testFile = File(testDir, testFileName)

        beforeSpec {
            LotteryRepository.directory = testDir
        }

        afterSpec {
            LotteryRepository.directory = ""
            if (testFile.exists()) testFile.delete()
        }

        beforeTest {
            if (testFile.exists()) testFile.delete()
        }

        afterTest {
            if (testFile.exists()) testFile.delete()
        }

        context("LotteryRepository 저장 및 조회") {
            should("1회차부터 순서대로 저장된다.") {
                LotteryRepository.save(listOf(1, 2, 3, 4, 5, 6), 7)
                LotteryRepository.save(listOf(8, 9, 10, 11, 12, 13), 14)
                val allHistory = LotteryRepository.loadAll()
                allHistory.size shouldBe 2

                allHistory[0].round shouldBe 1
                allHistory[0].numbers shouldBe listOf(1, 2, 3, 4, 5, 6)
                allHistory[0].bonus shouldBe 7

                allHistory[1].round shouldBe 2
                allHistory[1].numbers shouldBe listOf(8, 9, 10, 11, 12, 13)
                allHistory[1].bonus shouldBe 14
            }

            should("마지막 회차를 가져온다.") {
                LotteryRepository.save(listOf(1, 2, 3, 4, 5, 6), 7)
                LotteryRepository.save(listOf(21, 22, 23, 24, 25, 26), 27)

                val lastHistory = LotteryRepository.loadLast()

                lastHistory shouldNotBe null
                lastHistory?.round shouldBe 2
                lastHistory?.numbers shouldBe listOf(21, 22, 23, 24, 25, 26)
            }

            should("지정된 회차를 가져온다.") {
                LotteryRepository.save(listOf(1, 2, 3, 4, 5, 6), 7)
                LotteryRepository.save(listOf(11, 12, 13, 14, 15, 16), 17)

                val roundOne = LotteryRepository.loadByRound(1)
                roundOne shouldNotBe null
                roundOne?.bonus shouldBe 7

                val roundTwo = LotteryRepository.loadByRound(2)
                roundTwo shouldNotBe null
                roundTwo?.bonus shouldBe 17

                val roundThree = LotteryRepository.loadByRound(3)
                roundThree shouldBe null
            }
        }

        context("LotteryRepository 데이터 손상 예외처리") {
            should("잘못된 형식의 데이터가 있으면 건너뛴다.") {
                testFile.writeText("round,n1,n2,n3,n4,n5,n6,bonus\n")
                testFile.appendText("1,1,2,3,4,5,6,7\n")
                testFile.appendText("2,손상 데이터,7\n")
                testFile.appendText("3,10,11,12,13,14,15,16\n")

                val allHistory = LotteryRepository.loadAll()

                allHistory.size shouldBe 2
                allHistory[0].round shouldBe 1
                allHistory[1].round shouldBe 3
            }
        }
    })
