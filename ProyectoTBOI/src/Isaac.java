import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.FloatControl;

public class Isaac extends JPanel implements KeyListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private static final int CHARACTER_SIZE = 62;
    private static final int CHARACTER_SPEED = 3;
    private static final int BALL_SIZE = 30;
    private static final int BALL_SPEED = 8;
    private static final int FPS = 60;
    private static final int SHOOT_DELAY = 450; //1000 = 1 segundo

    private int characterX;
    private int characterY;

    private int velocityX;
    private int velocityY;

    private List<Ball> balls;
    private long lastShootTime; // Tiempo del último disparo

    private BufferedImage isaac;
    private BufferedImage tear;
    private BufferedImage fondo;

    private Clip clip;

    private float volume;

    public Isaac() {
        iniciarMusica();
        
        characterX = WIDTH / 2;
        characterY = HEIGHT / 2;
        velocityX = 0;
        velocityY = 0;

        balls = new ArrayList<>();
        lastShootTime = 0;

        try {
            isaac = ImageIO.read(new File("resources/isaacola.png"));
            tear = ImageIO.read(new File("resources/normaltear.png"));
            fondo = ImageIO.read(new File("resources/instrucciones2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("The Binding of Isaac");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        
        

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
        g.drawImage(fondo, 0, 0, WIDTH-16, HEIGHT-39, null);

        // Dibuja el personaje de Isaac
        g.drawImage(isaac, characterX - CHARACTER_SIZE / 2, characterY - CHARACTER_SIZE / 2, CHARACTER_SIZE,CHARACTER_SIZE, null);

        
        // Dibuja las pelotas
        for (Ball ball : balls) {
            g.drawImage(tear, ball.x - BALL_SIZE / 2, ball.y - BALL_SIZE / 2, BALL_SIZE, BALL_SIZE, null);
        }
        

    }
    
    /*Timer timer2 = new Timer(1000 / FPS, e -> {
        
        if (characterX <= 0 || characterX >= WIDTH - 110) {
            characterX = -(characterX-5);
        }
        
        if (characterY <= 0 || characterY >= HEIGHT - 110) {
            characterY = -(characterY-5);
        }

    });*/
    
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
            velocityX = -velocityX;
        }
        if (characterX > WIDTH - 110) {
            characterX = WIDTH - 110;
            velocityX = -velocityX;
        }
        if (characterY < 50) {
            characterY = 50;
            velocityY = -velocityY;
        }
        if (characterY > HEIGHT - 140) {
            characterY = HEIGHT - 140;
            velocityY = -velocityY;
        }

        // Comprueba si alguna pelota está fuera de los límites de la pantalla o las paredes
        balls.removeIf(ball -> ball.x < 85 || ball.x > WIDTH-110 || ball.y < 85 || ball.y > HEIGHT-110);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            velocityX = -CHARACTER_SPEED;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            velocityX = CHARACTER_SPEED;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            velocityY = -CHARACTER_SPEED;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            velocityY = CHARACTER_SPEED;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            shootUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            shootDown();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            shootLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            shootRight();
        } else if(e.getKeyCode() == KeyEvent.VK_M){

            if (clip.isRunning()) {
                System.out.println("Muted...");
                clip.stop();
            } else{
                System.out.println("Playing...");
                clip.start();
            }
        
        } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.out.println("Exit...");
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
            velocityX = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
            velocityY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No se utiliza en este ejemplo
    }

    private void shootUp() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Ball ball = new Ball(characterX, characterY, 0, -BALL_SPEED);
            balls.add(ball);
            lastShootTime = currentTime;
        }
    }

    private void shootDown() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Ball ball = new Ball(characterX, characterY, 0, BALL_SPEED);
            balls.add(ball);
            lastShootTime = currentTime;
        }
    }

    private void shootLeft() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Ball ball = new Ball(characterX, characterY, -BALL_SPEED, 0);
            balls.add(ball);
            lastShootTime = currentTime;
        }
    }

    private void shootRight() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_DELAY) {
            Ball ball = new Ball(characterX, characterY, BALL_SPEED, 0);
            balls.add(ball);
            lastShootTime = currentTime;
        }
    }

    public static void main(String[] args) {
        Isaac nuevo = new Isaac();
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

    private void iniciarMusica() {
        try {
            // Crear objeto Clip para reproducir el audio
            clip = AudioSystem.getClip();

            clip.open(AudioSystem.getAudioInputStream(new File("music\\MainSong.wav").getAbsoluteFile()));

            //ajustar volumen audio
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume = 0.06f; // volumen
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
            
            // Reproducir el audio
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException l) {
            l.printStackTrace();
        }
    }
    
    private void detenerMusica() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

}