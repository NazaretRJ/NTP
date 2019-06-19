import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Tablero {
    private int dimension;
    private ArrayList<Celda> contenido;

    public Tablero(int tamaño){
        dimension = tamaño;
        contenido = new ArrayList<Celda>();
    }


    //¡Alerta! Este método NO realiza comprobaciones acerca
    //de la validez de la Celda

    public Tablero ponerReina(int fila, int columna){

        Tablero aux = new Tablero(dimension);

        aux.contenido.addAll(contenido);
        aux.contenido.add(new Celda(fila,columna));

        return aux;
    }

    public boolean posicionSegura(Celda otra){
        //vamos a ver si hay conflicto con alguna reina por eso vamos a usar AnyMatch

        boolean conflicto = contenido.stream().anyMatch( celda ->
            celda.enConflicto(otra) == true );

        return !conflicto;
    }


    @Override
    public String toString() {
        String mensaje = new String();

        List<String> contenidoTablero = IntStream.range(0, dimension).boxed().map(fila -> {
            return toString(fila);
        }).collect(Collectors.toList());

        return String.join("", contenidoTablero);
    }

    //fila no puede ser null
    private String toString(int fila){
        // recorre todas las columnas de la fila pasada como argumento
        List<String> contenidoFila = IntStream.range(0, dimension).boxed().map(columna -> {
            List<Celda> celdasIguales = contenido.stream().filter(celda ->
                    celda.getFila() == fila && celda.getColumna() == columna).collect(Collectors.toList());

            String resultado;
            if(celdasIguales.size() == 0) {
                resultado=" X ";
            }
            else {
                resultado=" R ";
            }

            if(columna == (dimension-1)) {
                resultado = resultado + "\n";
            }

            return resultado;
        }).collect(Collectors.toList());

        return String.join("", contenidoFila);
    }

    public ArrayList<Celda> getContenido(){
        return contenido;
    }
}
