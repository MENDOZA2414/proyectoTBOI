import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Item {
	private BufferedImage sprite;
	private String nombre;
	private int ancho;
	private int alto;
	private int x;
    private int y;

    public Item(String spritePath, String nombre, int ancho, int alto, int x, int y) {    	
        try {
            this.sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.nombre = nombre;
    	this.ancho = ancho;
    	this.alto = alto;
        this.x = x;
        this.y = x;
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