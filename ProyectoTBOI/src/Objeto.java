import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Objeto {
	private BufferedImage sprite;
	private int ancho;
	private int alto;
	private int x;
    private int y;

    public Objeto(String spritePath, int ancho, int alto, int x, int y) {    	
        try {
            this.sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    	this.ancho = ancho;
    	this.alto = alto;

        this.x = x;
        this.y = y;
    }
    
    public Objeto(String spritePath, int ancho, int alto) {    	
        try {
            this.sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
       	this.ancho = ancho;
    	this.alto = alto;
    }

    public BufferedImage getSprite() {
    	return sprite;
    }
    
    public int getAncho() {
    	return ancho;
    }
    
    public int getAlto() {
    	return alto;
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
}
