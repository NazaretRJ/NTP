package TestEjercicio1

import Ejercicio1.TrianguloPascal

import org.scalacheck.{Gen,Properties}
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll


object TrianguloCheck extends Properties("Triangulo Pascal") {
  private val MAXIMO = 20

  //generador
  val coordenadasExtremos = for{
    fila <- Gen.choose(0,MAXIMO)
    columna <- Gen.oneOf(0,fila) //para hacer la propiedad de los extremos
  } yield (fila,columna)

  //propiedad que queremos comprobar
  property("Extremos valen 1")={
    forAll(coordenadasExtremos){ (i) =>
      //llamamos al otro object
      val resultado = TrianguloPascal.calcularValorTrianguloPascal(i._1,i._2)
      println("Probamos: " + i._1 + " col: " + i._2 + " resultado: "+resultado)

      resultado == 1
    }
  }

  //generador coordenas internas

  val coordenasInteriores = for{
    fila <-Gen.choose(2,MAXIMO)
    columna <- Gen.choose(1,fila-1)
  }yield(fila,columna)

  //propiedad que queremos comprobar
  property("Valores interiores")={
    forAll(coordenasInteriores){ (i) =>
      //llamamos al otro object
      val resultado = TrianguloPascal.calcularValorTrianguloPascal(i._1,i._2)
      val supIzquierda = TrianguloPascal.calcularValorTrianguloPascal(i._1-1,i._2-1)
      val supDerecha = TrianguloPascal.calcularValorTrianguloPascal(i._1-1,i._2)
      println("Probamos: " + i._1 + " col: " + i._2 + " resultado: "+resultado)

      resultado == (supDerecha + supIzquierda)
    }
  }
}