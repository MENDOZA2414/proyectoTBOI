import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class Jugador extends Entidad{

    private int velocityX;
    private int velocityY;
    
    private List<Lagrima> lagrimas;
    public long lastShootTime;  // Tiempo del último disparo
    private int monedasRecolectadas;
    
    public Jugador(String spritePath, String tearPath, String nombre, int ancho, int alto, int speed, boolean canMove, boolean canShoot, int daamage, int tearSize, int tearSpeed, float tearRange, int shootDelay, int life, int immunityTime, int x, int y) {
        super(spritePath, tearPath, nombre, ancho, alto, speed, canMove, canShoot, daamage, tearSize, tearSpeed, tearRange, shootDelay, life, immunityTime, x, y);
        this.velocityX = 0;
        this.velocityY = 0;
        lagrimas = new ArrayList<>();
        lastShootTime = 0;
        monedasRecolectadas = 0;
    }
    
    public void paint(Graphics g, boolean invencible) {
        if(!invencible) {
        	g.drawImage(getSprite(), getX() - getAncho() / 2, getY() - getAlto() / 2, getAncho(), getAlto(), null);
        }
        else {
        
        	float brightness = 0.5f; // Valor de brillo deseado

        	RescaleOp op = new RescaleOp(brightness, 0, null);

        	BufferedImage modifiedImage = op.filter(getSprite(), null);
        	g.drawImage(modifiedImage, getX() - getAncho() / 2, getY() - getAlto() / 2, getAncho(), getAlto(), null);

        }
        
        for (Lagrima lagrima : lagrimas) {
            g.drawImage(getTearSprite(), lagrima.getX() - lagrima.getTamaño() / 2, lagrima.getY() - lagrima.getTamaño() / 2, lagrima.getTamaño(), lagrima.getTamaño(), null);
        }
    }

    public void update(int velocityX, int velocityY) {
        if (isCanMove()) {
            setX(getX() + velocityX);
            setY(getY() + velocityY);
            
            long currentTime = System.currentTimeMillis();
            
            Iterator<Lagrima> iterator = lagrimas.iterator();
            while (iterator.hasNext()) {
                Lagrima lagrima = iterator.next();
                lagrima.update(); // Actualizar estado de la lágrima
                
                if (currentTime - lagrima.getStartTime() >= lagrima.getLifetime()) {
                    lagrima.setVelocityY(lagrima.getVelocityY() + 1); // Aumentar la velocidad de caída en cada actualización
                    lagrima.update(); // Actualizar la posición de la lágrima según la nueva velocidad
                    
                    if (lagrima.getY() > getY()+getAlto()/3) {
                        iterator.remove(); // Eliminar lágrima si ha alcanzado su tiempo de vida máximo y ha salido de la pantalla
                    }
                    if (lagrima.getY() < getY()-getAlto()/3) {
                        iterator.remove(); // Eliminar lágrima si ha alcanzado su tiempo de vida máximo y ha salido de la pantalla
                    }
                }
            }
            
            lagrimas.removeIf(lagrima -> lagrima.getX() < 80 || lagrima.getX() > Juego.WIDTH - 110 ||
                    lagrima.getY() < 80 || lagrima.getY() > Juego.HEIGHT - 110);
            
            // Verificar si se puede disparar nuevamente
            if (currentTime - lastShootTime >= getShootDelay()) {
                setCanShoot(true);
            }
        }
    }

    public void shootUp() {
        long currentTime = System.currentTimeMillis();
        if (isCanShoot() && currentTime - lastShootTime >= getShootDelay()) {
            Lagrima lagrima = new Lagrima(getTearSize(), getX(), getY(), 0, -getTearSpeed(), getTearRange());
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
            new Sonido("lagrima", 0.2f);
        }
    }

    public void shootDown() {
        long currentTime = System.currentTimeMillis();
        if (isCanShoot() && currentTime - lastShootTime >= getShootDelay()) {
            Lagrima lagrima = new Lagrima(getTearSize(), getX(), getY(), 0, getTearSpeed(), getTearRange());
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
            new Sonido("lagrima", 0.2f);
        }
    }

    public void shootLeft() {
        long currentTime = System.currentTimeMillis();
        if (isCanShoot() && currentTime - lastShootTime >= getShootDelay()) {
            Lagrima lagrima = new Lagrima(getTearSize(), getX(), getY(), -getTearSpeed(), 0, getTearRange());
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
            new Sonido("lagrima", 0.2f);
        }
    }

    public void shootRight() {
        long currentTime = System.currentTimeMillis();
        if (isCanShoot() && currentTime - lastShootTime >= getShootDelay()) {
            Lagrima lagrima = new Lagrima(getTearSize(), getX(), getY(), getTearSpeed(), 0, getTearRange());
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
            new Sonido("lagrima", 0.2f);
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

	public int getMonedasRecolectadas() {
		return monedasRecolectadas;
	}

	public void setMonedasRecolectadas(int monedasRecolectadas) {
		this.monedasRecolectadas = monedasRecolectadas;
	}
}