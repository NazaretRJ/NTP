package TestEjercicio2

import Ejercicio2.SeriesRecursivas

import org.scalacheck.{Gen, Properties}
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

object SeriesCheck extends Properties("Comprobar Series") {
  val MAXIMO = 20
  //generador

  val posiciones = for {
    pos<- Gen.choose(2, MAXIMO)
  }yield(pos)


  //propiedad que queremos comprobar
  property("Series")={
    forAll(posiciones){ (i) =>
      //llamamos al otro object
      println("Posicion: "+i)
      val Fibonacci = SeriesRecursivas.Fibonnacci(i)

      println("Fibonacci")
      Fibonacci == SeriesRecursivas.Fibonnacci(i-1) + SeriesRecursivas.Fibonnacci(i-2)

      println("Lucas")
      val Lucas = SeriesRecursivas.Lucas(i)
      Lucas == SeriesRecursivas.Lucas(i-1) + SeriesRecursivas.Lucas(i-2)

      val Pell = SeriesRecursivas.Pell(i)
      println("Pell")
      Pell == (2 * SeriesRecursivas.Pell(i-1)) + SeriesRecursivas.Pell(i-2)

      val Pell_Lucas = SeriesRecursivas.Pell_Lucas(i)
      println("Pell-Lucas")
      Pell_Lucas == 2*SeriesRecursivas.Pell_Lucas(i-1) + SeriesRecursivas.Pell_Lucas(i-2)

      val Jacob = SeriesRecursivas.Jacobsthal(i)
      println("Jacobsthal")
      Jacob == SeriesRecursivas.Jacobsthal(i-1) + 2*SeriesRecursivas.Jacobsthal(i-2)

    }
  }



}
