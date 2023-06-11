import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemigo extends Entidad {
    private List<Lagrima> lagrimas;
    public long lastShootTime;  // Tiempo del último disparo

    public Enemigo(String spritePath, String tearPath, String nombre, int ancho, int alto, int speed, boolean canMove, boolean canShoot, int tearSize, int tearSpeed, int shootDelay, int life, int immunityTime, int x, int y) {
        super(spritePath, tearPath, nombre, ancho, alto, speed, canMove, canShoot, tearSize, tearSpeed, shootDelay, life, immunityTime, x, y);
        
        lagrimas = new ArrayList<>();
        lastShootTime = 0;
    }

	public void mover() {
		if(isCanMove()) {
			Random random = new Random();
			int cambioX = random.nextInt(3) - 1; //Valores aleatorios entre -1 y 1
			int cambioY = random.nextInt(3) - 1; //Valores aleatorios entre -1 y 1
			
			int nuevaX = getX() + getSpeed() * cambioX;
			int nuevaY = getY() + getSpeed() * cambioY;
			
			if(!(nuevaX < 80 || nuevaX > Juego.WIDTH - 110|| nuevaY < 80 || nuevaY > Juego.HEIGHT - 110)) {
				setX(nuevaX);
				setY(nuevaY);
			}		
		}
    }
	
	 public void paint(Graphics g) {
        g.drawImage(getSprite(), getX(), getY(), getAncho(), getAlto(), null);

        for (Lagrima lagrima : lagrimas) {
            g.drawImage(getTearSprite(), lagrima.getX(), lagrima.getY(), lagrima.getTamaño(), lagrima.getTamaño(), null);
            lagrima.update();
        }
        lagrimas.removeIf(lagrima -> lagrima.getX() < 80 || lagrima.getX() > Juego.WIDTH - 110|| lagrima.getY() < 80 || lagrima.getY() > Juego.HEIGHT - 110);
    }
	
	 public void disparar(int playerX, int playerY) {
	    long currentTime = System.currentTimeMillis();
	    if (isCanShoot() && currentTime - lastShootTime >= getShootDelay()) {
	        int deltaX = playerX - getX()-getTearSize()/2;
	        int deltaY = playerY - getY()-getTearSize()/2;
	        double angle = Math.atan2(deltaY, deltaX);
	        int speedX = (int) (getTearSpeed() * Math.cos(angle));
	        int speedY = (int) (getTearSpeed() * Math.sin(angle));
	        
	        Lagrima lagrima = new Lagrima(getTearSize(), getX()+getTearSize()/2, getY()+ getTearSize() / 2, speedX, speedY);
	        lagrimas.add(lagrima);
	        lastShootTime = currentTime;
	    }
	}

	public List<Lagrima> getLagrimas() {
		return lagrimas;
	}
}