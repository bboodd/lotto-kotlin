package study.lotto

import study.lotto.InputUtil.getZeroWithPositiveIntInput

class LotteryProgram {
    companion object {
        private const val LOTTERY_PRICE = 1000
    }

    var wallet = Wallet()
    val tickets: MutableList<LotteryTicket> = mutableListOf()
    var lastRound = (LotteryRepository.loadLast()?.round ?: 0) + 1

    fun start() {
        OutputUtil.printStartMessage()
        entry()
    }

    fun entry() {
        OutputUtil.printMenu(wallet.balance, lastRound)
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
            OutputUtil.printInvalidInput()
            entry()
        }
    }

    fun findLotteryHistoryByRound() {
        OutputUtil.printRoundInputPrompt()
        val round = getZeroWithPositiveIntInput()
        if (round == lastRound) {
            OutputUtil.printRoundNotAvailable()
        } else if (round > lastRound) {
            OutputUtil.printRoundNotFound()
        } else {
            val findHistory = LotteryRepository.loadByRound(round)
            OutputUtil.printHistory(findHistory)
        }
        entry()
    }

    fun deposit() {
        OutputUtil.printDepositPrompt()
        try {
            val input = getZeroWithPositiveIntInput()
            wallet.deposit(input.toLong())
            entry()
        } catch (e: Exception) {
            OutputUtil.printInvalidInput()
            deposit()
        }
    }

    fun checkWinning() {
        OutputUtil.printWinningHeader(lastRound, tickets.size)

        val winningLottery = LotteryTicketGenerator.generateWinningLottery()
        OutputUtil.printWinningNumbers(winningLottery)

        var totalWinningAmount = 0L
        for (ticket in tickets) {
            val rank = LotteryMatcher.match(winningLottery, ticket)
            totalWinningAmount += rank.prize

            OutputUtil.printTicketResult(ticket, rank)
        }

        LotteryRepository.save(winningLottery.ticket.numbers.map { it.number }, winningLottery.bonus.number)
        lastRound += 1
        OutputUtil.printTotalPrize(totalWinningAmount)

        entry()
    }

    fun buyTickets() {
        try {
            val totalCount = InputUtil.receiveTotalTicketCount(LOTTERY_PRICE, wallet.balance)
            val totalPrice = (totalCount * LOTTERY_PRICE).toLong()

            val counts = InputUtil.receiveStrategyCounts(totalCount)

            wallet.pay(totalPrice)
            tickets.addAll(LotteryMachine.issue(counts))

            OutputUtil.printIssued(wallet.balance)
            entry()
        } catch (e: Exception) {
            OutputUtil.printInvalidInput()
            buyTickets()
        }
    }
}
