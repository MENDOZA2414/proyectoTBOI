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

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    private static final int FPS = 60;

    private Random random = new Random();
    private int numeroAleatorio = random.nextInt(4) + 1;
    public Isaac isaac;

    private BufferedImage fondo;
    public List<Objeto> objetos = new ArrayList<>();
    
    private Objeto puertaArriba = new Objeto("resources/puertaArriba.png", 80, 352, 20);
    private Objeto puertaAbajo = new Objeto("resources/puertaAbajo.png", 80, 352, 395);
    private Objeto puertaIzquierda = new Objeto("resources/puertaIzquierda.png", 80, 30, 190);
    private Objeto puertaDerecha = new Objeto("resources/puertaDerecha.png", 80, 710, 190);

    public Juego() {
        isaac = new Isaac(WIDTH / 2, HEIGHT / 2);
        isaac.lastShootTime = 0;

        try {
            fondo = ImageIO.read(new File("resources/instrucciones.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Timer timer = new Timer(1000 / FPS, e -> {
            update();
            repaint();
            for (Objeto objeto : objetos) {
            	if(objeto.colisionObjeto(isaac.getFrame(), isaac.getX(), isaac.getY())) {
            		System.out.println("Colision");
            	}
            }
        });
        timer.start();
        
        for(int i=0; i<5; i++) {
            Random random = new Random();
            int n = 50; // Límite inferior
            int m = 450; // Límite superior

            int x = random.nextInt(m - n + 1) + n;
            int y = random.nextInt(m - n + 1) + n;
        	objetos.add(new Objeto("resources/popo.png", 50, x, y));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //Fondo agregado

        g.drawImage(fondo, 0, 0, WIDTH, HEIGHT - 28, null);

        for (Objeto objeto : objetos) {
            g.drawImage(objeto.getSprite(), objeto.getX(), objeto.getY(), objeto.getTamaño(), objeto.getTamaño(), null);
        }
        switch (numeroAleatorio) {

            case 1:
                g.drawImage(puertaArriba.getSprite(), puertaArriba.getX(), puertaArriba.getY(), puertaArriba.getTamaño() + 20, puertaArriba.getTamaño() - 20, null);
            break;

            case 2:
                g.drawImage(puertaAbajo.getSprite(), puertaAbajo.getX(), puertaAbajo.getY(), puertaAbajo.getTamaño() + 20, puertaAbajo.getTamaño() - 25, null);
            break;

            case 3:
                g.drawImage(puertaIzquierda.getSprite(), puertaIzquierda.getX(), puertaIzquierda.getY(), puertaIzquierda.getTamaño() - 20, puertaIzquierda.getTamaño() + 20, null);
            break;
            
            case 4:
                g.drawImage(puertaDerecha.getSprite(), puertaDerecha.getX(), puertaDerecha.getY(), puertaDerecha.getTamaño() - 20, puertaDerecha.getTamaño() + 20, null);
            break;
        }

        isaac.paint(g);

        for (Lagrima lagrima : isaac.lagrimas) {
            lagrima.paint(g);
        }
    }

    private void update() {

        isaac.update(isaac.getVelocityX(), isaac.getVelocityY());

        for (Lagrima lagrima : isaac.lagrimas) {
            lagrima.update();
        }

        if (isaac.getX() < 90) {
            isaac.setVelocityX(0);
            isaac.setX(90);
        }
        if (isaac.getX() > WIDTH - 95) {
            isaac.setVelocityX(0);
            isaac.setX(WIDTH - 95);
        }
        if (isaac.getY() < 50) {
            isaac.setVelocityY(0);
            isaac.setY(50);
        }
        if (isaac.getY() > HEIGHT - 135) {
            isaac.setVelocityY(0);
            isaac.setY(HEIGHT - 135);
        }

        isaac.lagrimas.removeIf(lagrima -> lagrima.getX() < 85 || lagrima.getX() > WIDTH - 110 || lagrima.getY() < 85 || lagrima.getY() > HEIGHT - 110);
    }
}