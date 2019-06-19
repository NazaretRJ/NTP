package Ejercicio2

object SeriesRecursivas {

  /*
   Necesitamos los dos primeros números que da la secuencia(caso base),
   la condición,
   el i-ésimo a calcular y devolver el número
   */

  /*
  Vamos a calcularlo hacia abajo. POr ejemplo:
  si hacemos la serie de la posición 4 se llama como serie(4,0,1) en el caso de Fibonacci
  se hace go(4,0,1) -> go(3,1,criterio(0,1)) = go(3,1,1)
     esto llama a go(2,1,f(1,1) = go(2,1,2)
     esto llama a go(1,2,f(1,2) = go(1,2,3)
     esto como pos es 1, es un caso base por lo que te da -> 3 (que es el acumulador)
  */

  def serie (pos : Int,casoBase:(Double,Double)) (criterio: (Double,Double) => Double) : Double ={
    //casobase._1 es acumulador_previo -> el más antiguo (i-2)
    //casobase._2 es acumulador -> el más nuevo (i-1)

    @annotation.tailrec
    def go(pos : Int,casoBase:(Double,Double)) : Double ={

      if(pos == 0){

        casoBase._1 //acumulador_previo
      }
      else{
        if(pos == 1){

          casoBase._2 //acumulador
        }
        else{
          //posicion anterior, actual, criterio(acumulador_previo,acumulador)
          go(pos-1,(casoBase._2,criterio(casoBase._1,casoBase._2)))
        }

      }
    }

    go(pos,casoBase)
  }

  //Criterios

  def condicionSuma2Terminos (a : Double,b:Double): Double ={
    a+b
  }

  def condicionMultiplicaPrimeroSumaSegundo(a:Double,b:Double):Double ={
    a + b*2
  }

  def condicionSumaPrimeroMultiplicaSegundo(a:Double,b:Double):Double ={
    2*a + b
  }


  //Series

  def Fibonnacci = serie (_:Int,(0,1))(condicionSuma2Terminos)

  def Lucas = serie (_:Int,(2,1))(condicionSuma2Terminos)

  def Pell = serie(_:Int,(2,6)) (condicionMultiplicaPrimeroSumaSegundo)

  def Pell_Lucas = serie(_:Int,(2,2))(condicionMultiplicaPrimeroSumaSegundo)

  def Jacobsthal = serie(_:Int,(0,1)) (condicionSumaPrimeroMultiplicaSegundo)


}
