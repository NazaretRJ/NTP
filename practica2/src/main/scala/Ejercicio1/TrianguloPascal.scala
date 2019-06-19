package Ejercicio1

//Scala Check -> propiedades
//Scala Test

object TrianguloPascal extends App {


  def calcularCombinatroriaRec(num:Int): Long = {
    @annotation.tailrec
    def go(num:Long,acum:Long):Long = {
      if (num == 1 | num == 0) acum
      else go(num-1, acum * num)
    }
    go(num,1)
  }



  def calcularValorTrianguloPascal(fila:Int,col:Int):Long = {
    //es una relación que se cumple
    var f = calcularCombinatroriaRec(fila)
    var c = calcularCombinatroriaRec(col)

    var resta = calcularCombinatroriaRec((fila-col))

    var aux = (resta*c)

    f/aux

  }

  def imprimirTriangulo(Max:Int): Unit = {
    for(fila <- 0 to Max){
      for(columna <- 0 to fila){
        print(calcularValorTrianguloPascal(fila,columna)+"  ")
      }
      println()
    }
  }


}