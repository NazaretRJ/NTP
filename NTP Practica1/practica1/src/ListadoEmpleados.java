import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ListadoEmpleados {
    /**
     * Dato miembro para almacenar a los empleados tal y como se encuentran en el archivo de datos.txt
     **/
    private List<Empleado> listadoArchivo;
    /**
     * Dato miembro para almacenar a los empleados como mapa con pares* (una vez reparados los datos leidos del archivo) <dni - empleado>
     */
    private Map<String, Empleado> listado;

    public ListadoEmpleados(String nombreArchivo) throws IOException {
        // Se leen las lineas del archivo
        //cada string una linea
        Stream<String> lineas = Files.lines(Paths.get(nombreArchivo));

        // Se procesan las lineas del archivo mediante programación funcional
        listadoArchivo = lineas.map(linea -> new Empleado(linea)).
                collect(toList());
    }

    public void repararDnisRepetidos(Map<String, List<Empleado>> dnisRepetidos) {

        //se recorre y se modifica listadoArchivo porque las operaciones de ver si está repetido se hacen sobre él
        dnisRepetidos.forEach((dni, listaempleado) -> {
            listaempleado.forEach(empleado -> {
                //iteramos sobre la lista de empleados
                int i = listadoArchivo.indexOf(empleado);
                if (i >= 0) {
                    listadoArchivo.get(i).asignarDniAleatorio();
                }
            });
        });

        //si hay repetidos
        if (hayDnisRepetidosArchivo() == true) {
            repararDnisRepetidos(obtenerDnisRepetidosArchivo());
        } else {
            System.out.println("Todos los DNIs han sido reparados");
        }

    }

    public boolean hayCorreosRepetidosArchivo() {
        boolean repeat = true;

        List<String> correoEmpleadosSinRepetidos = listadoArchivo.stream().map(Empleado::obtenerCorreo).distinct().collect(toList());

        if (correoEmpleadosSinRepetidos.size() == listadoArchivo.size()) {
            repeat = false;
        }

        return repeat;
    }

    public void repararCorreosRepetidos(Map<String, List<Empleado>> correosRepetidos) {
        //se recorre y se modifica listadoArchivo porque las operaciones de ver si está repetido se hacen sobre él
        correosRepetidos.forEach((correo, listaempleado) -> {
            listaempleado.forEach(empleado -> {
                int i = listadoArchivo.indexOf(empleado);
                //indexOf para encontrar el indice de ese empleado (comparacion ===)
                if (i >= 0)
                    listadoArchivo.get(i).generarCorreoCompleto();
            });
        });


        //si hay repetidos
        if (contarEmpleadosCorreosRepetidos() > 0) {
            repararCorreosRepetidos(obtenerCorreosRepetidosArchivo());
        } else {
            System.out.println("Todos los correos han sido reparados");
        }
    }

    public Map<String, List<Empleado>> obtenerCorreosRepetidosArchivo() {
        //un map donde están ordenados por correo
        Map<String, List<Empleado>> aux = listadoArchivo.stream().collect(Collectors.groupingBy(Empleado::obtenerCorreo));

        //entrySet para recorrer el mapa
        //filter para ver si hay más de un empleado con el mismo correo
        //si es así se mete en el mapa
        Map<String, List<Empleado>> mapa2 = aux.entrySet().stream().filter(entrada -> entrada.getValue().size() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return mapa2;
    }

    public int contarEmpleadosCorreosRepetidos() {
        //a partir del map
        int aux = obtenerCorreosRepetidosArchivo().entrySet().stream().mapToInt(entrada -> entrada.getValue().size()).sum();
        return aux;
    }

    public void validarListaArchivo() {
        //para hacer el toMap es como si fueras dos expresiones lambda por eso se podría poner
        //empleado->empleado.obtenerDni() y empleado -> empleado
        //que se resume como pone:
        listado = listadoArchivo.stream().collect(Collectors.toMap(Empleado::obtenerDni, empleado -> empleado));
    }

    Sector encontrarSector(String sector) {

        Sector ssector = Sector.NOSECTOR;
        boolean encontrado = false;
        Sector[] list = Sector.values();
        for (int i = 0; i < list.length && !encontrado; i++) {
            if (sector.equals(list[i].toString())) {
                ssector = list[i];
                encontrado = true;
            }
        }
        return ssector;

    }

    public void cargarArchivoAsignacionSector(String nombreArchivo) throws IOException { //excepcion
        // Se leen las lineas del archivo y lo metemos en una lista
        List<String> lineas = Files.lines(Paths.get(nombreArchivo)).collect(toList());

        // Se extrae el nombre del sector
        Sector nombreSector = encontrarSector(lineas.get(0));
        //System.out.println("Sector1="+nombreSector.toString());
        //quitamos el nombre del sector
        lineas.remove(0);
        lineas.remove(0);//para el salto de línea que hay

        lineas.stream().forEach(dni -> {
            boolean esta = listado.containsKey(dni);

            if (esta) {
                //lo encontró asigna el sector
                listado.get(dni).asignarSector(nombreSector);
            }
        });
    }

    Ruta encontrarRuta(String ruta) {

        Ruta rruta = Ruta.NORUTA;
        boolean encontrado = false;
        Ruta[] list = Ruta.values();
        for (int i = 0; i < list.length && !encontrado; i++) {
            if (ruta.equals(list[i].toString())) {
                rruta = list[i];
                encontrado = true;
            }
        }
        return rruta;

    }


    public void cargarArchivoAsignacionRuta(String nombreArchivo) throws IOException {
        // Se leen las lineas del archivo y lo metemos en una lista
        List<String> lineas = Files.lines(Paths.get(nombreArchivo)).collect(toList());

        // Se extrae el nombre del sector
        Ruta nombreRuta = encontrarRuta(lineas.get(0));
        //System.out.println("Ruta="+nombreRuta.toString());
        //quitamos el nombre del sector
        lineas.remove(0);
        lineas.remove(0);//para el salto de línea que hay, al quitar, ahora esta es la 1

        lineas.stream().forEach(dni -> {
            boolean esta = listado.containsKey(dni);

            if (esta) {
                //lo encontró asigna el sector
                listado.get(dni).asignarRuta(nombreRuta);
            }
        });

    }

    //Empleados sin ruta del sector pasado como argumento
    public List<Empleado> buscarEmpleadosSinRuta(Sector sector1) {
        //Todos los empleados de ese sector que no tienen Ruta

        // Esto devuelve lista de entrada (entrada es un par dni,empleado)
        /*listado.entrySet().stream().filter(entrada -> {
            Empleado empleado=entrada.getValue();
            boolean condicion= (empleado.obtenerSector() == sector1 &&
                    empleado.obtenerRuta()== Ruta.NORUTA);
            return condicion;
        }).collect(toList());*/
        //esto daría los valores en lista
        //.map(entrada -> entrada.getValue()).collect(toList());


        return listado.values().stream().filter(empleado -> empleado.obtenerSector() == sector1 &&
                empleado.obtenerRuta() == Ruta.NORUTA).collect(toList());

    }

    //Empleados sin sector y la ruta pasada
    public List<Empleado> buscarEmpleadosSinSector(Ruta ruta){
        return listado.values().stream().filter(empleado -> empleado.obtenerSector() == Sector.NOSECTOR &&
                empleado.obtenerRuta() == ruta).collect(toList());
    }

    //Empleados sin sector ni ruta
    public List<Empleado> buscarEmpleadosSinSectorSinRuta(){
        return buscarEmpleadosSinRuta(Sector.NOSECTOR);
    }


    public List<Empleado> buscarEmpleadosConSectorSinRuta(){


        //quitamos el no sector
        List<Sector> sectores = Arrays.stream(Sector.values()).filter(sector -> sector != Sector.NOSECTOR).collect(Collectors.toList());
        List<Empleado> empleadosConSectorSinRuta = new ArrayList<>();

        sectores.stream().forEach(sector -> empleadosConSectorSinRuta.addAll(buscarEmpleadosSinRuta(sector)) );

        return empleadosConSectorSinRuta;
    }

    public List<Empleado> buscarEmpleadosSinSectorConRuta(){

        List<Empleado> empleadosSinSectorConRuta = new ArrayList<>();

        List<Ruta> rutas = Arrays.stream(Ruta.values()).filter(ruta -> ruta != Ruta.NORUTA).collect(Collectors.toList());
        rutas.stream().forEach( ruta -> empleadosSinSectorConRuta.addAll(buscarEmpleadosSinSector(ruta)) );

        return empleadosSinSectorConRuta;
    }

    public Map<Ruta, Long> obtenerContadoresRuta(Sector sector1) {
        return listado.values().stream().filter(empleado -> empleado.obtenerSector() == sector1 ).
                map(empleado -> empleado.obtenerRuta()).
                collect(Collectors.groupingBy(Function.identity(), TreeMap::new,
                        Collectors.counting()));
    }

    public Map<Sector, Map<Ruta, Long>> obtenerContadoresSectorRuta() {

        Sector[] listSector = Sector.values();
        Map<Sector, Map<Ruta, Long>> contador = new HashMap<Sector,Map<Ruta, Long>>();

        Stream.of(Sector.values()).forEach(sector ->
                contador.put(sector, obtenerContadoresRuta(sector)));

        return contador;
    }

    public int obtenerNumeroEmpleadosArchivo() {
        return listadoArchivo.size();
    }

    public boolean hayDnisRepetidosArchivo() {
        //lo que necesito es el dni
        //hago un conjunto de dnis
        //luego hago un distinct
        //si en los dos conjuntos hay el mismo número, no hay repetidos.

        //Nota: no se utiliza forEach porque no está pensado para obtener resultados (no podemos meter los dni en la lista dniEmpleados)

        boolean repeat = true;

        List<String> dniEmpleadosSinRepetidos = listadoArchivo.stream().map(Empleado::obtenerDni).distinct().collect(toList());

        if (dniEmpleadosSinRepetidos.size() == listadoArchivo.size()) {
            repeat = false;
        }

        return repeat;
    }

    public int contarEmpleadosDnisRepetidos() {
        //a partir del map
        int aux = obtenerDnisRepetidosArchivo().entrySet().stream().mapToInt(entrada -> entrada.getValue().size()).sum();
        return aux;
    }

    public Map<String, List<Empleado>> obtenerDnisRepetidosArchivo() {
        //un map donde están ordenados por DNI
        Map<String, List<Empleado>> aux = listadoArchivo.stream().collect(Collectors.groupingBy(Empleado::obtenerDni));

        //entrySet para recorrer el mapa
        //filter para ver si hay más de un empleado con el mismo DNI
        //si es así se mete en el mapa
        Map<String, List<Empleado>> mapa2 = aux.entrySet().stream().filter(entrada -> entrada.getValue().size() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return mapa2;

    }

    public List<Long> obtenerContadoresSectores(){
        Map<Sector, Map<Ruta, Long>> mapContadorSectoresRutas = obtenerContadoresSectorRuta();

        ArrayList<Long> contadores = (ArrayList<Long>) mapContadorSectoresRutas.entrySet().stream().map(sector -> {
            return sector.getValue().values().stream().mapToLong(x -> x).sum();
        }).collect(toList());

        //o ponemos aquí .sorted(Comparator.naturalOrder()) antes de collect

       return contadores;
    }


}
