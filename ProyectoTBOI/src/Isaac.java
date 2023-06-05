import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

import javax.imageio.ImageIO;

public class Isaac {

    public static final int CHARACTER_SPEED = 8;
    private static final int CHARACTER_SIZE = 72;
    private static final int BALL_SPEED = 5;
    private static final int SHOOT_DELAY = 450; // 1000 = 1 segundo

    private int x;
    private int y;
    private int velocityX;
    private int velocityY;

    private BufferedImage isaac;
    private BufferedImage tear;

    public List<Lagrima> lagrimas;
    public long lastShootTime;  // Tiempo del último disparo
    private int vidas = 6;
    private boolean invencible = false;

    public Isaac(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;

        try {
            isaac = ImageIO.read(new File("resources/isaacola.png"));
            tear = ImageIO.read(new File("resources/normaltear.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        lagrimas = new ArrayList<>();
        lastShootTime = 0;
    }

    public void paint(Graphics g) {
//    	g.setColor(Color.red);
//        g.fillRect(200, 200, CHARACTER_SIZE, CHARACTER_SIZE);
        g.drawImage(isaac, x - CHARACTER_SIZE / 2, y - CHARACTER_SIZE / 2, CHARACTER_SIZE, CHARACTER_SIZE, null);

        for (Lagrima lagrima : lagrimas) {
            lagrima.paint(g);
        }
    }

    public void update(int velocityX, int velocityY) {
        x += velocityX;
        y += velocityY;

        for (Lagrima lagrima : lagrimas) {
            lagrima.update();
        }

        lagrimas.removeIf(lagrima -> lagrima.getX() < 80 || lagrima.getX() > Juego.WIDTH - 110|| lagrima.getY() < 80 || lagrima.getY() > Juego.HEIGHT - 110);
    }

    public void shootUp() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Lagrima lagrima = new Lagrima(x, y, 0, -BALL_SPEED);
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }

    public void shootDown() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Lagrima lagrima = new Lagrima(x, y, 0, BALL_SPEED);
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }

    public void shootLeft() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Lagrima lagrima = new Lagrima(x, y, -BALL_SPEED, 0);
            lagrimas.add(lagrima);
            lastShootTime = currentTime;
        }
    }

    public void shootRight() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Lagrima lagrima = new Lagrima(x, y, BALL_SPEED, 0);
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

        int ancho2 = this.CHARACTER_SIZE;
        int alto2 = this.CHARACTER_SIZE;
        int x2 = this.getX();
        int y2 = this.getY();

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
    		
    		int ancho2 = this.CHARACTER_SIZE;
    		int alto2 = this.CHARACTER_SIZE;
    		int x2 = this.getX();
    		int y2 = this.getY();
    		
    		boolean colisionIzquierda = x1 + ancho1 > x2 && x1 < x2;
    		boolean colisionDerecha = x1 < x2 + ancho2 && x1 + ancho1 > x2;
    		boolean colisionArriba = y1 + alto1 > y2 && y1 < y2 + alto2;
    		boolean colisionAbajo = y1 < y2 + alto2 - alto2/2 && y1 + alto1 > y2;
    		
    		if (colisionIzquierda && colisionDerecha && colisionArriba && colisionAbajo) {
    			setVidas(getVidas()-1);
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

	public BufferedImage getFrame() {
		return isaac;
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

	public static int getCharacterSize() {
		return CHARACTER_SIZE;
	}

	public int getCharacterSpeed(){
        return CHARACTER_SPEED;
    }

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}
}