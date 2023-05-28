package isaacPRUEBA;

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

	public void mover(int panelAncho, int panelAlto) {

        Random random = new Random();
        int cambioX = random.nextInt(3) - 1; //Valores aleatorios entre -1 y 1
        int cambioY = random.nextInt(3) - 1; //Valores aleatorios entre -1 y 1

        x += velocidad * cambioX;
        y += velocidad * cambioY;

		//Comprueba que el enemigo no se salga del panel
		if (x <= 0 || x >= panelAncho - getSprite().getWidth()) {
			x = -x;
		}
		
		if (y <= 0 || y >= panelAlto - getSprite().getHeight()) {
			y = -y;
		}
    }
}