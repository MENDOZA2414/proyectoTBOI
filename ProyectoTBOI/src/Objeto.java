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

    public boolean intersecta(int x, int y, int ancho, int alto) {
        // Verificar si los límites del obstáculo se superponen con los límites del enemigo
        if (getX() < x + ancho && getX() + getAncho() > x &&
            getY() < y + alto && getY() + getAlto() > y) {
            // Hay intersección entre el enemigo y el obstáculo
            return true;
        } else {
            // No hay intersección
            return false;
        }
    }

    public double getRadio() {
        // Calcular el radio como la mitad de la diagonal del rectángulo del enemigo
        double diagonal = Math.sqrt(getAncho() * getAncho() + getAlto() * getAlto());
        double radio = diagonal / 2.0;
        return radio;
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
