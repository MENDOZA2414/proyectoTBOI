import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Jugador extends Entidad{

    private int velocityX;
    private int velocityY;
    private boolean invencible;
    
    public List<Lagrima> lagrimas;
    public long lastShootTime;  // Tiempo del último disparo

    public Jugador(String spritePath, String tearPath, String nombre, int ancho, int alto, int speed, boolean canShoot, int tearSpeed, int shootDelay, int life, int x, int y) {
        super(spritePath, tearPath, nombre, ancho, alto, speed, canShoot, tearSpeed, shootDelay, life, x, y);
        this.velocityX = 0;
        this.velocityY = 0;
        this.invencible = false;
        
        lagrimas = new ArrayList<>();
        lastShootTime = 0;
    }

    public void activarInmunidad(int segundos) {
        invencible = true;
        Timer invencibleTimer = new Timer();
        invencibleTimer.schedule(new TimerTask() {
            @Override
            public void run() {
            	invencible = false;
            }
        }, segundos*1000); // 1000 milisegundos = 1 segundos
    }
    
    public void paint(Graphics g) {
        g.drawImage(getSprite(), getX() - getAncho() / 2, getY() - getAlto() / 2, getAncho(), getAlto(), null);

        for (Lagrima lagrima : lagrimas) {
            lagrima.paint(g);
        }
    }

    public void update(int velocityX, int velocityY) {
    	setX(getX()+velocityX);
    	setY(getY()+velocityY);

        for (Lagrima lagrima : lagrimas) {
            lagrima.update();
        }

        lagrimas.removeIf(lagrima -> lagrima.getX() < 80 || lagrima.getX() > Juego.WIDTH - 110|| lagrima.getY() < 80 || lagrima.getY() > Juego.HEIGHT - 110);
    }

    public void shootUp() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= getShootDelay()) {
            Lagrima lagrima = new Lagrima(getX(), getY(), 0, -getTearSpeed());
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }

    public void shootDown() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= getShootDelay()) {
            Lagrima lagrima = new Lagrima(getX(), getY(), 0, getTearSpeed());
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }

    public void shootLeft() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= getShootDelay()) {
            Lagrima lagrima = new Lagrima(getX(), getY(), -getTearSpeed(), 0);
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }

    public void shootRight() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= getShootDelay()) {
            Lagrima lagrima = new Lagrima(getX(), getY(), getTearSpeed(), 0);
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }
	public boolean isInvencible() {
		return invencible;
	}

	public void setInvencible(boolean invencible) {
		this.invencible = invencible;
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