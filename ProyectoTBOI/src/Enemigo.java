import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

public class Enemigo {
	
	private int ancho;
	private int alto;
    private BufferedImage sprite;
    private int x;
    private int y;
    private int velocidad;
    private int vida;
    private String nombre;
    
    public Enemigo(String spritePath, String nombre, int ancho, int alto, int x, int y, int velocidad, int vida) {
    	this.ancho = ancho;
    	this.alto = alto;
        try {
            this.sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.vida = vida;
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

	public String getNombre() {
		return nombre;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void mover() {
		
		Random random = new Random();
        int cambioX = random.nextInt(3) - 1; //Valores aleatorios entre -1 y 1
        int cambioY = random.nextInt(3) - 1; //Valores aleatorios entre -1 y 1

        int nuevaX = x + velocidad * cambioX;
        int nuevaY = y + velocidad * cambioY;
        
        if(!(nuevaX < 80 || nuevaX > Juego.WIDTH - 110|| nuevaY < 80 || nuevaY > Juego.HEIGHT - 110)) {
            x = nuevaX;
            y = nuevaY;   
        }
    }

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}
}