package study.lotto

data class WinningLottery(
    val ticket: LotteryTicket,
    val bonus: LotteryNumber,
) {
    init {
        require(!ticket.numbers.contains(bonus)) { OutputUtil.printBonusNumberIsDoNotInTicketNumber() }
    }
}
