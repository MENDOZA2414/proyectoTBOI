import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

public class Isaac extends Entidad{

    private int velocityX;
    private int velocityY;
    private boolean invencible;

    public List<Lagrima> lagrimas;
    public long lastShootTime;  // Tiempo del último disparo

    public Isaac(String spritePath, String tearPath, String nombre, int ancho, int alto, int speed, boolean canShoot, int tearSpeed, int shootDelay, int life, int x, int y) {
        super(spritePath, tearPath, nombre, ancho, alto, speed, canShoot, tearSpeed, shootDelay, life, x, y);
        this.velocityX = 0;
        this.velocityY = 0;
        this.invencible = false;
        
        lagrimas = new ArrayList<>();
        lastShootTime = 0;
    }

    public void paint(Graphics g) {
//    	g.setColor(Color.red);
//        g.fillRect(200, 200, CHARACTER_SIZE, CHARACTER_SIZE);
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

	public boolean detectarColision(Objeto objeto) {
        int ancho1 = objeto.getTamaño();
        int alto1 = objeto.getTamaño();
        int x1 = objeto.getX();
        int y1 = objeto.getY();

        int ancho2 = getAncho();
        int alto2 = getAlto();
        int x2 = getX();
        int y2 = getY();

        boolean colisionIzquierda = x1 + ancho1 > x2 && x1 < x2;
        boolean colisionDerecha = x1 < x2 + ancho2 && x1 + ancho1 > x2;
        boolean colisionArriba = y1 + alto1 > y2 && y1 < y2 + alto2;
        boolean colisionAbajo = y1 < y2 + alto2 - alto2/2 && y1 + alto1 > y2;

        if (colisionIzquierda && colisionDerecha && colisionArriba && colisionAbajo) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean detectarColision(Enemigo enemigo) {
    	boolean colision = false;
    	if(!isInvencible()){
    		
    		int ancho1 = enemigo.getAncho();
    		int alto1 = enemigo.getAlto();
    		int x1 = enemigo.getX();
    		int y1 = enemigo.getY();
    		
    		int ancho2 = getAncho();
    		int alto2 = getAlto();
    		int x2 = getX();
    		int y2 = getY();
    		
    		boolean colisionIzquierda = x1 + ancho1 > x2 && x1 < x2;
    		boolean colisionDerecha = x1 < x2 + ancho2 && x1 + ancho1 > x2;
    		boolean colisionArriba = y1 + alto1 > y2 && y1 < y2 + alto2;
    		boolean colisionAbajo = y1 < y2 + alto2 - alto2/2 && y1 + alto1 > y2;
    		
    		if (colisionIzquierda && colisionDerecha && colisionArriba && colisionAbajo) {
    			setLife(getLife()-1);
    			setInvencible(true);
			 	Timer timer = new Timer();
		        timer.schedule(new TimerTask() {
		            @Override
		            public void run() {
		            	setInvencible(false);
		            }
		        }, 1000);
                colision = true;
    		} else {
    			colision = false;
    		}
    	}
    	return colision;
    }
    
    public boolean colisionLagrima(Objeto objeto) {    
    	boolean colision = false;
    	 for (int i = 0; i < lagrimas.size(); i++) {
    	    Lagrima lagrima = lagrimas.get(i);
        	
    		int ancho1 = objeto.getTamaño();
    		int alto1 = objeto.getTamaño();
    		int x1 = objeto.getX();
    		int y1 = objeto.getY();
    		
    		int ancho2 = lagrima.getTamaño();
    		int alto2 = lagrima.getTamaño();
    		int x2 = lagrima.getX();
    		int y2 = lagrima.getY();
    		
    		boolean colisionIzquierda = x1 + ancho1 > x2 && x1 < x2;
    		boolean colisionDerecha = x1 < x2 + ancho2 && x1 + ancho1 > x2;
    		boolean colisionArriba = y1 + alto1 > y2 && y1 < y2 + alto2;
    		boolean colisionAbajo = y1 < y2 + alto2 - alto2/2 && y1 + alto1 > y2;
    		
    		if (colisionIzquierda && colisionDerecha && colisionArriba && colisionAbajo) {
                lagrimas.remove(i);
    			colision = true;
            } else {
            	colision = false;
            }
    	}
        return colision;
    }
    
    public boolean colisionLagrima(Enemigo enemigo) {    
    	boolean colision = false;
    	 for (int i = 0; i < lagrimas.size(); i++) {
    	    Lagrima lagrima = lagrimas.get(i);
        	
    		int ancho1 = enemigo.getAncho();
    		int alto1 = enemigo.getAlto();
    		int x1 = enemigo.getX();
    		int y1 = enemigo.getY();
    		
    		int ancho2 = lagrima.getTamaño();
    		int alto2 = lagrima.getTamaño();
    		int x2 = lagrima.getX();
    		int y2 = lagrima.getY();
    		
    		boolean colisionIzquierda = x1 + ancho1 > x2 && x1 < x2;
    		boolean colisionDerecha = x1 < x2 + ancho2 && x1 + ancho1 > x2;
    		boolean colisionArriba = y1 + alto1 > y2 && y1 < y2 + alto2;
    		boolean colisionAbajo = y1 < y2 + alto2 - alto2/2 && y1 + alto1 > y2;
    		
    		if (colisionIzquierda && colisionDerecha && colisionArriba && colisionAbajo) {
                lagrimas.remove(i);
    			colision = true;
            } else {
            	colision = false;
            }
    	}
        return colision;
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
}