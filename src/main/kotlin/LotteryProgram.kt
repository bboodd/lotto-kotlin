package com.www

class LotteryProgram {
    companion object {
        private const val LOTTERY_PRICE = 1000
        private const val TICKET_STRATEGY_INPUT = 3
    }

    var balance = 0L
    val tickets: MutableList<LotteryTicket> = mutableListOf()
    var lastRound = (LotteryRepository.loadLast()?.round ?: 0) + 1

    fun start() {
        println("\n***********************************************")
        println("*****************Lottery started***************")
        println("***********************************************")
        entry()
    }

    fun entry() {
        println("\n현재 잔액은 ${balance}원 입니다.")
        println("현재 회차는 ${lastRound}회 입니다")
        println("진행을 원하시는 번호를 입력 해 주세요.")
        println("0. 프로그램 종료 1. 입금 2. 로또 구매 3. 이전 회차 당첨 번호 확인 4. ${lastRound}회차 종료 및 로또 당첨 확인")
        try {
            val input = getZeroWithPositiveIntInput()
            if (input !in 0..5) throw Exception()

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
        val round = getZeroWithPositiveIntInput()
        if (round == lastRound) {
            println("현재 회차는 아직 확인 하실 수 없습니다")
        } else if (round > lastRound) {
            println("존재하지 않는 회차입니다.")
        } else {
            val findHistory = LotteryRepository.loadByRound(round)
            println("${findHistory?.round}회차 번호")
            println("[" + findHistory?.numbers?.joinToString() + "]" + " 보너스: ${findHistory?.bonus}")
        }
        entry()
    }

    fun deposit() {
        println("얼마를 입급하시겠습니까?")
        try {
            val input = getZeroWithPositiveIntInput()
            balance += input
            entry()
        } catch (e: Exception) {
            println("잘못된 입력 입니다 다시 입력 해주세요")
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
        println("로또를 구매합니다. 1장에 ${LOTTERY_PRICE}원 / 현재 잔액 ${balance}원")
        println("구매할 총 장수를 입력하세요")

        try {
            val totalCount = getPositiveIntInput()
            val totalPrice = totalCount * LOTTERY_PRICE

            if (totalPrice > balance) {
                println("잔액이 부족합니다.")
                return buyTickets()
            }

            println("자동, 수동, 반자동 장수를 차례대로 입력하세요 (예: 2 1 1):")
            val (auto, manual, semi) =
                getZeroWithPositiveIntInputWithSpace(
                    TICKET_STRATEGY_INPUT,
                    TICKET_STRATEGY_INPUT
                )

            if (auto + manual + semi != totalCount) {
                println("입력한 장수의 합이 총 구매 장수와 다릅니다.")
                return buyTickets()
            }

            issueTickets(AutoTicketIssueStrategy(), auto)
            issueTickets(ManualTicketIssueStrategy(), manual)
            issueTickets(SemiAutoTicketIssueStrategy(), semi)

            balance -= totalPrice
            println("티켓이 발급되었습니다. / 남은 잔액 ${balance}원")
            entry()
        } catch (e: Exception) {
            println("잘못된 입력입니다 다시 입력해주세요")
            buyTickets()
        }
    }

    private fun issueTickets(
        strategy: TicketIssueStrategy,
        count: Int
    ) {
        repeat(count) {
            val ticket = strategy.issueTicket()
            tickets.add(ticket)
        }
    }
}

fun getPositiveIntInput(): Int {
    val input = readln().toIntOrNull()
    return if (input != null && input > 0) {
        input
    } else {
        println("양수를 입력해주세요")
        getPositiveIntInput()
    }
}

fun getZeroWithPositiveIntInput(): Int {
    val input = readln().toIntOrNull()
    return if (input != null && input >= 0) {
        input
    } else {
        println("양수를 입력해주세요")
        getZeroWithPositiveIntInput()
    }
}

fun getZeroWithPositiveIntInputWithSpace(
    minSize: Int,
    maxSize: Int
): List<Int> {
    val line = readln()
    val parts = line.split(" ").filter { it.isNotBlank() }
    val numbers = parts.mapNotNull { it.toIntOrNull() }

    return if (parts.size != numbers.size || numbers.any { it < 0 } || numbers.size !in minSize..maxSize) {
        println("유효하지 않은 입력입니다. 다시 입력해주세요")
        getZeroWithPositiveIntInputWithSpace(minSize, maxSize)
    } else {
        numbers
    }
}

interface TicketIssueStrategy {
    fun issueTicket(): LotteryTicket
}

class AutoTicketIssueStrategy : TicketIssueStrategy {
    override fun issueTicket(): LotteryTicket {
        val ticket = LotteryTicket.generate()
        println("[자동] $ticket")
        return ticket
    }
}

class ManualTicketIssueStrategy : TicketIssueStrategy {
    override fun issueTicket(): LotteryTicket {
        println("6개의 번호를 입력해 주세요 (1-45 사이의 숫자를 띄어쓰기로 구분하여 입력 해 주세요)")
        return try {
            val input = getZeroWithPositiveIntInputWithSpace(LotteryTicket.TICKET_SIZE, LotteryTicket.TICKET_SIZE)
            val ticket = LotteryTicket(input.map { LotteryNumber(it) })
            println("[수동] $ticket")
            return ticket
        } catch (e: Exception) {
            println("잘못된 입력입니다 다시 입력해주세요")
            issueTicket()
        }
    }
}

class SemiAutoTicketIssueStrategy : TicketIssueStrategy {
    override fun issueTicket(): LotteryTicket {
        return try {
            println("수동으로 선택할 번호들을 띄어쓰기로 구분하여 입력해 주세요 1개~6개  (나머지 자동)")
            val input =
                getZeroWithPositiveIntInputWithSpace(
                    LotteryTicket.SEMI_AUTO_MIN_MANUAL_TICKET_SIZE,
                    LotteryTicket.SEMI_AUTO_MAX_MANUAL_TICKET_SIZE
                )
            val ticket = LotteryTicket.generateSemiAuto(input.map { LotteryNumber(it) })
            println("[반자동] $ticket")
            return ticket
        } catch (e: Exception) {
            println("잘못된 입력입니다 다시 입력해주세요")
            issueTicket()
        }
    }
}
