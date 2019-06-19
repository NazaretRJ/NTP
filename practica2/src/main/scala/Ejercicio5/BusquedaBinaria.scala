package Ejercicio5

object BusquedaBinaria {

  def busquedaBinaria[A](coleccion : Array[A], aBuscar: A,criterio : (A,A) => Boolean) : Int = {

    @annotation.tailrec
    def go(izquierda:Int,derecha:Int):Int = {

      if(izquierda > derecha) {

        -1 //se han cruzado
      }
      else{
        val centro = (izquierda + derecha) / 2
        val valorCentro = coleccion(centro)

        val enOrden = criterio(valorCentro,aBuscar)


        if(!criterio(aBuscar,valorCentro) && !enOrden){

          //no es mayor, ni menor -> es igual (no tenemos criterio de igualdad)
          centro
        }
        else{
          //no es el central
          if(enOrden)
            go(izquierda, centro-1)
          else
            go(centro+1, derecha)
        }
      }
    }

    go(0,coleccion.length-1)
  }


}
