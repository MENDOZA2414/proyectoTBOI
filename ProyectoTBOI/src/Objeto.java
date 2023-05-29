import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Objeto {
    private BufferedImage sprite;
    private int tamaño;
    private int x;
    private int y;

    public Objeto(String spritePath, int tamaño, int x, int y) {
        try {
            this.sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tamaño = tamaño;
        this.x = x;
        this.y = y;
    }
    
    public boolean colisionObjeto(BufferedImage frame, int jugadorX, int jugadorY) {
        return false;
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
