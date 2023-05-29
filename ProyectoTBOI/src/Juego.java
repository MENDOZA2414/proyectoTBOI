import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private static final int CHARACTER_SIZE = 62;
    private static final int CHARACTER_SPEED = 6;
    private static final int BALL_SIZE = 30;
    private static final int BALL_SPEED = 8;
    private static final int FPS = 60;
    private static final int SHOOT_DELAY = 450; //1000 = 1 segundo
    
    private Random random = new Random();
    private int numeroAleatorio = random.nextInt(4) + 1; 
    private int characterX;
    private int characterY;

    private int velocityX;
    private int velocityY;

    private List<Ball> balls;
    private long lastShootTime; // Tiempo del último disparo

    private BufferedImage isaac;
    private BufferedImage tear;
    private BufferedImage fondo;

   
    private Objeto caca = new Objeto("resources/popo.png",70,100,100);
    private Objeto puertaArriba = new Objeto("resources/puertaArriba.png",80,352,20);
    private Objeto puertaAbajo = new Objeto("resources/puertaAbajo.png",80,352,395);
    
    private Objeto puertaIzquierda = new Objeto("resources/puertaIzquierda.png",80,30,190);
    private Objeto puertaDerecha = new Objeto("resources/puertaDerecha.png",80,710,190);
    
    public Juego() {
        //iniciarMusica();
        characterX = WIDTH / 2;
        characterY = HEIGHT / 2;
        velocityX = 0;
        velocityY = 0;

        balls = new ArrayList<>();
        lastShootTime = 0;

        try {
            isaac = ImageIO.read(new File("resources/isaacola.png"));
            tear = ImageIO.read(new File("resources/normaltear.png"));
            fondo = ImageIO.read(new File("resources/instrucciones.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }   

        Timer timer = new Timer(1000 / FPS, e -> {
            update();
            repaint();
        });
        timer.start();
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //fondo agregado
        g.drawImage(fondo, 0, 0, WIDTH, HEIGHT-28, null);
        
        g.drawImage(caca.getSprite(),caca.getX(),caca.getY(),caca.getTamaño(),caca.getTamaño(),null);
        
       

        switch(numeroAleatorio) {
        case 1:
            g.drawImage(puertaArriba.getSprite(),puertaArriba.getX(),puertaArriba.getY(),puertaArriba.getTamaño()+20,puertaArriba.getTamaño()-20,null);

        	break;
        case 2:
            g.drawImage(puertaAbajo.getSprite(),puertaAbajo.getX(),puertaAbajo.getY(),puertaAbajo.getTamaño()+20,puertaAbajo.getTamaño()-25,null);

        	break;
        case 3:
            g.drawImage(puertaIzquierda.getSprite(),puertaIzquierda.getX(),puertaIzquierda.getY(),puertaIzquierda.getTamaño()-20,puertaIzquierda.getTamaño()+20,null);

        	break;
        case 4:
            g.drawImage(puertaDerecha.getSprite(),puertaDerecha.getX(),puertaDerecha.getY(),puertaDerecha.getTamaño()-20,puertaDerecha.getTamaño()+20,null);
        	break;
        	
        }

        
        // Dibuja el personaje de Isaac
        g.drawImage(isaac, characterX - CHARACTER_SIZE / 2, characterY - CHARACTER_SIZE / 2, CHARACTER_SIZE,CHARACTER_SIZE, null);

        // Dibuja las pelotas
        for (Ball ball : balls) {
            g.drawImage(tear, ball.x - BALL_SIZE / 2, ball.y - BALL_SIZE / 2, BALL_SIZE, BALL_SIZE, null);
        }
        

    }
    
    private void update() {
        characterX += velocityX;
        characterY += velocityY;

        // Actualiza las posiciones de las pelotas
        for (Ball ball : balls) {
            ball.x += ball.velocityX;
            ball.y += ball.velocityY;
        }

        //si isaac llega a las paredes, detenerlo para que no pueda sobrepasarla
        if (characterX < 90) {
            characterX = 90;
          
        }
        if (characterX > WIDTH - 95) {
            characterX = WIDTH - 95;
        
        }
        if (characterY < 50) {
            characterY = 50;
           
        }
        if (characterY > HEIGHT - 135) {
            characterY = HEIGHT - 135;
           
        }

        // Comprueba si alguna pelota está fuera de los límites de la pantalla o las paredes
        balls.removeIf(ball -> ball.x < 85 || ball.x > WIDTH-110 || ball.y < 85 || ball.y > HEIGHT-110);
    }

    public void shootUp() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Ball ball = new Ball(characterX, characterY, 0, -BALL_SPEED);
            balls.add(ball);
            lastShootTime = currentTime;
        }
    }

    public void shootDown() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Ball ball = new Ball(characterX, characterY, 0, BALL_SPEED);
            balls.add(ball);
            lastShootTime = currentTime;
        }
    }

    public void shootLeft() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Ball ball = new Ball(characterX, characterY, -BALL_SPEED, 0);
            balls.add(ball);
            lastShootTime = currentTime;
        }
    }

    public void shootRight() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Ball ball = new Ball(characterX, characterY, BALL_SPEED, 0);
            balls.add(ball);
            lastShootTime = currentTime;
        }
    }


    private class Ball {
        private int x;
        private int y;
        private int velocityX;
        private int velocityY;

        public Ball(int x, int y, int velocityX, int velocityY) {
            this.x = x;
            this.y = y;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
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

	public static int getCharacterSpeed() {
		return CHARACTER_SPEED;
	}
}