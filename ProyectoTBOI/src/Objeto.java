import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Objeto {
	private int tamaño;
    private BufferedImage sprite;
    private int x;
    private int y;

    public Objeto(String spritePath, int tamaño, int x, int y) {
    	this.tamaño = tamaño;
    	
        try {
            this.sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.x = x;
        this.y = y;
    }
    
    public boolean detectarColision(Isaac jugador) {
        int ancho1 = this.getTamaño();
        int alto1 = this.getTamaño();
        int x1 = this.getX();
        int y1 = this.getY();

        int ancho2 = jugador.getCharacterSize();
        int alto2 = jugador.getCharacterSize();
        int x2 = jugador.getX();
        int y2 = jugador.getY();

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public int getTamaño() {
		return tamaño;
	}
}
