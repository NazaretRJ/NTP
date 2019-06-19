package Ejercicio3

object BalanceoCadenas {



  def chequearBalance(cadena: List[Char]): Boolean ={

    @annotation.tailrec
    def go(cadena:List[Char],abiertos:Int,cerrados:Int): Boolean ={
      if(cadena.size == 0 ) (abiertos - cerrados == 0)
      else{
        if( abiertos - cerrados > 0 ){
          var cola = cadena.tail
          if(cola == '(' ) go(cadena,abiertos +1,cerrados)
          else {
            if(cola == ')') go(cadena,abiertos,cerrados +1)

            else go(cadena,abiertos,cerrados +1)
          }
        }
        else false
      }
    }

    go(cadena,0,0)
  }



}
