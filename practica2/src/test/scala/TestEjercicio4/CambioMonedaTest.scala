package TestEjercicio4
import Ejercicio4.CambioMoneda

import org.scalatest.FunSuite

class CambioMonedaTest extends FunSuite{

  val monedas =  List(1,2,5,10)
  val monedasGrandes = List(10,20,50,100)
  
  test("cambio 80") {
    val cambio3:List[List[Int]] = CambioMoneda.contarCambiosPosibles(80,List(1,2,5,10))
    assert(cambio3.map(cambio => cambio.sum).filter(elementos => elementos != 80).toList.size == 0 )

  }

  test("cambio 0") {
    val cambio3:List[List[Int]] = CambioMoneda.contarCambiosPosibles(0,List(1,2,5,10))
    assert(cambio3.map(cambio => cambio.sum).filter(elementos => elementos != 0).toList.size == 0)

  }

  test("cambio imposible") {
    val cambio3:List[List[Int]] = CambioMoneda.contarCambiosPosibles(1,List(2,5,10))
    assert(cambio3.map(cambio => cambio.sum).size == 0 )

  }

  test("cambio de 10") {
    val cambio3:List[List[Int]] = CambioMoneda.contarCambiosPosibles(10,List(1,2,5,10))
    assert(cambio3.map(cambio => cambio.sum).filter(elementos => elementos != 10).toList.size == 0 )

  }

  test("cambio de 1") {
    val cambio3:List[List[Int]] = CambioMoneda.contarCambiosPosibles(1,List(1,2,5,10))
    assert(cambio3.map(cambio => cambio.sum).filter(elementos => elementos != 1).toList.size == 0 )

  }
  
}
