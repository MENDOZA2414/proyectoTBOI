
public class Puerta extends Objeto {
    private boolean abierta;
    private String rutaOriginal;

    public Puerta(String spritePath, String nombre, int ancho, int alto, boolean destruible, int dureza, int x, int y, boolean abierta) {
        super(spritePath, nombre, ancho, alto, destruible, dureza, x, y);
        rutaOriginal=spritePath;
        this.abierta = abierta;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    public void setSpriteCerrado(){
        this.setSprite(rutaOriginal);
    }
    public void setSpriteAbierto(int puerta) {
        switch(puerta){
            case 0:
            	this.setSprite("resources/puertaArriba.png");
            break;

        case 1:    
            	this.setSprite("resources/puertaAbajo.png");
            break;

        case 2:
        		this.setSprite("resources/puertaIzquierda.png");
            break;

        case 3:
            	this.setSprite("resources/puertaDerecha.png");
            break;
        }  
	}
}