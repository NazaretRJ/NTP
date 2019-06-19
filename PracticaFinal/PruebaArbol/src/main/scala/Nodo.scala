/* Son como árboles de probabilidad, los nodos intermedios no necesitan valor
  En caso de necesitarlo:

  abstract class Nodo [A](val id:Int, val valor:Double)
  case class Hoja[A] (override val id:Int , override val valor:Double) extends Nodo[A](id,valor);
  case class Intermedio[A](override val id:Int , override val valor:Double , val hijo_izq:Nodo[A] , val hijo_der:Nodo[A] ) extends Nodo[A](id,valor);

*/

abstract class Nodo[A](val id: Int)

case class Hoja[A](override val id: Int, val valor: Int) extends Nodo[A](id)

case class Intermedio[A](override val id: Int, val hijo_izq: Nodo[A], val hijo_der: Nodo[A]) extends Nodo[A](id)

//Los companion Objects no modifican al nodo.
object Nodo {

  //Una función que suma los valores de las hojas
  def sumarValoresHojas[A](nodo: Nodo[A]): Int = {

    def go(suma: Int, nodo: Nodo[A]): Int = {

      nodo match {
        case Hoja(id, valor) => {
          suma + valor
        }
        case Intermedio(id, hijo_izq, hijo_der) => {
          go(suma, hijo_izq) + go(suma, hijo_der)
        }
      }

    }

    go(0, nodo)
  }


  //Una función que suma un número a todos los valores de las hojas

  def sumarNumeroAHojas[A](padre: Nodo[A], suma: Int): Nodo[A] = {

    def go[A](nodo:Nodo[A]):Nodo[A] ={
      nodo match {
        case Hoja(id, valor) => {
          // creamos la hoja
          Hoja[A](nodo.id,valor+suma)

        }
        case Intermedio(id, hijo_izq, hijo_der) => {
          //creamos el intermedio
          Intermedio[A](id, go(hijo_izq) , go(hijo_der))
        }
      }
    }

    go(padre)

  }


  //Aquí imprime
  def recorrerAnchuraPrint[A](padre: Nodo[A]): Unit = {

    @annotation.tailrec
    def go( pendientes : List[Nodo[A]] ): Unit = {
      val nodo = pendientes.head

      println(nodo.id)

      nodo match {
        case Intermedio(id, hijo_izq, hijo_der) => {
          //quitamos el primero(ya lo hemos mostrado) y metemos en lista al final, a los hijos
          go(pendientes.tail ::: List[Nodo[A]](hijo_izq,hijo_der))

        }
        case Hoja(id,_) => {
          if(!pendientes.tail.isEmpty)
            go(pendientes.tail)
        }
      }
    }

    // Imprimimos


    go(List[Nodo[A]](padre))

  }


  //Aquí devuelve una lista de enteros con los id de los nodos.
  def recorrerAnchura[A](padre: Nodo[A]): List[Int] = {

    def go( pendientes : List[Nodo[A]], lista:List[Int] ): List[Int] = {
      val nodo = pendientes.head

      val aux:List[Int] = lista:::List[Int](nodo.id)

      nodo match {
        case Intermedio(id, hijo_izq, hijo_der) => {
          //quitamos el primero(ya lo hemos mostrado) y metemos en lista al final, a los hijos
          go(pendientes.tail ::: List[Nodo[A]](hijo_izq,hijo_der),aux)

        }
        case Hoja(id,_) => {
          if(!pendientes.tail.isEmpty)
            go(pendientes.tail,aux)
          else
            aux
        }
      }
    }

    // devolvemos
    go(List[Nodo[A]](padre),List[Int]()).distinct

  }

  //Imprime por pantalla los id
  def recorrerProfundidadPrint[A](nodo: Nodo[A]): Unit = {
    println(nodo.id)

    nodo match {
      case Intermedio(id, hijo_izq, hijo_der) => {
        //aquí tenemos que seguir

        recorrerProfundidadPrint(hijo_izq)
        recorrerProfundidadPrint(hijo_der)
      }
      case _ => {}
    }

  }


  //devuelve una lista de enteros con los ID de los nodos
  //sin repetidos

  def recorrerProfundidad[A](padre: Nodo[A]): List[Int] = {

    def go(nodo :Nodo[A],lista:List[Int]): List[Int] ={

      nodo match {
        case Intermedio(id, hijo_izq, hijo_der) => {
          //aquí tenemos que seguir
          var lista_aux:List[Int] = lista ::: List[Int](nodo.id)
          go(hijo_izq,lista_aux) ::: go(hijo_der,lista_aux)
        }
        case _ =>{
          lista ::: List[Int](nodo.id)
        }
      }

    }

    go(padre,List[Int](padre.id)).distinct
  }

  //Función auxiliar que devuelve el mínimo de dos números
  private def Minimo(a: Int, b: Int): Int = {
    if (a < b) a
    else b
  }

  //devolvemos al papa
  /*
  * Los padres son los que tienen el menor id
  * por eso cogeremos al menor de ellos y le restaremos 1
  * */
  def juntarDosArboles[A](padre: Nodo[A], padre2: Nodo[A]): Nodo[A] = {

    val aux_id = Minimo(padre.id, padre2.id)
    val padre_comun = new Intermedio[A](aux_id - 1, padre, padre2)

    padre_comun
  }

  //Cada nodo intermedio siempre tendrá dos hijos
  //pero eso no significa que una rama tenga más hijos que otra (es decir más profundidad)

  def getAltura[A](nodo:Nodo[A]):Int ={

    def go(alt : Int,nodo: Nodo[A],alturas:List[Int]):List[Int] ={
      nodo match {
        case Hoja(id,valor) =>{
          alt :: alturas
        }
        case Intermedio(id,hijo_izq,hijo_der) =>{
          //aquí tenemos que seguir
          go(alt+1,hijo_der,alturas) ::: go(alt+1,hijo_izq,alturas)
        }
        case _ => null
      }
    }

    //aquí tenemos que filtrar
    var cosa = go(0,nodo,List[Int]())

    if(cosa.isEmpty) 0
    else {
      cosa.max
    }
  }


}


/* object PruebaMain extends App {

  val hoja5 = new Hoja[Int](9, 0)
  val hoja6 = new Hoja[Int](10, 1)

  val ia = new Intermedio[Int](8, hoja5, hoja6)

  //Nodo.recorrerProfundidad(ia)

  //  println(Nodo.getAltura(ia))

  val hoja1 = new Hoja[Int](5, 5)
  val hoja2 = new Hoja[Int](6, 4)

  val i1 = new Intermedio[Int](4, hoja1, hoja2)

  val hoja3 = new Hoja[Int](2, 0)
  val hoja4 = new Hoja[Int](3, 1)

  val i2 = new Intermedio[Int](1, hoja3, hoja4)

  val i0 = new Intermedio[Int](0, i1, i2)


  val nuevo_papa = Nodo.juntarDosArboles(i0, ia)


  Nodo.recorrerProfundidadPrint(nuevo_papa)
  Nodo.recorrerAnchuraPrint(nuevo_papa)


  //pruebas:
  println(Nodo.getAltura(nuevo_papa))

  Nodo.recorrerAnchura(nuevo_papa)
  Nodo.recorrerAnchura(i0)
  Nodo.recorrerProfundidadPrint(nuevo_papa)


  var sumatorios = Nodo.sumarNumeroAHojas(nuevo_papa,5)
  println("Original " +Nodo.sumarValoresHojas(nuevo_papa))
  println("Nuevo " +Nodo.sumarValoresHojas(sumatorios))


  val aux:List[Int] = Nodo.recorrerAnchura(ia)




}

*/