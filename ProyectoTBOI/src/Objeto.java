import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Objeto {
	private BufferedImage sprite;
	private String nombre;
	private int ancho;
	private int alto;
    private boolean destruible;
    private int dureza;
	private int x;
    private int y;

    public Objeto(String spritePath, String nombre, int ancho, int alto, boolean destruible, int dureza, int x, int y) {    	
        try {
            this.sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.nombre = nombre;
    	this.ancho = ancho;
    	this.alto = alto;
    	this.destruible = destruible;
    	this.dureza = dureza;
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

	public void setSprite(String spritePath) {
		try {
			this.sprite = ImageIO.read(new File(spritePath));
	            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public boolean isDestruible() {
		return destruible;
	}

	public void setDestruible(boolean destruible) {
		this.destruible = destruible;
	}

	public int getDureza() {
		return dureza;
	}

	public void setDureza(int dureza) {
		this.dureza = dureza;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
