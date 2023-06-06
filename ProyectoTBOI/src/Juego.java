import java.awt.Color;
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
    public static final int FPS = 60;

    private Random random = new Random();
    private int numeroAleatorio = random.nextInt(4) + 1;
    private Isaac isaac;
    private int anteriorIsaacX;
    private int anteriorIsaacY;
    private boolean nuevoJuego = false;
    
    private BufferedImage fondo;
    private List<Objeto> objetos = new ArrayList<>();
    private List<Enemigo> enemigos = new ArrayList<>();
    private List<Puerta> puertas = new ArrayList<>();
    
    private Objeto corazon = new Objeto("resources/corazon.png", 30);
    
    public Juego() {
        isaac = new Isaac(WIDTH / 2, HEIGHT / 2);
        isaac.lastShootTime = 0;
        
        generarFondo("resources/instrucciones.png");
        generarPuertas();
        Timer timer = new Timer(1000 / FPS, e -> {
            update();
            repaint();  
            
        	//Colision objetos
        	for (Objeto objeto : objetos) {
        		if(isaac.detectarColision(objeto)) {
        			isaac.setX(isaac.getX()-isaac.getVelocityX());
            		isaac.setY(isaac.getY()-isaac.getVelocityY());
        		}
        		if(isaac.colisionLagrima(objeto)) {
            	}
        	}
            //Colicion puertas
        	 for (int i = 0; i < puertas.size(); i++) {
         	  
            	if(isaac.detectarColision(puertas.get(numeroAleatorio-1)) && puertas.get(i).isAbierta()) {
            		puertas.get(i).setAbierta(false);
            		nuevaSala();
            	}
            	if(enemigos.isEmpty()) {
            		puertas.get(i).setAbierta(true);
            	}else {
            		puertas.get(i).setAbierta(false);
            	}
            }
            //Colisiones enemigos
            for (int i = 0; i < enemigos.size(); i++) {
        	  
        	    //Colision isaac con enemigos
            	if(isaac.detectarColision(enemigos.get(i))) {
            		
            	}
            	//Colision lagrimas con enemigos
            	if(isaac.colisionLagrima(enemigos.get(i))) {
            		enemigos.get(i).setVida(enemigos.get(i).getVida()-1);
            		
            		if(enemigos.get(i).getVida() == 0) {
            			enemigos.remove(i);
            		}
            	}
            }
            //Movimiento enemigos
            for (Enemigo enemigo : enemigos) {
            	if(enemigo.getNombre() == "Mosca") {
            		enemigo.mover();            		
            	}
            }
        });
        timer.start();
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
        for (Enemigo enemigo : enemigos) {
            g.drawImage(enemigo.getSprite(), enemigo.getX(), enemigo.getY(), enemigo.getAncho(), enemigo.getAlto(), null);
        }

        isaac.paint(g);

        for (Lagrima lagrima : isaac.lagrimas) {
        	lagrima.paint(g);
        }
        
        //VIDAS
        g.setColor(Color.RED);
        for (int i = 0; i < isaac.getVidas(); i++) {
            int x = 10 + (i * (20 + 5));
            g.drawImage(corazon.getSprite(), x, 10, corazon.getTamaño(), corazon.getTamaño(), null);
        }
    }
    
    //Variables globales para controlar la cantidad de piedras y cacas
    private int cantidadPiedras = 6;
    //private int cantidadPiedrasPicos = 3;
    private int cantidadCacas = 3;

    public void generarObjetos() {
        int areaAncho = 700;
        int areaAlto = 340;
        
        int limiteInferiorX = (Juego.WIDTH - areaAncho) / 2;
        int limiteSuperiorX = limiteInferiorX + areaAncho;
        int limiteInferiorY = (Juego.HEIGHT - areaAlto) / 2;
        int limiteSuperiorY = limiteInferiorY + areaAlto;

        Random random = new Random();

        // Generar piedras
        for (int i = 0; i < cantidadPiedras; i++) {
            int x = random.nextInt(limiteSuperiorX - limiteInferiorX + 1) + limiteInferiorX;
            int y = random.nextInt(limiteSuperiorY - limiteInferiorY + 1) + limiteInferiorY;

            // Verificar colisión con otros objetos generados
            while (verificarColisionConObjetos(x, y, objetos) || verificarColisionConAdyacentes(x, y, objetos)) {
                x = random.nextInt(limiteSuperiorX - limiteInferiorX + 1) + limiteInferiorX;
                y = random.nextInt(limiteSuperiorY - limiteInferiorY + 1) + limiteInferiorY;
            }

            objetos.add(new Objeto("resources/roca.png", 70, x, y));
        }

        // Generar cacas
        for (int i = 0; i < cantidadCacas; i++) {
            int x = random.nextInt(limiteSuperiorX - limiteInferiorX + 1) + limiteInferiorX;
            int y = random.nextInt(limiteSuperiorY - limiteInferiorY + 1) + limiteInferiorY;

            // Verificar colisión con otros objetos generados
            while (verificarColisionConObjetos(x, y, objetos) || verificarColisionConAdyacentes(x, y, objetos)) {
                x = random.nextInt(limiteSuperiorX - limiteInferiorX + 1) + limiteInferiorX;
                y = random.nextInt(limiteSuperiorY - limiteInferiorY + 1) + limiteInferiorY;
            }

            objetos.add(new Objeto("resources/popo.png", 70, x, y));
        }
        
        // Generar rocas con picos
//        for (int i = 0; i < cantidadPiedrasPicos; i++) {
//            int x = random.nextInt(limiteSuperiorX - limiteInferiorX + 1) + limiteInferiorX;
//            int y = random.nextInt(limiteSuperiorY - limiteInferiorY + 1) + limiteInferiorY;
//
//            // Verificar colisión con otros objetos generados
//            while (verificarColisionConObjetos(x, y, objetos) || verificarColisionConAdyacentes(x, y, objetos)) {
//                x = random.nextInt(limiteSuperiorX - limiteInferiorX + 1) + limiteInferiorX;
//                y = random.nextInt(limiteSuperiorY - limiteInferiorY + 1) + limiteInferiorY;
//            }
//
//            objetos.add(new Objeto("resources/rocaPicos.png", 70, x, y));
//        }
    }

    private boolean verificarColisionConAdyacentes(int x, int y, List<Objeto> objetos) {
        int distanciaMinima = 100; // Distancia mínima entre objetos adyacentes

        for (Objeto objeto : objetos) {
            int distanciaX = Math.abs(x - objeto.getX());
            int distanciaY = Math.abs(y - objeto.getY());

            if (distanciaX <= distanciaMinima && distanciaY <= distanciaMinima) {
                return true; // Colisión con objeto adyacente detectada
            }
        }

        return false; // No hay colisión con objetos adyacentes
    }


    private boolean verificarColisionConObjetos(int x, int y, List<Objeto> objetos) {
        for (Objeto objeto : objetos) {
            if (verificarColision(x, y, objeto.getX(), objeto.getY(), objeto.getTamaño(), objeto.getTamaño())) {
                return true; // Colisión detectada
            }
        }
        return false; // No hay colisión
    }

    private boolean verificarColision(int x1, int y1, int x2, int y2, int ancho, int alto) {
        return x1 < x2 + ancho && x1 + 50 > x2 && y1 < y2 + alto && y1 + 50 > y2;
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
        Puerta puertaArriba = new Puerta("resources/puertaArriba.png", puertaAncho, puertaArribaX, puertaArribaY, false);
        puertas.add(puertaArriba);
   
        // Puerta abajo    
        int puertaAbajoX = (WIDTH - puertaAncho) / 2;
        int puertaAbajoY = HEIGHT - puertaAlto + 245;
        Puerta puertaAbajo = new Puerta("resources/puertaAbajo.png", puertaAncho, puertaAbajoX, puertaAbajoY, false);
        puertas.add(puertaAbajo);   

        // Puerta izquierda
        int puertaIzquierdaX = 45;
        int puertaIzquierdaY = (HEIGHT - puertaAncho - 20) / 2;
        Puerta puertaIzquierda = new Puerta("resources/puertaIzquierda.png", puertaAncho, puertaIzquierdaX, puertaIzquierdaY, false);
        puertas.add(puertaIzquierda);

        // Puerta derecha
        int puertaDerechaX = WIDTH - puertaAncho - 25;
        int puertaDerechaY = (HEIGHT - puertaAncho - 20) / 2;
        Puerta puertaDerecha = new Puerta("resources/puertaDerecha.png", puertaAncho, puertaDerechaX, puertaDerechaY, false);
        puertas.add(puertaDerecha);
    }

    public void generarEnemigos() {
        int areaAncho = 700;
        int areaAlto = 340;
        
        int limiteInferiorX = (Juego.WIDTH - areaAncho) / 2;
        int limiteSuperiorX = limiteInferiorX + areaAncho;
        int limiteInferiorY = (Juego.HEIGHT - areaAlto) / 2;
        int limiteSuperiorY = limiteInferiorY + areaAlto;
        
        Random random = new Random();
        
        //Generacion de horf
        int numeroHorf = random.nextInt(2) + 1;
        for (int i = 0; i < numeroHorf; i++) {
            int x = random.nextInt(limiteSuperiorX - limiteInferiorX + 1) + limiteInferiorX;
            int y = random.nextInt(limiteSuperiorY - limiteInferiorY + 1) + limiteInferiorY;

            // Verificar colisión con otros objetos generados
            while (verificarColisionConObjetos(x, y, objetos) || verificarColisionConAdyacentes(x, y, objetos)) {
                x = random.nextInt(limiteSuperiorX - limiteInferiorX + 1) + limiteInferiorX;
                y = random.nextInt(limiteSuperiorY - limiteInferiorY + 1) + limiteInferiorY;
            }

            enemigos.add(new Enemigo("resources/Horf.png", "Horf", 55, 55, x, y, 2, 4));
        }
        //Generacion de moscas
        int numeroMosca = random.nextInt(4) + 1;
        for (int i = 0; i < numeroMosca; i++) {
            int x = random.nextInt(limiteSuperiorX - limiteInferiorX + 1) + limiteInferiorX;
            int y = random.nextInt(limiteSuperiorY - limiteInferiorY + 1) + limiteInferiorY;

            // Verificar colisión con otros objetos generados
            while (verificarColisionConObjetos(x, y, objetos) || verificarColisionConAdyacentes(x, y, objetos)) {
                x = random.nextInt(limiteSuperiorX - limiteInferiorX + 1) + limiteInferiorX;
                y = random.nextInt(limiteSuperiorY - limiteInferiorY + 1) + limiteInferiorY;
            }

            enemigos.add(new Enemigo("resources/mosca.png", "Mosca", 34, 26, x, y, 6, 1));
        }
    }
    
    public void eliminarEnemigos(){
    	enemigos.clear();
    }
    
    int nuevoAleatorio;
    public void nuevaSala() {
        eliminarObjetos();
        eliminarEnemigos();
        do {
        	nuevoAleatorio = random.nextInt(4) + 1;
        	
        } while(nuevoAleatorio == numeroAleatorio);
        
        numeroAleatorio = nuevoAleatorio;
    	switch (numeroAleatorio) {
    	case 1:  
    		isaac.setX(puertas.get(0).getX() + puertas.get(0).getTamaño() / 2);
    		isaac.setY(puertas.get(0).getY() + puertas.get(0).getTamaño());
    		break;
    	case 2:
    		isaac.setX(puertas.get(1).getX() + puertas.get(1).getTamaño() / 2);
    		isaac.setY(puertas.get(1).getY() - puertas.get(1).getTamaño() / 2);
    		break;
    	case 3:
    		isaac.setX(puertas.get(2).getX() + puertas.get(2).getTamaño());
    		isaac.setY(puertas.get(2).getY() + puertas.get(2).getTamaño() / 2);
    		break;
    	case 4:
    		isaac.setX(puertas.get(3).getX() - (puertas.get(3).getTamaño() / 2) + 30);
    		isaac.setY(puertas.get(3).getY() + puertas.get(3).getTamaño() / 2);
    		break;
    	}
        generarFondo("resources/mapa.png");
        generarObjetos();
        generarEnemigos();
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

        if (isaac.getX() < 130) {
            isaac.setVelocityX(0);
            isaac.setX(130);
        }
        if (isaac.getX() > WIDTH - 110) {
            isaac.setVelocityX(0);
            isaac.setX(WIDTH - 110);
        }
        if (isaac.getY() < 120) {
            isaac.setVelocityY(0);
            isaac.setY(120);
        }
        if (isaac.getY() > HEIGHT - 135) {
            isaac.setVelocityY(0);
            isaac.setY(HEIGHT - 135);
        }

        isaac.lagrimas.removeIf(lagrima -> lagrima.getX() < 85 || lagrima.getX() > WIDTH - 110 || lagrima.getY() < 85 || lagrima.getY() > HEIGHT - 110);
    }
	
	public void nuevoJuego() {
		isaac.setX(WIDTH / 2);
		isaac.setY(HEIGHT / 2);
		isaac.setVidas(6);
		eliminarObjetos();
	    eliminarEnemigos();
		generarFondo("resources/instrucciones.png"); 
        generarPuertas();
        nuevoJuego = true;
	}
	
	public Isaac getIsaac() {
		return isaac;
	}

	public int getAnteriorIsaacX() {
		return anteriorIsaacX;
	}

	public void setAnteriorIsaacX(int anteriorIsaacX) {
		this.anteriorIsaacX = anteriorIsaacX;
	}

	public int getAnteriorIsaacY() {
		return anteriorIsaacY;
	}

	public void setAnteriorIsaacY(int anteriorIsaacY) {
		this.anteriorIsaacY = anteriorIsaacY;
	}

	public boolean isNuevoJuego() {
		return nuevoJuego;
	}

	public void setNuevoJuego(boolean nuevoJuego) {
		this.nuevoJuego = nuevoJuego;
	}
	
	
}