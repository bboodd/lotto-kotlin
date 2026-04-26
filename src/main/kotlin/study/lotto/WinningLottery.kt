package study.lotto

data class WinningLottery(
    val ticket: LotteryTicket,
    val bonus: LotteryNumber,
) {
    init {
        require(!ticket.numbers.contains(bonus)) { "보너스 번호는 당첨 번호와 중복될 수 없습니다." }
    }
}
