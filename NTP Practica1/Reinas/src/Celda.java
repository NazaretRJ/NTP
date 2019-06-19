public class Celda {

    private int fila,columna;

    public Celda(int f, int c){
        fila = f;
        columna = c;
    }

    public int getFila(){
        return fila;
    }

    public int getColumna(){
        return columna;
    }

    public boolean enConflicto(Celda otra){
        boolean peligro = true;
        int dif_f = Math.abs(fila - otra.getFila()) ;
        int dif_c = Math.abs(columna - otra.getColumna());


        if(dif_f != dif_c && fila != otra.fila && columna != otra.columna){
            peligro = false;
        }

        return peligro;

    }

    public void setCelda(int f, int c){
        fila = f;
        columna = c;
    }


}
