import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

public class Entidad {
	
	//Atributos del sprite
	private BufferedImage sprite;
	BufferedImage tearSprite;
	private String nombre;
	private int ancho;
	private int alto;
	
	//Atributos generales
	private boolean canShoot;	
    private int speed;
    private boolean canMove;
    private int tearSize;
    private int tearSpeed;
    private float tearRange;
    private int shootDelay;
    private int life;
    private boolean invencible;
    private int immunityTime;
    
	//Coordenadas
    private int x;
    private int y;
    
    public Entidad(String spritePath, String tearPath, String nombre, int ancho, int alto, int speed, boolean canMove, boolean canShoot, int tearSize, int tearSpeed, float tearRange, int shootDelay, int life, int immunityTime, int x, int y) {
    	
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
    	this.canMove = canMove;
    	this.tearSize = tearSize;
    	this.tearSpeed = tearSpeed;
    	this.tearRange = tearRange;
    	this.shootDelay = shootDelay;
    	this.life = life;
    	this.invencible = false;
    	this.immunityTime = immunityTime;
    	
        this.x = x;
        this.y = y;
    }

    public void recibeDaño() {
    	setLife(getLife()-1);
    }
    
    public void activarInmunidad(int segundos) {
        setInvencible(true);
        Timer invencibleTimer = new Timer();
        invencibleTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                setInvencible(false);
            }
        }, segundos*1000); // 1000 milisegundos = 1 segundos
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

	public BufferedImage getTearSprite() {
		return tearSprite;
	}

	public void setTearSprite(String spritePath) {
		try {
			this.tearSprite = ImageIO.read(new File(spritePath));
	            
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

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public int getTearSize() {
		return tearSize;
	}

	public void setTearSize(int tearSize) {
		this.tearSize = tearSize;
	}

	public int getTearSpeed() {
		return tearSpeed;
	}

	public void setTearSpeed(int tearSpeed) {
		this.tearSpeed = tearSpeed;
	}
	
	public float getTearRange() {
		return tearRange;
	}

	public void setTearRange(float tearRange) {
		this.tearRange = tearRange;
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

	public boolean isInvencible() {
		return invencible;
	}

	public void setInvencible(boolean invencible) {
		this.invencible = invencible;
	}

	public int getImmunityTime() {
		return immunityTime;
	}

	public void setImmunityTime(int immunityTime) {
		this.immunityTime = immunityTime;
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