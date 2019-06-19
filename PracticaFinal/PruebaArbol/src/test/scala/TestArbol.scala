
import org.scalatest.FunSuite

class TestArbol extends FunSuite {

  val hoja5 = new Hoja[Int](7, 0)
  val hoja6 = new Hoja[Int](8, 1)

  val ia = new Intermedio[Int](6, hoja5, hoja6)

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
  val lista_profundidad : List[Int] = List[Int](-1,0,4,5,6,1,2,3,7,8)
  val lista_anchura : List[Int] = List[Int](-1,0,6,4,1,7,8,5,2,3)



  test("Arbol 1") {
    println("Árbol 1")

    println("profundidad")
    assert( Nodo.recorrerProfundidad(nuevo_papa) ==  lista_profundidad)

    println("Anchura")
    assert( Nodo.recorrerAnchura(nuevo_papa) ==  lista_anchura)

    println("Sumar Valores de hojas")
    assert(Nodo.sumarValoresHojas(nuevo_papa) == (0+1+5+4+0+1))

    println("Sumarle un valor a las hojas")
    var sumatorios = Nodo.sumarNumeroAHojas(nuevo_papa,5)
    assert( Nodo.sumarValoresHojas(sumatorios) == 41 )

    println("Altura")
    assert(Nodo.getAltura(nuevo_papa) == 3)
  }


  val hoja13 = new Hoja[Int](13, 10)
  val hoja14 = new Hoja[Int](14, 2)

  val ip2_1 = new Intermedio[Int](11, hoja13, hoja14)

  val hoja15 = new Hoja[Int](15, 3)
  val hoja16 = new Hoja[Int](16, 40)

  val ip2_2 = new Intermedio[Int](12, hoja15, hoja16)

  val ip2_0 = new Intermedio[Int](10, ip2_1, ip2_2)

  val recorrido_profundidad_arbol_2 = List[Int](10,11,13,14,12,15,16)
  val recorrido_anchura_arbol_2 = List[Int](10,11,12,13,14,15,16)


  test("Arbol2"){

    println("Árbol 2")
    println("Recorrido profundidad")
    assert(Nodo.recorrerProfundidad(ip2_0) == recorrido_profundidad_arbol_2)

    println("Recorrido anchura")
    assert(Nodo.recorrerAnchura(ip2_0) == recorrido_anchura_arbol_2)

    println("Sumar Valores")
    assert(Nodo.sumarValoresHojas(ip2_0) == (10+2+3+40))

    println("Sumar +11 ")
    val sumatorios_arbol2 = Nodo.sumarNumeroAHojas(ip2_0,11)
    assert(Nodo.sumarValoresHojas(sumatorios_arbol2) == ((10+2+3+40) + 11*4) )

    println("Altura")
    assert(Nodo.getAltura(ip2_0) == 2)

  }

  val recorrido_profundidad_juntos:List[Int] = List[Int](-2,-1,0,4,5,6,1,2,3,7,8,10,11,13,14,12,15,16)
  val recorrido_anchura_juntos:List[Int] = List[Int](-2,-1,10,0,6,11,12,4,1,7,8,13,14,15,16,5,2,3)

  test("Juntar dos arboles"){

    println("Juntamos arbol 1 y 2")
    var arbol_nuevo_padre  = Nodo.juntarDosArboles(nuevo_papa,ip2_0)

    println("Recorrido Profundidad")
    assert(Nodo.recorrerProfundidad(arbol_nuevo_padre) == recorrido_profundidad_juntos)

    println("Recorrido Anchura")
    assert(Nodo.recorrerAnchura(arbol_nuevo_padre) == recorrido_anchura_juntos)

    println("Altura")
    assert(Nodo.getAltura(arbol_nuevo_padre) == 4)


  }




}
