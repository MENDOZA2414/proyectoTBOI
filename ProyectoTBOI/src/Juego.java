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
	private int tiempoImunidad = 2; //Tiempo que pasa antes de recibir otro fregazo
    private int velocidad = 6; //Velocidad del isaac
    private boolean puedeMoverse = true; //Determina si isaac puede o no moverse
    private boolean dispara = true; //Determina si isaac puede o no disparar
    private int tamañoLagrimas = 30; //Tamaño de las lagrimas
    private int velocidaLagrimas = 5; //Velocidad a la que salen las lagrimas
    private float rangoLagrimas = 0.3f; //Rango de las lagrimas
    private int frecuenciaLagrimas = 450; //Que tan frecuente dispara iaaac, (1000 = 1 segundo)
    private int vida = 6; //Vida del isaac

    //Variables Horf
	private int tiempoImunidadH = 0;
    private int velocidadH = 6;
    private boolean puedeMoverseH = false;
    private boolean disparaH = true;
    private int tamañoLagrimasH = 30;
    private int velocidaLagrimasH = 5;
    private float rangoLagrimasH = 0.6f;
    private int frecuenciaLagrimasH = 1500;
    private int vidaH = 4;
    private float probabilidadItemH = 0.15f; //Probabilidad de dropeo de item

    //Variables mosca
	private int tiempoImunidadM = 0;
    private int velocidadM = 6;
    private boolean puedeMoverseM = true; 
    private boolean disparaM = false;
    private int tamañoLagrimasM = 0;
    private int velocidaLagrimasM = 0;
    private float rangoLagrimasM = 0;
    private int frecuenciaLagrimasM = 0;
    private int vidaM = 1;
    private float probabilidadItemM = 0f; //Probabilidad de dropeo de item

    //Variables skinny
	private int tiempoImunidadS = 0;
    private int velocidadS = 3;
    private boolean puedeMoverseS = true; 
    private boolean disparaS = false;
    private int tamañoLagrimasS = 0;
    private int velocidaLagrimasS = 0;
    private float rangoLagrimasS = 0;
    private int frecuenciaLagrimasS = 0;
    private int vidaS = 6;
    private float probabilidadItemS = 0.25f; //Probabilidad de dropeo de item

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
    private List<Objeto> corazones = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
 
    //Animacion isaac
  	private String[] quieto = {"resources/skinny.png","resources/skinny.png","resources/skinny.png"};
  	private String[] animacion = quieto;
  	private String[] arriba = {"resources/skinnyArr1.png","resources/skinnyArr2.png","resources/skinnyArr3.png"};
  	private String[] abajo = {"resources/skinnyAbj1.png","resources/skinnyAbj2.png","resources/skinnyAbj3.png"};
  	private String[] izquierda = {"resources/skinnyIzq1.png","resources/skinnyIzq2.png","resources/skinnyIzq3.png"};
  	private String[] derecha = {"resources/skinnyDer1.png","resources/skinnyDer2.png","resources/skinnyDer3.png"};

    private Colision colision = new Colision();
    
    public Juego() {
        isaac = new Jugador("resources/isaac.png", "resources/normaltear.png", "isaac", 63, 80, velocidad, puedeMoverse, dispara, tamañoLagrimas, velocidaLagrimas, rangoLagrimas, frecuenciaLagrimas, vida, tiempoImunidad, WIDTH / 2, HEIGHT / 2);
        for(int i = 0; i< isaac.getLife(); i++) {
            corazones.add(new Objeto("resources/corazon.png", "Corazon", 30, 30, true, 0, 0, 0));
        }
        generarFondo("resources/instrucciones.png");
        generarPuertas();
        Timer timer = new Timer(1000 / FPS, e -> {
            update();
            repaint();  
            
            colisionesObjetos();
            colisionesEnemigos();
            colisionItems();
            
          //Movimiento enemigos
            for (Enemigo enemigo : enemigos) {
            	if(enemigo.getNombre() == "Skinny") {
            		int posXAnterior = enemigo.getX();
                    int posYAnterior = enemigo.getY();

                    enemigo.moverHaciaIsaacConEvasion( isaac.getX(), isaac.getY(), objetos);
                    // Determinar dirección horizontal
                    int deltaX = enemigo.getX() - posXAnterior;
                    if (deltaX > 0) {
                        animacion = derecha;
                    } else if (deltaX < 0) {
                        animacion = izquierda;
                    }

                    // Determinar dirección vertical
                    int deltaY = enemigo.getY() - posYAnterior;
                    if (deltaY > 0) {
                        animacion = abajo;
                    } else if (deltaY < 0) {
                        animacion = arriba;
                    }
            	}
            	else {
                	enemigo.mover();
            	}
            }
        });
        timer.start();
        
        Timer disparosEnemigo = new Timer(1000, e -> {
	        for (Enemigo enemigo : enemigos) {
	        	enemigo.disparar(isaac.getX(), isaac.getY());
        	}
        });
        disparosEnemigo.start();
        
        final int[] i = {0}; // Declarar la variable i como un array final

        Timer movimientoEnemigo = new Timer(250, e -> {
            for (Enemigo enemigo : enemigos) {
                if (enemigo.getNombre().equals("Skinny")) {
                    
                    // Actualizar la animación del enemigo
                    enemigo.setSprite(animacion[i[0]]);
                    i[0]++;
                    if (i[0] >= animacion.length) {
                        i[0] = 0;
                    }
                }
            }
        });
        movimientoEnemigo.start();
    }
    
    //POWER UPS MODIFICABLES
    public void colisionItems() {
    	for (int i = 0; i < items.size(); i++) {
    		Item item = items.get(i);
    		if(colision.detectar(isaac, item)) {
    			if(item.getNombre().equals("Cebolla")) {
    				isaac.setShootDelay(isaac.getShootDelay()-5);
    				items.remove(i);
    			}
    			if(item.getNombre().equals("Jeringa")) {
    				isaac.setTearRange(isaac.getTearRange()+0.5f);
    				items.remove(i);
    			}
    			if(item.getNombre().equals("CorazonHorf")) {
    				isaac.setLife(isaac.getLife()+1);
    				items.remove(i);
    			}
    		}
    	}
    }
    public void colisionesObjetos() {
    	//Colision isaac con objetoss
    	for (int i = 0; i < objetos.size(); i++) {
    		Objeto objeto = objetos.get(i);
    		
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
        			if(!isaac.isInvencible()){
	        			if (colision.detectar(isaac, enemigo.getLagrimas().get(j))) {
	        				enemigo.getLagrimas().remove(j);
	        				isaac.recibeDaño();
	                		isaac.activarInmunidad(tiempoImunidad);
	        				j--;
	        				break;
	        			}
        			}
        		}
    	    }
            //Colicion lagrimas con objetos
           	for (int k = 0; k < isaac.getLagrimas().size(); k++) {
            	Lagrima lagrima = isaac.getLagrimas().get(k);
            	
               	if(colision.detectar(lagrima, objeto)) {
                    isaac.getLagrimas().remove(k);
                    
                    if(objeto.isDestruible()) {
                    	objeto.setDureza(objeto.getDureza()-1);
                    	if(objeto.getDureza() == 0) {
                    		objetos.remove(i);
                    	}
                    }
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
    	    	if(enemigos.get(i).generarItem()) {
    	    		items.add(enemigos.get(i).getItem());    	    		
    	    	}
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
        for (Item item : items) {
            g.drawImage(item.getSprite(), item.getX(), item.getY(), item.getAncho(), item.getAlto(), null);
        }
        //VIDAS
        for (int i = 0; i < isaac.getLife(); i++) {
        	Objeto corazon = corazones.get(i);
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

            objetos.add(new Objeto("resources/roca.png", "Roca", 70, 70, false, 0, x, y));
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

            objetos.add(new Objeto("resources/popo.png", "Popo", 70, 70, true, 3, x, y));
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
    
    private void eliminarItems() {
    	items.clear();
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
        Puerta puertaArriba = new Puerta("resources/puertaCerradaArriba.png", "Arriba", puertaAncho, puertaAncho, false, 0, puertaArribaX, puertaArribaY, false);
        puertas.add(puertaArriba);
   
        // Puerta abajo    
        int puertaAbajoX = (WIDTH - puertaAncho) / 2;
        int puertaAbajoY = HEIGHT - puertaAlto + 245;
        Puerta puertaAbajo = new Puerta("resources/puertaCerradaAbajo.png", "Abajo", puertaAncho, puertaAncho, false, 0, puertaAbajoX, puertaAbajoY, false);
        puertas.add(puertaAbajo);   

        // Puerta izquierda
        int puertaIzquierdaX = 45;
        int puertaIzquierdaY = (HEIGHT - puertaAncho - 20) / 2;
        Puerta puertaIzquierda = new Puerta("resources/puertaCerradaIzquierda.png", "Izquierda", puertaAncho, puertaAncho, false, 0, puertaIzquierdaX, puertaIzquierdaY, false);
        puertas.add(puertaIzquierda);

        // Puerta derecha
        int puertaDerechaX = WIDTH - puertaAncho - 25;
        int puertaDerechaY = (HEIGHT - puertaAncho - 20) / 2;
        Puerta puertaDerecha = new Puerta("resources/puertaCerradaDerecha.png", "Derecha", puertaAncho, puertaAncho, false, 0, puertaDerechaX, puertaDerechaY, false);
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
            Item HorfItem = new Item("resources/CorazonHorf.png", "CorazonHorf", 35, 33, 0, 0);
            enemigos.add(new Enemigo("resources/Horf.png", "resources/redtear.png", "Horf", 55, 55, velocidadH, puedeMoverseH, disparaH, tamañoLagrimasH, velocidaLagrimasH, rangoLagrimasH, frecuenciaLagrimasH, vidaH, tiempoImunidadH, HorfItem, probabilidadItemH, x, y));
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
            enemigos.add(new Enemigo("resources/mosca.png", "resources/redtear.png", "Mosca", 34, 26, velocidadM, puedeMoverseM, disparaM, tamañoLagrimasM, velocidaLagrimasM, rangoLagrimasM, frecuenciaLagrimasM, vidaM, tiempoImunidadM, null, probabilidadItemM, x, y));
        }
      //Generacion de Skinny
        int numeroSkinny = random.nextInt(2); // Genera 0 o 1

        for (int i = 0; i < numeroSkinny; i++) {
            int x = random.nextInt(limiteSuperiorX - limiteInferiorX + 1) + limiteInferiorX;
            int y = random.nextInt(limiteSuperiorY - limiteInferiorY + 1) + limiteInferiorY;

            // Verificar colisión con otros objetos generados
            while (verificarColisionConObjetos(x, y, objetos) || verificarColisionConAdyacentes(x, y, objetos)) {
                x = random.nextInt(limiteSuperiorX - limiteInferiorX + 1) + limiteInferiorX;
                y = random.nextInt(limiteSuperiorY - limiteInferiorY + 1) + limiteInferiorY;
            }
            Item SkinnyItem = new Item("resources/CebollaTriste.png", "Cebolla", 29, 51, 0, 0);
            enemigos.add(new Enemigo("resources/skinny.png", "resources/redtear.png", "Skinny", 63, 80, velocidadS, puedeMoverseS, disparaS, tamañoLagrimasS, velocidaLagrimasS, rangoLagrimasS, frecuenciaLagrimasS, vidaS, tiempoImunidadS, SkinnyItem, probabilidadItemS, x, y));
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
	    eliminarItems();
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