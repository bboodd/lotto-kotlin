package study.lotto

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class WalletTest :
    ShouldSpec({
        context("Wallet 증감") {
            should("잔액이 증가한다") {
                val wallet = Wallet()
                wallet.deposit(1000L)
                wallet.balance shouldBe 1000L
            }

            should("입금액이 0보다 작을 시 예외가 발생한다") {
                val wallet = Wallet()
                val exception = shouldThrow<IllegalArgumentException> { wallet.deposit(-100L) }
                exception.message shouldBe "입금액은 0보다 커야 합니다."
            }
        }

        context("Wallet 차감") {
            should("잔액이 차감된다") {
                val wallet = Wallet()
                wallet.deposit(1000L)
                wallet.pay(500L)
                wallet.balance shouldBe 500L
            }

            should("차감액이 잔액보다 클 시 예외가 발생한다") {
                val wallet = Wallet()
                wallet.deposit(1000L)
                val exception = shouldThrow<IllegalArgumentException> { wallet.pay(1500L) }
                exception.message shouldBe "잔액이 부족합니다."
            }
        }
    })
