import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Jugador extends Entidad{

    private int velocityX;
    private int velocityY;
    
    private List<Lagrima> lagrimas;
    public long lastShootTime;  // Tiempo del último disparo

    public Jugador(String spritePath, String tearPath, String nombre, int ancho, int alto, int speed, boolean canMove, boolean canShoot, int tearSize, int tearSpeed, int shootDelay, int life, int immunityTime, int x, int y) {
        super(spritePath, tearPath, nombre, ancho, alto, speed, canMove, canShoot, tearSize, tearSpeed, shootDelay, life, immunityTime, x, y);
        this.velocityX = 0;
        this.velocityY = 0;
        
        lagrimas = new ArrayList<>();
        lastShootTime = 0;
    }
    
    public void paint(Graphics g) {
        g.drawImage(getSprite(), getX() - getAncho() / 2, getY() - getAlto() / 2, getAncho(), getAlto(), null);

        for (Lagrima lagrima : lagrimas) {
            g.drawImage(getTearSprite(), lagrima.getX() - lagrima.getTamaño() / 2, lagrima.getY() - lagrima.getTamaño() / 2, lagrima.getTamaño(), lagrima.getTamaño(), null);
        }
    }

    public void update(int velocityX, int velocityY) {
    	if(isCanMove()) {
	    	setX(getX()+velocityX);
	    	setY(getY()+velocityY);
	    	
	    	for (Lagrima lagrima : lagrimas) {
	    		lagrima.update();
	    	}
	    	
	    	lagrimas.removeIf(lagrima -> lagrima.getX() < 80 || lagrima.getX() > Juego.WIDTH - 110|| lagrima.getY() < 80 || lagrima.getY() > Juego.HEIGHT - 110);    		
    	}
    }

    public void shootUp() {
        long currentTime = System.currentTimeMillis();
        if (isCanShoot() && currentTime - lastShootTime >= getShootDelay()) {
            Lagrima lagrima = new Lagrima(getTearSize(), getX(), getY(), 0, -getTearSpeed());
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }

    public void shootDown() {
        long currentTime = System.currentTimeMillis();
        if (isCanShoot() && currentTime - lastShootTime >= getShootDelay()) {
            Lagrima lagrima = new Lagrima(getTearSize(), getX(), getY(), 0, getTearSpeed());
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }

    public void shootLeft() {
        long currentTime = System.currentTimeMillis();
        if (isCanShoot() && currentTime - lastShootTime >= getShootDelay()) {
            Lagrima lagrima = new Lagrima(getTearSize(), getX(), getY(), -getTearSpeed(), 0);
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }

    public void shootRight() {
        long currentTime = System.currentTimeMillis();
        if (isCanShoot() && currentTime - lastShootTime >= getShootDelay()) {
            Lagrima lagrima = new Lagrima(getTearSize(), getX(), getY(), getTearSpeed(), 0);
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

	public List<Lagrima> getLagrimas() {
		return lagrimas;
	}
}