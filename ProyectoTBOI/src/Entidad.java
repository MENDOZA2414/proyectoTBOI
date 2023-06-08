import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Entidad {
	
	//Atributos del sprite
	private BufferedImage sprite;
	BufferedImage tearSprite;
	private String nombre;
	private int ancho;
	private int alto;
	
	//Atributos generales
	boolean canShoot;	
    private int speed;
    private int tearSpeed;
    private int shootDelay; // 1000 = 1 segundo
    private int life;
    
	//Coordenadas
    private int x;
    private int y;
    
    public Entidad(String spritePath, String tearPath, String nombre, int ancho, int alto, int speed, boolean canShoot, int tearSpeed, int shootDelay, int life, int x, int y) {
    	
        try {
            this.sprite = ImageIO.read(new File(spritePath));
            tearSprite = ImageIO.read(new File(tearPath));

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.nombre = nombre;
        this.ancho = ancho;
    	this.alto = alto;
    	
    	this.canShoot = canShoot;
    	this.speed = speed;
    	this.tearSpeed = tearSpeed;
    	this.shootDelay = shootDelay;
    	this.life = life;
    	
        this.x = x;
        this.y = y;
    }

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public BufferedImage getTearSprite() {
		return tearSprite;
	}

	public void setTearSprite(BufferedImage tearSprite) {
		this.tearSprite = tearSprite;
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

	public boolean isCanShoot() {
		return canShoot;
	}

	public void setCanShoot(boolean canShoot) {
		this.canShoot = canShoot;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getTearSpeed() {
		return tearSpeed;
	}

	public void setTearSpeed(int tearSpeed) {
		this.tearSpeed = tearSpeed;
	}

	public int getShootDelay() {
		return shootDelay;
	}

	public void setShootDelay(int shootDelay) {
		this.shootDelay = shootDelay;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
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
