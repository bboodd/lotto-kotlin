package com.www

class LotteryProgram {
    companion object {
        private const val NUMBER_OF_TICKETS = 6
        private const val LOTTERY_PRICE = 1000
        private const val MANUAL = 1
        private const val AUTO = 2
        private const val SEMI_AUTO = 3
    }

    var balance = 0L
    val tickets: MutableList<LotteryTicket> = mutableListOf()
    var lastRound = (LotteryRepository.loadLast()?.round ?: 0) + 1

    fun start() {
        println("\n***********************************************")
        println("*****************Lottery started*************")
        println("***********************************************\n")
        entry()
    }

    fun entry() {
        println("현재 잔액은 ${balance}원 입니다.")
        println("현재 회차는 ${lastRound}회 입니다")
        println("진행을 원하시는 번호를 입력 해 주세요.")
        println("0.시스템 종료 1.입금 2.로또 구매 3.이전 회차 당첨 번호 확인 4.$lastRound 회차 종료 및 로또 당첨 확인")
        try {
            val input = readln().toInt()
            when (input) {
                0 -> return
                1 -> deposit()
                2 -> buyTickets()
                3 -> findLotteryHistoryByRound()
                4 -> checkWinning()
                else -> entry()
            }
        } catch (e: Exception) {
            println("잘못된 입력 입니다 다시 입력 해 주세요.")
            entry()
        }
    }

    fun findLotteryHistoryByRound() {
        println("몇 회차 번호를 확인 하시겠습니까?")
        val round = readln().toInt()
        if (round <= 0) {
            println("잘못된 입력입니다.")
        } else if (round > lastRound) {
            println("존재하지 않는 회차입니다.")
        } else {
            val findHistory = LotteryRepository.loadByRound(round)
            println("${findHistory?.round} 회차 번호")
            println(findHistory?.numbers?.joinToString() + "보너스: ${findHistory?.bonus}")
        }
        entry()
    }

    fun deposit() {
        println("얼마를 입급하시겠습니까?")
        try {
            val input = readln().toInt()
            balance += input
            entry()
        } catch (e: Exception) {
            println("숫자만 입력 해 주세요")
            // TODO: 추가 유효성검사
            deposit()
        }
    }

    fun checkWinning() {
        println("$lastRound 회차 로또 당첨을 확인합니다.")
        println("구매한 총 복권 ${tickets.size}개")

        val winningLottery = WinningLottery.generate()
        println("당첨번호: ${winningLottery.ticket} 보너스 번호: ${winningLottery.bonus}")

        var totalWinningAmount = 0L
        for (ticket in tickets) {
            val rank = winningLottery.match(ticket)
            totalWinningAmount += rank.prize

            println("구매한 번호: $ticket -> ${rank.comment}")
        }

        LotteryRepository.save(winningLottery.ticket.numbers.map { it.number }, winningLottery.bonus.number)
        lastRound += 1
        println("총 당첨금액: $totalWinningAmount")

        entry()
    }

    fun buyTickets() {
        println("로또를 구매합니다. 1장에 1000원")
        println("현재 잔액 ${balance}원")
        println("몇 장을 구매하시겠습니까? 숫자를 입력 해 주세요(0.취소 1~100 사이로 입력 해 주세요")
        try {
            val input = readln().toInt()
            if (input == 0) {
                entry()
            } else if (input * LOTTERY_PRICE > balance) {
                println("잔액이 부족합니다.")
                buyTickets()
            } else {
                println("자동을 몇 장 구매하시겠습니까?")
                val autoTicket = readln().toInt()
                println("수동을 몇 장 구매하시겠습니까?")
                val manualTicket = readln().toInt()
                println("반자동을 몇 장 구매하시겠습니까?")
                val semiAutoTicket = readln().toInt()

                println("총 구매 금액: ${input * LOTTERY_PRICE}원")
                println("총 자동: ${autoTicket}장 수동: ${manualTicket}장 반자동: ${semiAutoTicket}장")

                balance -= input * LOTTERY_PRICE

                println("자동을 ${autoTicket}장 구입합니다")
                issueTicket(AUTO, autoTicket)
                println("수동을 ${manualTicket}장 구입합니다")
                issueTicket(MANUAL, manualTicket)
                println("반자동을 ${semiAutoTicket}장 구입합니다")
                issueTicket(SEMI_AUTO, semiAutoTicket)

                println("티켓이 발급되었습니다.")
                println("남은 잔액 $${balance}원")
            }
        } catch (e: Exception) {
            println("숫자만 입력 해 주세요")
            buyTickets()
        }
    }

    fun issueTicket(
        type: Int,
        count: Int
    ) {
        if (count == 0) return
        for (i in 0..count) {
            when (type) {
                MANUAL -> tickets.add(generateManualTicket(NUMBER_OF_TICKETS))
                AUTO -> tickets.add(generateAutoTicket(NUMBER_OF_TICKETS))
                SEMI_AUTO -> tickets.add(generateSemiAutoTicket())
            }
        }
    }

    private fun generateAutoTicket(count: Int): List<Int> = (1..45).shuffled().take(count).sorted()

    private fun generateManualTicket(count: Int): List<Int> {
        try {
            print("$count 개의 번호를 입력 해 주세요(1-45 사이의 숫자를 띄어쓰기로 구분하여 입력 해 주세요)")
            val input = readln().split(" ").map { it.toInt() }
            return input
        } catch (e: Exception) {
            println("잘못된 입력 입니다 다시 입력 해 주세요")
            return generateManualTicket(count)
        }
    }

    private fun generateSemiAutoTicket(): List<Int> {
        var autoCount = 0
        var manualCount = 0

        try {
            println("자동으로 선택 할 번호의 개수를 입력 해 주세요.")
            autoCount = readln().toInt()
            println("수등으로 선택 할 번호의 개수를 입력 해 주세요")
            manualCount = readln().toInt()
            val autoTicket = generateAutoTicket(autoCount)
            val manualTicket = generateManualTicket(manualCount)
            return autoTicket + manualTicket
        } catch (e: Exception) {
            println("잘못된 입력입니다 다시 입력 해 주세요")
            return generateSemiAutoTicket()
        }
    }
}
