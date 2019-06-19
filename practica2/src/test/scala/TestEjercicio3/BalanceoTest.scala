package TestEjercicio3
import org.scalatest.FunSuite
import Ejercicio3.BalanceoCadenas

class ContadorParentesisTest extends FunSuite {
  // Prueba
  test("chequear balance: '(if (zero? x) max (/ 1 x))' esta balanceada") {
    assert(BalanceoCadenas.chequearBalance("(if (zero? x) max (/ 1 x))".toList))
  }
  // Prueba
  test("chequear balance: 'Te lo dije ...' esta balanceada") {
    assert(BalanceoCadenas.chequearBalance("Te lo dije (eso esta (todavia) hecho))".toList))
  }
  // Prueba
  test("chequear balance: ':-)' no esta balanceada") {
    assert(!BalanceoCadenas.chequearBalance(":-)".toList))
  }
  // Prueba
  test("chequear balance: no basta con contar sin mas") {
    assert(!BalanceoCadenas.chequearBalance("())(".toList))
  }
  // Prueba
  test("(if (a > b) (b/a) else (a/(b*b)))"){
    assert(BalanceoCadenas.chequearBalance("(if (a > b) (b/a) else (a/(b*b)))".toList))
  }
  // Prueba
  test("(ccc(ccc)cc((ccc(c))))"){
    assert(BalanceoCadenas.chequearBalance("(ccc(ccc)cc((ccc(c))))".toList))
  }
  // Prueba
  test("(if (a > b) b/a) else (a/(b*b)))"){
    assert(!BalanceoCadenas.chequearBalance("(if (a > b) b/a) else (a/(b*b)))".toList))
  }
  // Prueba
  test("(ccc(ccccc((ccc(c))))"){
    assert(!BalanceoCadenas.chequearBalance("(ccc(ccccc((ccc(c))))".toList))
  }
  // Prueba
  test("())()())"){
    assert(!BalanceoCadenas.chequearBalance("())()())".toList))
  }

}