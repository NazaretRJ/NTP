package Ejercicio4

import scala.collection.mutable.ListBuffer

object CambioMoneda {

def contarCambiosPosibles(cantidad:Int,monedas:List[Int],cambiosPosibles:List[List[Int]],actual:List[Int]): List[List[Int]] ={

  if(monedas.isEmpty) cambiosPosibles
  else{
    if(cantidad == 0) actual :: cambiosPosibles

    else{
      if(cantidad > 0){
        val moneda = monedas.head
        /* miramos si con esa moneda podemos seguir buscando solución
        * y buscamos también posibles cambios sin esa moneda
        * */
        contarCambiosPosibles(cantidad - moneda,monedas,cambiosPosibles,moneda::actual) ::: contarCambiosPosibles(cantidad,monedas.tail,cambiosPosibles,actual)
      }
      else cambiosPosibles
    }
  }

}

  def contarCambiosPosibles(cantidad:Int,monedas:List[Int]): List[List[Int]] ={
    // se les pasa las listas vacías , es como el new de Java
    contarCambiosPosibles(cantidad,monedas, List[List[Int]](), List[Int]())
  }

}
