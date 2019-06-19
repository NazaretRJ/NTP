import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Buscador {

    private int dimension;

    //siendo tam el tamaño del tablero
    public Buscador(int tam){
        dimension = tam;
    }

    public int getDimension() {
        return dimension;
    }

    public ArrayList<Tablero> ubicarReina(int fila){
        ArrayList<Tablero> provisional = new ArrayList<>();

        if( fila == -1){
            //caso base
            provisional.add(new Tablero(dimension));
        }
        else{
            //inductivo
            ArrayList<Tablero> soluciones = ubicarReina(fila-1);
            //como hago lo de iterar la fila y la columna

            Celda aux = new Celda(0,0);


            List<Tablero> soluciones2 = soluciones.stream().flatMap(tablero ->
                IntStream.range(0,dimension).boxed().map( columna ->{
                   Celda nueva = new Celda(fila,columna);
                   Tablero resultado = null;

                   if(tablero.posicionSegura(nueva)){
                       resultado = tablero.ponerReina(nueva.getFila(),nueva.getColumna());

                   }

                   return resultado;
                }).filter(solucion -> solucion!= null)
            ).collect(Collectors.toList());

            provisional.addAll(soluciones2);
        }
        return provisional;
    }

    public ArrayList<Tablero> resolver(){
        return ubicarReina(dimension -1);
    }


    public static void main(String args[]) {
        Scanner capt = new Scanner(System.in);
        System.out.print("Dimension deseada: ");
        int dim = capt.nextInt();

        Buscador buscador = new Buscador(dim);

        ArrayList<Tablero> solucion = buscador.resolver();
        System.out.println(solucion.size());
        if(solucion.size() > 0){
            System.out.println("Soluciones encontradas: \n");
            solucion.stream().forEach(tablero -> {
                System.out.println(tablero.toString());
                System.out.println("------------------------------------------------------------");
                System.out.println("------------------------------------------------------------" + "\n");

            });
        }
        else{
            System.out.println("Ninguna solución encontrada");
        }

    }

}


