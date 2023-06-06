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
    
    public Objeto(String spritePath, int tamaño) {
    	this.tamaño = tamaño;
    	
        try {
            this.sprite = ImageIO.read(new File(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
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
