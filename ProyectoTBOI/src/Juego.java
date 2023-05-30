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

    public static final int WIDTH = 1080;
    public static final int HEIGHT = 690;
    private static final int FPS = 60;

    private Random random = new Random();
    private int numeroAleatorio = random.nextInt(4) + 1;
    public Isaac isaac;

    private BufferedImage fondo;
    public List<Objeto> objetos = new ArrayList<>();
    public List<Puerta> puertas = new ArrayList<>();
    
    public Juego() {
        isaac = new Isaac(WIDTH / 2, HEIGHT / 2);
        isaac.lastShootTime = 0;
        
        generarFondo("resources/instrucciones.png");
        generarPuertas();
        
        Timer timer = new Timer(1000 / FPS, e -> {
            update();
            repaint();
            
            //Colisiones puertas objetos
            for (Objeto objeto : objetos) {
            	if(objeto.detectarColision(isaac)) {
            		System.out.println("Colision objeto");
            	}
            }
            for (Puerta puerta : puertas) {
            	if(puerta.detectarColision(isaac) && puerta.isAbierta()) {
            		System.out.println("Colision puerta");
            		nuevaSala();
            	}
            }
        });
        timer.start();
        generarObjetos();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //Fondo agregado

        g.drawImage(fondo, 0, 0, WIDTH, HEIGHT, null);

        for (Objeto objeto : objetos) {
            g.drawImage(objeto.getSprite(), objeto.getX(), objeto.getY(), objeto.getTamaño(), objeto.getTamaño(), null);
        }
        
        switch (numeroAleatorio) {
        case 1:
            g.drawImage(puertas.get(0).getSprite(), puertas.get(0).getX(), puertas.get(0).getY(), puertas.get(0).getTamaño() + 20, puertas.get(0).getTamaño() - 20, null);
            break;

        case 2:    
            g.drawImage(puertas.get(1).getSprite(), puertas.get(1).getX(), puertas.get(1).getY(), puertas.get(1).getTamaño() + 20, puertas.get(1).getTamaño() - 25, null);
            break;

        case 3:
            g.drawImage(puertas.get(2).getSprite(), puertas.get(2).getX(), puertas.get(2).getY(), puertas.get(2).getTamaño() - 20, puertas.get(2).getTamaño() + 20, null);
            break;

        case 4:
            g.drawImage(puertas.get(3).getSprite(), puertas.get(3).getX(), puertas.get(3).getY(), puertas.get(3).getTamaño() - 20, puertas.get(3).getTamaño() + 20, null);
            break;
        }

        isaac.paint(g);

        for (Lagrima lagrima : isaac.lagrimas) {
            lagrima.paint(g);
        }
    }
    
    public void generarObjetos() {
        int areaAncho = 780;
        int areaAlto = 400;
        int limiteInferiorX = (Juego.WIDTH - areaAncho) / 2;
        int limiteSuperiorX = limiteInferiorX + areaAncho;
        int limiteInferiorY = (Juego.HEIGHT - areaAlto) / 2;
        int limiteSuperiorY = limiteInferiorY + areaAlto;

        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            int x = random.nextInt(limiteSuperiorX - limiteInferiorX + 1) + limiteInferiorX;
            int y = random.nextInt(limiteSuperiorY - limiteInferiorY + 1) + limiteInferiorY;
            objetos.add(new Objeto("resources/popo.png", 50, x, y));
        }
    }
    
    public void eliminarObjetos() {
    	objetos.clear();
    }
    
    private void generarPuertas() {
        int puertaAncho = 90;
        int puertaAlto = 352;

        // Puerta arriba
        int puertaArribaX = (WIDTH - puertaAncho) / 2;
        int puertaArribaY = 40;
        Puerta puertaArriba = new Puerta("resources/puertaArriba.png", puertaAncho, puertaArribaX, puertaArribaY, true);
        puertas.add(puertaArriba);
   
        // Puerta abajo    
        int puertaAbajoX = (WIDTH - puertaAncho) / 2;
        int puertaAbajoY = HEIGHT - puertaAlto + 245;
        Puerta puertaAbajo = new Puerta("resources/puertaAbajo.png", puertaAncho, puertaAbajoX, puertaAbajoY, true);
        puertas.add(puertaAbajo);   

        // Puerta izquierda
        int puertaIzquierdaX = 45;
        int puertaIzquierdaY = (HEIGHT - puertaAncho - 20) / 2;
        Puerta puertaIzquierda = new Puerta("resources/puertaIzquierda.png", puertaAncho, puertaIzquierdaX, puertaIzquierdaY, true);
        puertas.add(puertaIzquierda);

        // Puerta derecha
        int puertaDerechaX = WIDTH - puertaAncho - 25;
        int puertaDerechaY = (HEIGHT - puertaAncho - 20) / 2;
        Puerta puertaDerecha = new Puerta("resources/puertaDerecha.png", puertaAncho, puertaDerechaX, puertaDerechaY, true);
        puertas.add(puertaDerecha);
    }

    public void nuevaSala() {
        eliminarObjetos();
        numeroAleatorio = random.nextInt(4) + 1;
        switch (numeroAleatorio) {
            case 1:
                isaac.setX(puertas.get(0).getX());
                isaac.setY(puertas.get(0).getY() + puertas.get(0).getTamaño());
                break;
            case 2:
                isaac.setX(puertas.get(1).getX());
                isaac.setY(puertas.get(1).getY() - puertas.get(1).getTamaño());
                break;
            case 3:
                isaac.setX(puertas.get(2).getX() + puertas.get(2).getTamaño());
                isaac.setY(puertas.get(2).getY());
                break;
            case 4:
                isaac.setX(puertas.get(3).getX() - puertas.get(3).getTamaño());
                isaac.setY(puertas.get(3).getY());
                break;
        }
        generarFondo("resources/mapa.png");
        generarObjetos();
    }

    public void generarFondo(String ruta) {
        try {
            fondo = ImageIO.read(new File(ruta));
        } catch (IOException e) {
            e.printStackTrace();
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