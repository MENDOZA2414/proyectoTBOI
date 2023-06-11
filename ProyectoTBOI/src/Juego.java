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

	//Variables generales juego (DATO: no son para power ups, son de default)
	private int tiempoImunidad = 1; //Tiempo que pasa antes de recibir otro fregazo
    private int velocidad = 8; //Velocidad del isaac
    private boolean puedeMoverse = true; //Determina si isaac puede o no moverse
    private boolean dispara = true; //Determina si isaac puede o no disparar
    private int tamañoLagrimas = 30; //Tamaño de las lagrimas
    private int velocidaLagrimas = 5; //Velocidad a la que salen las lagrimas
    private int frecuenciaLagrimas = 450; //Que tan frecuente dispara iaaac, (1000 = 1 segundo)
    private int vida = 6; //Vida del isaac
    
    //Variables Horf
	private int tiempoImunidadH = 0;
    private int velocidadH = 8;
    private boolean puedeMoverseH = false;
    private boolean disparaH = true;
    private int tamañoLagrimasH = 30;
    private int velocidaLagrimasH = 5;
    private int frecuenciaLagrimasH = 450;
    private int vidaH = 4;
    
    //Variables mosca
	private int tiempoImunidadM = 0;
    private int velocidadM = 6;
    private boolean puedeMoverseM = true; 
    private boolean disparaM = false;
    private int tamañoLagrimasM = 0;
    private int velocidaLagrimasM = 0;
    private int frecuenciaLagrimasM = 0;
    private int vidaM = 1;
    //---------------------------------------------------------------------------------------------
    
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 690;
    public static final int FPS = 60;

    private Random random = new Random();
    private int numeroAleatorio = random.nextInt(4) + 1;
    
    private Jugador isaac;
    private int anteriorIsaacX;
    private int anteriorIsaacY;
    private boolean nuevoJuego = false;
    
    private BufferedImage fondo;
    private List<Objeto> objetos = new ArrayList<>();
    private List<Enemigo> enemigos = new ArrayList<>();
    private List<Puerta> puertas = new ArrayList<>();
    
    private Objeto corazon = new Objeto("resources/corazon.png", 30, 30, 0, 0);
    private Colision colision = new Colision();
    
    public Juego() {
        isaac = new Jugador("resources/isaacola.png", "resources/normaltear.png", "isaac", 72, 72, velocidad, puedeMoverse, dispara, tamañoLagrimas, velocidaLagrimas, frecuenciaLagrimas, vida, tiempoImunidad, WIDTH / 2, HEIGHT / 2);
        
        generarFondo("resources/instrucciones.png");
        generarPuertas();
        Timer timer = new Timer(1000 / FPS, e -> {
            update();
            repaint();  
            
            colisionesObjetos();
            colisionesEnemigos();
            
            //Movimiento enemigos
            for (Enemigo enemigo : enemigos) {
            	enemigo.mover();
            }
        });
        timer.start();
        
        Timer disparosEnemigo = new Timer(1000, e -> {
	        for (Enemigo enemigo : enemigos) {
	        	enemigo.disparar(isaac.getX(), isaac.getY());
        	}
        });
        disparosEnemigo.start();
    }
    
    public void colisionesObjetos() {
    	//Colision isaac con objetoss
    	for (Objeto objeto : objetos) {
    		if(colision.detectar(isaac, objeto)) {
    			isaac.setX(isaac.getX()-isaac.getVelocityX());
        		isaac.setY(isaac.getY()-isaac.getVelocityY());
    		}
    		//Colision lagrimas enemigas con objetos
    		for (Enemigo enemigo : enemigos) {
        		for (int j = 0; j < enemigo.getLagrimas().size(); j++) {
        			
        			if (colision.detectar(enemigo.getLagrimas().get(j), objeto)) {
        				enemigo.getLagrimas().remove(j);
        				j--;
        				break;
        			}
        			if (colision.detectar(isaac, enemigo.getLagrimas().get(j))) {
        				enemigo.getLagrimas().remove(j);
        				isaac.recibeDaño();
                		isaac.activarInmunidad(tiempoImunidad);
        				j--;
        				break;
        			}
        		}
    	    }
            //Colicion lagrimas con objetos
           	for (int i = 0; i < isaac.getLagrimas().size(); i++) {
            	Lagrima lagrima = isaac.getLagrimas().get(i);
            	
               	if(colision.detectar(lagrima, objeto)) {
                    isaac.getLagrimas().remove(i);
               	}
           	}
    	}
        //Colicion isaac con puertas
    	for (int i = 0; i < puertas.size(); i++) {
     	
        	if(colision.detectar(isaac, puertas.get(numeroAleatorio-1)) && puertas.get(i).isAbierta()) {
        		puertas.get(i).setAbierta(false);
        		nuevaSala();
        	}
        	if(enemigos.isEmpty()) {
        		puertas.get(i).setAbierta(true);
        	}else {
        		puertas.get(i).setAbierta(false);
        	}
        }
    }
    
    public void colisionesEnemigos() {
    	//Colisiones de enemigos
        for (int i = 0; i < enemigos.size(); i++) {
        	if(!isaac.isInvencible()){

        	    //Colision isaac con enemigos
            	if(colision.detectar(isaac, enemigos.get(i))) {
            		isaac.recibeDaño();
            		isaac.activarInmunidad(tiempoImunidad);
            	}
        	}
        }
        //Colision lagrimas con enemigos
    	for (int j = 0; j < isaac.getLagrimas().size(); j++) {
    	    Lagrima lagrima = isaac.getLagrimas().get(j);
    	    for (int i = 0; i < enemigos.size(); i++) {
    	        if (colision.detectar(lagrima, enemigos.get(i))) {
    	            enemigos.get(i).recibeDaño();
            		enemigos.get(i).activarInmunidad(enemigos.get(i).getImmunityTime());
    	            isaac.getLagrimas().remove(j);
        	        j--;
    	            break;
    	        }
    	    }
    	}
    	//Remover enemigos con vida igual a cero
    	for (int i = 0; i < enemigos.size(); i++) {
    	    if (enemigos.get(i).getLife() <= 0) {
    	        enemigos.remove(i);
    	        i--;
    	    }
    	}
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //Fondo agregado

        g.drawImage(fondo, 0, 0, WIDTH, HEIGHT, null);

        for (Objeto objeto : objetos) {
            g.drawImage(objeto.getSprite(), objeto.getX(), objeto.getY(), objeto.getAncho(), objeto.getAlto(), null);
        }
        
        switch (numeroAleatorio) {
        case 1:
            if(enemigos.isEmpty()) {
                puertas.get(0).setSpriteAbierto(0);
            }
            else{
                puertas.get(0).setSpriteCerrado();
            }
            g.drawImage(puertas.get(0).getSprite(), puertas.get(0).getX(), puertas.get(0).getY(), puertas.get(0).getAncho() + 20, puertas.get(0).getAlto() - 20, null);
            break;

        case 2:    
            if(enemigos.isEmpty()) {
                puertas.get(1).setSpriteAbierto(1);
            }
            else{
                puertas.get(1).setSpriteCerrado();
            }
            g.drawImage(puertas.get(1).getSprite(), puertas.get(1).getX(), puertas.get(1).getY(), puertas.get(1).getAncho() + 20, puertas.get(1).getAlto() - 25, null);
            break;

        case 3:
            if(enemigos.isEmpty()) {
                puertas.get(2).setSpriteAbierto(2);
            }
            else{
                puertas.get(2).setSpriteCerrado();
            }
            g.drawImage(puertas.get(2).getSprite(), puertas.get(2).getX(), puertas.get(2).getY(), puertas.get(2).getAncho() - 20, puertas.get(2).getAlto() + 20, null);
            break;

        case 4:
            if(enemigos.isEmpty()) {
                puertas.get(3).setSpriteAbierto(3);
            }
            else{
                puertas.get(3).setSpriteCerrado();
            }
            g.drawImage(puertas.get(3).getSprite(), puertas.get(3).getX(), puertas.get(3).getY(), puertas.get(3).getAncho() - 20, puertas.get(3).getAlto() + 20, null);
            break;
        }
        
        isaac.paint(g);
        
        for (Enemigo enemigo : enemigos) {
            enemigo.paint(g);
        }
        
        //VIDAS
        g.setColor(Color.RED);
        for (int i = 0; i < isaac.getLife(); i++) {
            int x = 10 + (i * (20 + 5));
            g.drawImage(corazon.getSprite(), x, 10, corazon.getAncho(), corazon.getAlto(), null);
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

            objetos.add(new Objeto("resources/roca.png", 70, 70, x, y));
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

            objetos.add(new Objeto("resources/popo.png", 70, 70, x, y));
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
            if (verificarColision(x, y, objeto.getX(), objeto.getY(), objeto.getAncho(), objeto.getAlto())) {
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
        Puerta puertaArriba = new Puerta("resources/puertaCerradaArriba.png", puertaAncho, puertaAncho, puertaArribaX, puertaArribaY, false);
        puertas.add(puertaArriba);
   
        // Puerta abajo    
        int puertaAbajoX = (WIDTH - puertaAncho) / 2;
        int puertaAbajoY = HEIGHT - puertaAlto + 245;
        Puerta puertaAbajo = new Puerta("resources/puertaCerradaAbajo.png", puertaAncho, puertaAncho, puertaAbajoX, puertaAbajoY, false);
        puertas.add(puertaAbajo);   

        // Puerta izquierda
        int puertaIzquierdaX = 45;
        int puertaIzquierdaY = (HEIGHT - puertaAncho - 20) / 2;
        Puerta puertaIzquierda = new Puerta("resources/puertaCerradaIzquierda.png", puertaAncho, puertaAncho, puertaIzquierdaX, puertaIzquierdaY, false);
        puertas.add(puertaIzquierda);

        // Puerta derecha
        int puertaDerechaX = WIDTH - puertaAncho - 25;
        int puertaDerechaY = (HEIGHT - puertaAncho - 20) / 2;
        Puerta puertaDerecha = new Puerta("resources/puertaCerradaDerecha.png", puertaAncho, puertaAncho, puertaDerechaX, puertaDerechaY, false);
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

            enemigos.add(new Enemigo("resources/Horf.png", "resources/normaltear.png", "Horf", 55, 55, velocidadH, puedeMoverseH, disparaH, tamañoLagrimasH, velocidaLagrimasH, frecuenciaLagrimasH, vidaH, tiempoImunidadH, x, y));
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
            enemigos.add(new Enemigo("resources/mosca.png", "resources/normaltear.png", "Mosca", 34, 26, velocidadM, puedeMoverseM, disparaM, tamañoLagrimasM, velocidaLagrimasM, frecuenciaLagrimasM, vidaM, tiempoImunidadM, x, y));
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
    		isaac.setX(puertas.get(0).getX() + puertas.get(0).getAncho() / 2);
    		isaac.setY(puertas.get(0).getY() + puertas.get(0).getAlto());
    		break;
    	case 2:
    		isaac.setX(puertas.get(1).getX() + puertas.get(1).getAncho() / 2);
    		isaac.setY(puertas.get(1).getY() - puertas.get(1).getAlto() / 2);
    		break;
    	case 3:
    		isaac.setX(puertas.get(2).getX() + puertas.get(2).getAncho());
    		isaac.setY(puertas.get(2).getY() + puertas.get(2).getAlto() / 2);
    		break;
    	case 4:
    		isaac.setX(puertas.get(3).getX() - (puertas.get(3).getAncho() / 2) + 30);
    		isaac.setY(puertas.get(3).getY() + puertas.get(3).getAlto() / 2);
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

        for (Lagrima lagrima : isaac.getLagrimas()) {
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

        isaac.getLagrimas().removeIf(lagrima -> lagrima.getX() < 85 || lagrima.getX() > WIDTH - 110 || lagrima.getY() < 85 || lagrima.getY() > HEIGHT - 110);
    }
	
	public void nuevoJuego() {
		isaac.setX(WIDTH / 2);
		isaac.setY(HEIGHT / 2);
		isaac.setLife(6);
		eliminarObjetos();
	    eliminarEnemigos();
		generarFondo("resources/instrucciones.png"); 
        nuevoJuego = true;
	}
	
	public Jugador getIsaac() {
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