package TestEjercicio6

import org.scalatest.FunSuite
import Ejercicio6.BusquedaSaltos

class BusquedaSaltoTest extends FunSuite{

  def mayor (a :Int,b:Int): Boolean ={
    if(a>b) true
    else false
  }

  def masLarga(a:String,b:String): Boolean ={
    if(a.length > b.length) true
    else false
  }

    test("1,2,2,9,6,8,10,50,51,60,80 buscar 60") {
      assert(( BusquedaSaltos.busquedaSaltos(Array(1,2,3,6,8,9,10,50,51,60,80),60,mayor) == 9))

    }

    test("No está") {
      assert(( BusquedaSaltos.busquedaSaltos(Array(2,10,15,80,100),0,mayor) == -1))

    }

    test("2,10,15,80,100 buscamos 15") {
      assert(( BusquedaSaltos.busquedaSaltos(Array(2,10,15,80,100),15,mayor) == 2))

    }

    test("Buscar cadena mas larga buscar") {
      assert( ( BusquedaSaltos.busquedaSaltos(Array("Stitch","Tarzan y Jane","Hazme un muñeco de nieve","See the light in the sky you will see it calls me"),"Stitch",masLarga ) == 0))

    }


    test("Buscar cadena de 3") {
      assert( ( BusquedaSaltos.busquedaSaltos(Array("Stitch","Tarzan y Jane","Hazme un muñeco de nieve","See the light in the sky you will see it calls me"),"Tarzan y Jane",masLarga ) == 1) )
    }


}

