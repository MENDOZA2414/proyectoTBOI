
public class Colision {
	private int x1;
	private int y1;
	private int ancho1;
	private int alto1;
	
	private int x2;
	private int y2;
	private int ancho2;
	private int alto2;

	public Colision() {
		x1 = 0;
		y1 = 0;
		ancho1 = 0;
		alto1 = 0;
		
		x2 = 0;
		y2 = 0;
		ancho2 = 0;
		alto2 = 0;
	}
	
	public boolean colision() {
		boolean colisionIzquierda = x1 + ancho1 > x2 && x1 < x2;
        boolean colisionDerecha = x1 < x2 + ancho2 && x1 + ancho1 > x2;
        boolean colisionArriba = y1 + alto1 > y2 && y1 < y2 + alto2;
        boolean colisionAbajo = y1 < y2 + alto2 - alto2/2 && y1 + alto1 > y2;

        if (colisionIzquierda && colisionDerecha && colisionArriba && colisionAbajo) {
            return true;
        } else {
            return false;
        }
	}
	
	public boolean detectar(Jugador jugador, Objeto objeto) {
		x1 = objeto.getX();
		y1 = objeto.getY();
        ancho1 = objeto.getAncho();
        alto1 = objeto.getAlto();

        x2 = jugador.getX();
        y2 = jugador.getY();
        ancho2 = jugador.getAncho();
        alto2 = jugador.getAlto();
        
        return colision();
    }
	
	public boolean detectar(Enemigo enemigo, Objeto objeto) {
		x1 = objeto.getX();
		y1 = objeto.getY();
        ancho1 = objeto.getAncho();
        alto1 = objeto.getAlto();

        x2 = enemigo.getX();
        y2 = enemigo.getY();
        ancho2 = enemigo.getAncho();
        alto2 = enemigo.getAlto();
        
        return colision();
    }
	
	public boolean detectar(Jugador jugador, Enemigo enemigo) {
		x1 = enemigo.getX();
		y1 = enemigo.getY();
		ancho1 = enemigo.getAncho();
		alto1 = enemigo.getAlto();
		
		x2 = jugador.getX();
		y2 = jugador.getY();
		ancho2 = jugador.getAncho();
		alto2 = jugador.getAlto();
		
		return colision();
    }
	
	public boolean detectar(Jugador jugador, Lagrima lagrima) {
		x1 = lagrima.getX();
		y1 = lagrima.getY();
		ancho1 = lagrima.getTamaño();
		alto1 = lagrima.getTamaño();
		
		x2 = jugador.getX();
		y2 = jugador.getY();
		ancho2 = jugador.getAncho();
		alto2 = jugador.getAlto();
		
		return colision();
    }
	
	public boolean detectar(Jugador jugador, Item item) {
		x1 = item.getX();
		y1 = item.getY();
		ancho1 = item.getAncho();
		alto1 = item.getAlto();
		
		x2 = jugador.getX();
		y2 = jugador.getY();
		ancho2 = jugador.getAncho();
		alto2 = jugador.getAlto();
		
		return colision();
    }
	
	public boolean detectar(Lagrima lagrima, Enemigo enemigo) { 
		x1 = enemigo.getX();
		y1 = enemigo.getY();
		ancho1 = enemigo.getAncho();
		alto1 = enemigo.getAlto();
		
		x2 = lagrima.getX();
		y2 = lagrima.getY();
		ancho2 = lagrima.getTamaño();
		alto2 = lagrima.getTamaño();
		
		return colision();
	 }
	
	public boolean detectar(Lagrima lagrima, Objeto objeto) {    
		x1 = objeto.getX();
		y1 = objeto.getY();
    	ancho1 = objeto.getAncho();
    	alto1 = objeto.getAlto();
    		
    	x2 = lagrima.getX();
    	y2 = lagrima.getY();
    	ancho2 = lagrima.getTamaño();
    	alto2 = lagrima.getTamaño();
 
    	return colision();
    }
}