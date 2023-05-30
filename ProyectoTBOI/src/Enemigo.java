import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

public class Enemigo {
	
	private int tamaño;
    private BufferedImage sprite;
    private int x;
    private int y;
    private int velocidad;

    public Enemigo(String spritePath, int tamaño, int x, int y, int velocidad) {
    	this.tamaño = tamaño;
        try {
            this.sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
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

	public void mover(int panelAncho, int panelAlto) {

        Random random = new Random();
        int cambioX = random.nextInt(3) - 1; //Valores aleatorios entre -1 y 1
        int cambioY = random.nextInt(3) - 1; //Valores aleatorios entre -1 y 1

        int nuevaX = x + velocidad * cambioX;
        int nuevaY = y + velocidad * cambioY;

        // Comprueba si las nuevas coordenadas están dentro de los límites del panel
        if (nuevaX >= 0 && nuevaX <= panelAncho - getSprite().getWidth()) {
            x = nuevaX;
        }
        
        if (nuevaY >= 0 && nuevaY <= panelAlto - getSprite().getHeight()) {
            y = nuevaY;
        }
    }
}