package Ejercicio6
import scala.math.sqrt

object BusquedaSaltos {

  def busquedaSaltos[A](coleccion : Array[A], aBuscar: A,mayor : (A,A) => Boolean) : Int = {

    @annotation.tailrec
    def go(izquierda:Int):Int = {

      val tam = (sqrt(coleccion.length).toInt ) - 1

      if( izquierda + tam >= coleccion.length){
        -1
      }
      else{
        var valor = coleccion(izquierda + tam)


        if(mayor(aBuscar,valor)){

          go(izquierda + tam)
        }
        else {
          //está en este bloque
          var pos = -1
          var i = izquierda

          while (pos == -1 && i <= izquierda + tam) {

            if ( !mayor(coleccion(i), aBuscar) && !mayor(aBuscar, coleccion(i)) ) {
              //es igual
              pos = i

            }
            i = i+1
          }

          pos

        }
      }

    }

    go(0)

  }

}

