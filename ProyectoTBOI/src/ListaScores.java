
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListaScores {
    private ArrayList<Object> lista = new ArrayList<Object>();
   
    public ListaScores(){}

    public void agregarScore(Score Score){
        this.lista.add(Score);
    }
    /*
    public void modificarScore(int posicion, Score Score){
        this.lista.set(posicion, Score);
    }
    
    public void eliminarScore(int posicion){
        this.lista.remove(posicion);
    }*/
    
    public Score obtenerScore(int posicion){
        return (Score)lista.get(posicion);
    }
    
    public void ordenarScores() {
    	Collections.sort(lista, new Comparator<Object>() {
            @Override
            public int compare(Object obj1, Object obj2) {
                Score score1 = (Score) obj1;
                Score score2 = (Score) obj2;
                return Integer.compare(score2.getScore(), score1.getScore());
            }
        });
    }
    
    public int cantidadScore(){
        return this.lista.size();
    }
    
    public int buscaNombre(String nombre){
        for(int i = 0; i < cantidadScore(); i++){
           if(nombre.equals(obtenerScore(i).getNombre()))return i;
        }
        return -1;
    }
    public  ArrayList<Object> obtenerLista() {
    	return lista;
    }
}
