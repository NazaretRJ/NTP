package Medidor
import org.scalameter._
import org.scalameter.api._
import Ejercicio5.BusquedaBinaria
import Ejercicio6.BusquedaSaltos

//https://scalameter.github.io/home/gettingstarted/0.7/simplemicrobenchmark/index.html

object ObjectMedidor extends App{

  //configuración standard

  val standarConfig = config(
    Key.exec.minWarmupRuns -> 5, //minimo para estar estable
    Key.exec.maxWarmupRuns -> 10,
    Key.exec.benchRuns -> 10,
    Key.verbose -> true
  )

  //coleccion aleatoria

  val TAM_COLECCION = 10
  val TAM_BUSCAR = 10

  val lista = Array.fill(TAM_COLECCION)(util.Random.nextInt(500)).sorted.distinct
  val buscadores = List.fill(TAM_BUSCAR)(util.Random.nextInt(lista.length))

  //forma de ordenacion

  val orden = (a:Int,b:Int) =>{ a > b }

  //se realiza la busqueda para cada indice de buscar

  val tiemposBusquedaBinaria = standarConfig measure(
    buscadores.foreach(index => {
      val pos = BusquedaBinaria.busquedaBinaria[Int](lista,lista(index),orden)
    })
  )

  println("tiempo de busqueda Binaria"+tiemposBusquedaBinaria)


  val tiemposBusquedaSalto = standarConfig measure( //????????????
    buscadores.foreach(index => {
        val pos = BusquedaSaltos.busquedaSaltos[Int](lista,lista(index),orden)
    })
    )

  println("tiempo de busqueda Saltos"+tiemposBusquedaSalto)


}
