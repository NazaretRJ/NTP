

import org.junit.BeforeClass;
import org.junit.Test;


import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.sort;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Práctica 1 NTP
 */
public class ListadoTestP2 {
    private static ListadoEmpleados listado;

    /**
     * Codigo a ejecutar antes de realizar las llamadas a los métodos
     * de la clase; incluso antes de la propia instanciación de la
     * clase. Por eso el método debe ser estatico
     */
    @BeforeClass
    public static void inicializacion() {
        System.out.println("Metodo inicializacion conjunto pruebas");
        // Se genera el listado de empleados
        try {
            listado = new ListadoEmpleados("./data/datos.txt");
        } catch (IOException e) {
            System.out.println("Error en lectura de archivo de datos");
        }


        // Se reparan los problemas y se pasan los datos al datos miembro
        // listado
        Map<String, List<Empleado>> dnisRepetidos = listado.obtenerDnisRepetidosArchivo();
        listado.repararDnisRepetidos(dnisRepetidos);


        /**
         * Test del procedimiento de asignacion de grupos procesando
         * los archivos de asignacion. Tambien implica la prueba de
         * busqueda de empleados sin grupo asignado en alguna asignatura
         *
         * @throws Exception
         */


        Map<String, List<Empleado>> correosRepetidos = listado.obtenerCorreosRepetidosArchivo();
        listado.repararCorreosRepetidos(correosRepetidos);
        listado.validarListaArchivo();

        // Se leen ahora los archivos de asignaciones de sectores y departamentos
        try {
            long errores;
            listado.cargarArchivoAsignacionSector("./data/asignacionSECTOR1.txt");
            listado.cargarArchivoAsignacionSector("./data/asignacionSECTOR2.txt");
            listado.cargarArchivoAsignacionRuta("./data/asignacionRUTA1.txt");
            listado.cargarArchivoAsignacionRuta("./data/asignacionRUTA2.txt");
            listado.cargarArchivoAsignacionRuta("./data/asignacionRUTA3.txt");
            System.out.println("Han sido asignados");
        } catch (IOException e) {
            System.out.println("Problema lectura datos asignacion");
            System.exit(0);
        }
    }


    /**
     * Test del procedimiento de asignacion de grupos procesando
     * los archivos de asignacion. Tambien implica la prueba de
     * busqueda de empleados sin grupo asignado en alguna asignatura
     *
     * @throws Exception
     */
    @Test
    public void testBusquedaEmpleadosSinRuta() throws Exception {
        // Se obtienen los empleados no asignados a cada asignatura
        // y se comprueba su valor
        int res1, res2, res3;
        res1 = listado.buscarEmpleadosSinRuta(Sector.NOSECTOR).size();
        res2 = listado.buscarEmpleadosSinRuta(Sector.SECTOR1).size();
        res3 = listado.buscarEmpleadosSinRuta(Sector.SECTOR2).size();
        System.out.println("res1: " + res1 + " res2: " + res2 + " res3: " + res3);
        assertTrue(res1 == 418);
        assertTrue(res2 == 432);
        assertTrue(res3 == 399);
    }

   /**
    * Prueba para el procedimiento de conteo de grupos para cada una
    * de las asignaturas
    */
   @Test
   public void testObtenerContadoresSector1() {
      // Se obtienen los contadores para la asignatura ES
      Map<Ruta, Long> contadores = listado.obtenerContadoresRuta(Sector.SECTOR1);
      contadores.keySet().stream().forEach(key -> System.out.println(
              key.toString() + "- " + contadores.get(key)));
      // Se comprueba que los valores son DEPNA = 49, DEPSB = 48, DEPSM = 53, DEPSA = 41
      Long contadoresReferencia[] = {401L, 437L, 403L, 432L};
      Long contadoresCalculados[] = new Long[4];
      assertArrayEquals(contadores.values().toArray(contadoresCalculados),
              contadoresReferencia);
   }

   /**
    * Prueba del procedimiento general de obtencion de contadores
    * para todas las asignaturas
    *
    * @throws Exception
    */
   @Test
   public void testObtenerContadoresSector() throws Exception {
      // Se obtienen los contadores para todos los grupos
      Map<Sector, Map<Ruta, Long>> contadores =
         listado.obtenerContadoresSectorRuta();

      // Se comprueban los valores obtenenidos con los valores por referencia
      Long contadoresReferenciaSector1[] = {401L, 437L, 403L, 432L};
      Long contadoresReferenciaSector2[] = {428L, 425L, 388L, 399L};
      Long contadoresReferenciaNoSector[] = {446L, 414L, 409L, 418L};

      // Se comprueban los resultado del metodo con los de referencia
      Long contadoresCalculados[] = new Long[4];
      assertArrayEquals(contadores.get(Sector.NOSECTOR).values().
         toArray(contadoresCalculados), contadoresReferenciaNoSector);
      assertArrayEquals(contadores.get(Sector.SECTOR1).values().
         toArray(contadoresCalculados), contadoresReferenciaSector1);
      assertArrayEquals(contadores.get(Sector.SECTOR2).values().
         toArray(contadoresCalculados), contadoresReferenciaSector2);
   }

   // Aqui habria que completar los casos de prueba para el resto de
   // metodos a ofrecer por la clase Listado

    //Esto hay que hacerlo antes de hacer validarListaArchivo.
   /*@Test
   public void testCorreosDNIsRepetidos() throws Exception {

      assertTrue  (listado.contarEmpleadosDnisRepetidos() == 0);
      System.out.println("NO hay DNI repetidos");
      assertTrue (listado.hayCorreosRepetidosArchivo() == true);
      assertTrue  (listado.contarEmpleadosCorreosRepetidos() == 315);
      System.out.println("Hay correos repetidos");

      Map<String, List<Empleado>> correosRepetidos = listado.obtenerCorreosRepetidosArchivo();
      listado.repararCorreosRepetidos(correosRepetidos);
      System.out.println("corregimos los correos...");
      assertTrue  (listado.contarEmpleadosCorreosRepetidos() == 0);
      System.out.println("No hay correos repetidos...");

   }*/

   /*@Test
   public void testAsiganrArchivo() throws Exception{
      listado.cargarArchivoAsignacionSector("./data/asignacionSECTOR1.txt");
      listado.cargarArchivoAsignacionSector("./data/asignacionSECTOR2.txt");
   }*/
    @Test
    public void obtenerEmpleadosSinRutaConSector(){

        int empleadosSinRutaCS = listado.buscarEmpleadosConSectorSinRuta().size();

        assertTrue(empleadosSinRutaCS == 831);
    }

    @Test
    public void obtenerEmpleadosSinSectorConRuta(){
        int empleadosSinSectorConRuta = listado.buscarEmpleadosSinSectorConRuta().size();

        assertTrue(empleadosSinSectorConRuta == 1269);
    }


    @Test
    public void obtenerContadoresSectores(){
        List<Long> contador = listado.obtenerContadoresSectores();
        //o aquí hacemos un sort
        sort(contador);
        assertTrue (contador.get(0) == 1640);
        assertTrue (contador.get(1) == 1673);
        assertTrue (contador.get(2) == 1687);
    }

    @Test
    public void obtenerContadorEmpleadosSinSectorSinRuta(){
        int empleadosSinNada = listado.buscarEmpleadosSinSectorSinRuta().size();

        assertTrue(empleadosSinNada ==  418);
    }

}