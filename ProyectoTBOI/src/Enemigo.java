import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Enemigo extends Entidad {
    private List<Lagrima> lagrimas;
    public long lastShootTime;  // Tiempo del último disparo
    
    private Item item;
    private float dropProbability;
    
    public Enemigo(String spritePath, String tearPath, String nombre, int ancho, int alto, int speed, boolean canMove, boolean canShoot, int tearSize, int tearSpeed, float tearRange, int shootDelay, int life, int immunityTime, Item item, float dropProbability, int x, int y) {
        super(spritePath, tearPath, nombre, ancho, alto, speed, canMove, canShoot, tearSize, tearSpeed, tearRange, shootDelay, life, immunityTime, x, y);
        
        lagrimas = new ArrayList<>();
        lastShootTime = 0;
        this.item = item;
        this.dropProbability = dropProbability;
    }

	public void mover() {
		if(isCanMove()) {
			Random random = new Random();
			int cambioX = random.nextInt(3) - 1; //Valores aleatorios entre -1 y 1
			int cambioY = random.nextInt(3) - 1; //Valores aleatorios entre -1 y 1
			
			int nuevaX = getX() + getSpeed() * cambioX;
			int nuevaY = getY() + getSpeed() * cambioY;
			
			if(!(nuevaX < 90 || nuevaX > Juego.WIDTH - 120|| nuevaY < 80 || nuevaY > Juego.HEIGHT - 120)) {
				setX(nuevaX);
				setY(nuevaY);
			}		
		}
    }
	
	public void moverHaciaIsaacConEvasion(int targetX, int targetY, List<Objeto> objetos) {
	    if (isCanMove()) {
	        int deltaX = targetX - getX() - getAncho() / 2;
	        int deltaY = targetY - getY() - getAlto() / 2;

	        int distancia = (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

	        if (distancia > 0) {
	            int velocidadX = (int) (getSpeed() * deltaX / distancia);
	            int velocidadY = (int) (getSpeed() * deltaY / distancia);

	            int nuevaX = getX() + velocidadX;
	            int nuevaY = getY() + velocidadY;

	            // Verificar colisiones con obstáculos
	            boolean colision = false;
	            for (Objeto objeto : objetos) {
	                if (objeto.intersecta(nuevaX, nuevaY, getAncho(), getAlto())) {
	                    colision = true;
	                    break;
	                }
	            }

	            // Evitar obstáculos
	            if (colision) {
	                int desplazamientoX = 0;
	                int desplazamientoY = 0;

	                for (Objeto objeto : objetos) {
	                    int evasionX = objeto.getX() + objeto.getAncho() / 2 - getX();
	                    int evasionY = objeto.getY() + objeto.getAlto() / 2 - getY();

	                    double distanciaEvasion = Math.sqrt(evasionX * evasionX + evasionY * evasionY);

	                    if (distanciaEvasion < objeto.getRadio()) {
	                        double normalizadoX = evasionX / distanciaEvasion;
	                        double normalizadoY = evasionY / distanciaEvasion;

	                        // Ajustar la magnitud del desplazamiento en función de la distancia de evasión
	                        double factorEscala = 1.0 - (distanciaEvasion / objeto.getRadio());
	                        int desplazamiento = (int) (getSpeed() * factorEscala);

	                        desplazamientoX -= (int) (normalizadoX * desplazamiento);
	                        desplazamientoY -= (int) (normalizadoY * desplazamiento);
	                    }
	                }

	                nuevaX = getX() + desplazamientoX;
	                nuevaY = getY() + desplazamientoY;
	            }
	            // Verificar límites del juego
	            if (!(nuevaX < 80 || nuevaX > Juego.WIDTH - 110 || nuevaY < 80 || nuevaY > Juego.HEIGHT - 110)) {
	                setX(nuevaX);
	                setY(nuevaY);
	            }
	        }
	    }
	}

	public boolean generarItem() {
		float randomNum = new Random().nextFloat();
        if (randomNum <= getDropProbability() && item != null) {
        	item.setX(getX()+getAncho()/2);
        	item.setY(getY()+getAlto()/2);
        	return true;
        }
        else {
        	return false;
        }
	}
	
	public void paint(Graphics g) {
        g.drawImage(getSprite(), getX(), getY(), getAncho(), getAlto(), null);

        for (Lagrima lagrima : lagrimas) {
            g.drawImage(getTearSprite(), lagrima.getX(), lagrima.getY(), lagrima.getTamaño(), lagrima.getTamaño(), null);
            lagrima.update();
        }
        
        long currentTime = System.currentTimeMillis();
        
        Iterator<Lagrima> iterator = lagrimas.iterator();
        while (iterator.hasNext()) {
            Lagrima lagrima = iterator.next();
            lagrima.update(); // Actualizar estado de la lágrima
            
            if (currentTime - lagrima.getStartTime() >= lagrima.getLifetime()) {
                lagrima.setVelocityY(lagrima.getVelocityY() + 1); // Aumentar la velocidad de caída en cada actualización
                lagrima.update(); // Actualizar la posición de la lágrima según la nueva velocidad
                
                iterator.remove();
            }
        }
        
        lagrimas.removeIf(lagrima -> lagrima.getX() < 80 || lagrima.getX() > Juego.WIDTH - 110 ||
                lagrima.getY() < 80 || lagrima.getY() > Juego.HEIGHT - 110);
        
        // Verificar si se puede disparar nuevamente
        if (currentTime - lastShootTime >= getShootDelay()) {
            setCanShoot(true);
        }
    }
	
	public void disparar(int playerX, int playerY) {
	    long currentTime = System.currentTimeMillis();
	    if (isCanShoot() && currentTime - lastShootTime >= getShootDelay()) {
	        int deltaX = playerX - getX()-getTearSize()/2;
	        int deltaY = playerY - getY()-getTearSize()/2;
	        double angle = Math.atan2(deltaY, deltaX);
	        int speedX = (int) (getTearSpeed() * Math.cos(angle));
	        int speedY = (int) (getTearSpeed() * Math.sin(angle));
	        
	        Lagrima lagrima = new Lagrima(getTearSize(), getX()+getTearSize()/2, getY()+ getTearSize() / 2, speedX, speedY, getTearRange());
	        lagrimas.add(lagrima);
	        lastShootTime = currentTime;
	    }
	}

	public List<Lagrima> getLagrimas() {
		return lagrimas;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public float getDropProbability() {
		return dropProbability;
	}

	public void setDropProbability(float dropProbability) {
		this.dropProbability = dropProbability;
	}
}