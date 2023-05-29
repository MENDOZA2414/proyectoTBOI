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
    
    public boolean colisionObjeto(int tamañoJugador, int jugadorX, int jugadorY) {
        if (jugadorX + tamañoJugador >= x && jugadorX <= x + tamaño &&
            jugadorY + tamañoJugador >= y && jugadorY <= y + tamaño) {
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

    public BufferedImage getSprite() {
		return sprite;
	}

	public int getTamaño() {
		return tamaño;
	}
 
}
