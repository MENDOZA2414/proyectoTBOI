import java.awt.Color;
import java.awt.Font;
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
    private int score=0;
	private int tiempoImunidad = 2; //Tiempo que pasa antes de recibir otro fregazo
    private int velocidad = 6; //Velocidad del isaac
    private boolean puedeMoverse = true; //Determina si isaac puede o no moverse
    private boolean dispara = true; //Determina si isaac puede o no disparar
    private int daño = 1; //Daño del isaac
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
    private int dañoH = 1;
    private int tamañoLagrimasH = 30;
    private int velocidaLagrimasH = 5;
    private float rangoLagrimasH = 0.6f;
    private int frecuenciaLagrimasH = 1500;
    private int vidaH = 4;
    private float probabilidadMonedaH = 0.6f; //Probabilidad de dropeo de item

    //Variables mosca
	private int tiempoImunidadM = 0;
    private int velocidadM = 6;
    private boolean puedeMoverseM = true; 
    private boolean disparaM = false;
    private int dañoM = 1;
    private int tamañoLagrimasM = 0;
    private int velocidaLagrimasM = 0;
    private float rangoLagrimasM = 0;
    private int frecuenciaLagrimasM = 0;
    private int vidaM = 1;
    private float probabilidadMonedaM = 0.15f; //Probabilidad de dropeo de item

    //Variables skinny
	private int tiempoImunidadS = 0;
    private int velocidadS = 3;
    private boolean puedeMoverseS = true; 
    private boolean disparaS = false;
    private int dañoS = 1;
    private int tamañoLagrimasS = 0;
    private int velocidaLagrimasS = 0;
    private float rangoLagrimasS = 0;
    private int frecuenciaLagrimasS = 0;
    private int vidaS = 6;
    private float probabilidadMonedaS = 0.40f; //Probabilidad de dropeo de item

    private float probabilidadTienda = 0.1f;
    private float probabilidadItemVida = 0.25f;

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
    private int cintosRecogidos = 0;
    private int coronasRecogidas = 0;
    
    private BufferedImage fondo;
    private List<Objeto> objetos = new ArrayList<>();
    private List<Enemigo> enemigos = new ArrayList<>();
    private List<Puerta> puertas = new ArrayList<>();
    private List<Objeto> corazones = new ArrayList<>();
    private List<Item> powerUps = new ArrayList<>();
    private List<Item> itemsGenerados = new ArrayList<>();
    private List<Item> monedas = new ArrayList<>();

    //Animacion isaac
  	private String[] quieto = {"resources/skinny.png","resources/skinny.png","resources/skinny.png"};
  	private String[] animacion = quieto;
  	private String[] arriba = {"resources/skinnyArr1.png","resources/skinnyArr2.png","resources/skinnyArr3.png"};
  	private String[] abajo = {"resources/skinnyAbj1.png","resources/skinnyAbj2.png","resources/skinnyAbj3.png"};
  	private String[] izquierda = {"resources/skinnyIzq1.png","resources/skinnyIzq2.png","resources/skinnyIzq3.png"};
  	private String[] derecha = {"resources/skinnyDer1.png","resources/skinnyDer2.png","resources/skinnyDer3.png"};

    private Colision colision = new Colision();

    Font font ;
    public Juego() {
        font = new Font("Pixel", Font.PLAIN, 24);
        isaac = new Jugador("resources/isaac.png", "resources/normaltear.png", "isaac", 63, 80, velocidad, puedeMoverse, dispara, daño, tamañoLagrimas, velocidaLagrimas, rangoLagrimas, frecuenciaLagrimas, vida, tiempoImunidad, WIDTH / 2, HEIGHT / 2);
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
        
        Timer sonidoHorf = new Timer(3000, e -> {
            boolean horfPresente = false;
            for (Enemigo enemigo : enemigos) {
                if (enemigo.getNombre().equals("Horf")) {
                    horfPresente = true;
                    break;
                }
            }
            if (horfPresente) {
                new Sonido("horf", 0.1f);
            }
        });
        sonidoHorf.start();

        Timer sonidoMosca = new Timer(1500, e -> {
            boolean moscaPresente = false;
            for (Enemigo enemigo : enemigos) {
                if (enemigo.getNombre().equals("Mosca")) {
                    moscaPresente = true;
                    break;
                }
            }
            if (moscaPresente) {
                new Sonido("fly", 0.2f);
            }
        });
        sonidoMosca.start();

        
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
	        				sonidoDañoIsaac();
	        				isaac.recibeDaño(enemigo.getDamage());
	                		isaac.activarInmunidad(tiempoImunidad);
	        				j--;
	        				break;
	        			}
        			}
        		}
    	    }
    		List<Item> recogibles = new ArrayList<>();
    		// Colision lagrimas con objetos
    		for (int k = 0; k < isaac.getLagrimas().size(); k++) {
    		    Lagrima lagrima = isaac.getLagrimas().get(k);
    			
    		    if (colision.detectar(lagrima, objeto)) {
    		        isaac.getLagrimas().remove(k);
    		        if (objeto.isDestruible()) {
    		            objeto.setDureza(objeto.getDureza() - 1);
    		            if (objeto.getDureza() == 0) {
    		            	
    		            	float randomNum = new Random().nextFloat();
    		                if (randomNum <= probabilidadItemVida){
    		                	int objetoAncho = objeto.getAncho();
    		                	int objetoAlto = objeto.getAlto();
    		                	
    		                	switch (new Random().nextInt(2) + 1) {
    		                    case 1:
    		                    	Item corazonRojo = new Item("resources/Red_Heart.png", "Corazon Rojo", 36, 29, 0, 0);
        		                	int itemRojoX = objeto.getX() + (objetoAncho - corazonRojo.getAncho()) / 2;
        		                	int itemRojoY = objeto.getY() + (objetoAlto - corazonRojo.getAlto()) / 2;
        		                	corazonRojo.setX(itemRojoX);
        		                	corazonRojo.setY(itemRojoY);    

        		                	recogibles.add(corazonRojo); 		                	
        		                	break;
    		                	
	    		                case 2:
			                    	Item corazonAzul = new Item("resources/corazonAzul.png", "Corazon Azul", 36, 29, 0, 0);
	    		                	int itemAzulX = objeto.getX() + (objetoAncho - corazonAzul.getAncho()) / 2;
	    		                	int itemAzulY = objeto.getY() + (objetoAlto - corazonAzul.getAlto()) / 2;
	    		                	corazonAzul.setX(itemAzulX);
	    		                	corazonAzul.setY(itemAzulY);    
	
	    		                	recogibles.add(corazonAzul); 		                	
	    		                	break;
			                	}
    		                }
    		                objetos.remove(i);
    		                score += 1;
    		                break;  // Salir del bucle una vez que se ha generado el item
    		            }
    		        }
    		    }
    		}
    		// Agregar los items generados a la lista itemsGenerados
    		itemsGenerados.addAll(recogibles);
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
    
    public void sonidoDañoIsaac() {
    	int numeroAleatorio = random.nextInt(3) + 1;
		
		switch(numeroAleatorio) {
			case 1:
				new Sonido("dañoIsaac1",0.2f);
			break;
			
			case 2:
				new Sonido("dañoIsaac2",0.2f);
			break;
			
			case 3:
				new Sonido("dañoIsaac3",0.2f);
			break;
		}
    }
    
    public void colisionesEnemigos() {
    	//Colisiones de enemigos
        for (int i = 0; i < enemigos.size(); i++) {
        	if(!isaac.isInvencible()){

        	    //Colision isaac con enemigos
            	if(colision.detectar(isaac, enemigos.get(i))) {
            		isaac.recibeDaño(isaac.getDamage());
            		sonidoDañoIsaac();
            		isaac.activarInmunidad(tiempoImunidad);
            	}
        	}
        }
        //Colision lagrimas con enemigos
    	for (int j = 0; j < isaac.getLagrimas().size(); j++) {
    	    Lagrima lagrima = isaac.getLagrimas().get(j);
    	    for (int i = 0; i < enemigos.size(); i++) {
    	        if (colision.detectar(lagrima, enemigos.get(i))) {
    	            enemigos.get(i).recibeDaño(isaac.getDamage());
            		enemigos.get(i).activarInmunidad(enemigos.get(i).getImmunityTime());
    	            isaac.getLagrimas().remove(j);
                    score+=1;
        	        j--;
    	            break;
    	        }
    	    }
    	}
    	//Remover enemigos con vida igual a cero
    	for (int i = 0; i < enemigos.size(); i++) {
    	    if (enemigos.get(i).getLife() <= 0) {
	    		//Generar moneda
    	    	if(enemigos.get(i).generarMoneda()) {
        	    	monedas.add(enemigos.get(i).getMoneda());  
    	    	}
    	    	enemigos.remove(i);
                score+=10;
    	    }
    	}
    }
    
    public void colisionItems() {
		for (int i = 0; i < monedas.size(); i++) {
	        Item moneda = monedas.get(i);
	        if(colision.detectar(isaac, moneda)) {
	        	new Sonido("monedaSound",0.2f);
	        	isaac.setMonedasRecolectadas(isaac.getMonedasRecolectadas()+1);
	            monedas.remove(i);
                score+=2;
	        }
	    }
		for (int i = 0; i < itemsGenerados.size(); i++) {
		    Item item = itemsGenerados.get(i);
		    if (colision.detectar(isaac, item)) {
		        new Sonido("powerUp", 0.1f);
		        int contadorCorazonesRojos = 0;
		        for (Objeto corazon : corazones) {
		            if (corazon.getNombre().equals("Corazon Rojo")) {
		                contadorCorazonesRojos++;
		            }
		        }  

		        if (item.getNombre().equals("Corazon Rojo") && contadorCorazonesRojos < 6 && isaac.getLife() < 6) {
		            isaac.setLife(isaac.getLife() + 1);
		            corazones.add(new Objeto("resources/corazon.png", "Corazon Rojo", 30, 30, true, 0, 0, 0));
		        }
		        else if (item.getNombre().equals("Corazon Azul")) {
		            isaac.setLife(isaac.getLife() + 1);
		            corazones.add(new Objeto("resources/viditaAzul.png", "Corazon Azul", 30, 30, true, 0, 0, 0));
		        }
	            itemsGenerados.remove(i);
		    }
		}

		//POWER UPS
        for (int i = 0; i < powerUps.size(); i++) {
            Item item = powerUps.get(i);
            if(colision.detectar(isaac, item)) {
            	if(isaac.getMonedasRecolectadas() >= item.getPrecio()) {
    	        	isaac.setMonedasRecolectadas(isaac.getMonedasRecolectadas()-item.getPrecio());
    	        	new Sonido("powerUp",0.2f);
    	        	
    	        	if(item.getNombre().equals("Cebolla")) {
	                    isaac.setShootDelay(isaac.getShootDelay()-15);
	                }
	                if(item.getNombre().equals("Jeringa")) {
	                    isaac.setTearRange(isaac.getTearRange()+0.5f);
	                }
	                if(item.getNombre().equals("Corazon")) {
	                    isaac.setLife(6);
	                }
	                if(item.getNombre().equals("Cinturon")) {
	                    isaac.setSpeed(isaac.getSpeed()+1);
	                    cintosRecogidos++;
	                }
	                if(item.getNombre().equals("Corona")) {
	                    isaac.setTearSize(isaac.getTearSize()+5);
	                    isaac.setDamage(isaac.getDamage()+1);
	                    coronasRecogidas++;
	                   	for (int j = 0; j < enemigos.size(); j++) {
	                    	enemigos.get(j).setLife(enemigos.get(i).getLife()+2);
	                   	}
	                }
	                powerUps.remove(i);
            	}
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
        
        isaac.paint(g, isaac.isInvencible());
        
        for (Enemigo enemigo : enemigos) {
            enemigo.paint(g);
        }
        for (Item item : powerUps) {
            g.drawImage(item.getSprite(), item.getX(), item.getY(), item.getAncho(), item.getAlto(), null);
        }
        for (Item item : itemsGenerados) {
            g.drawImage(item.getSprite(), item.getX(), item.getY(), item.getAncho(), item.getAlto(), null);
        }
        for (Item moneda : monedas) {
            g.drawImage(moneda.getSprite(), moneda.getX(), moneda.getY(), moneda.getAncho(), moneda.getAlto(), null);
        }
        //VIDAS
        for (int i = 0; i < isaac.getLife(); i++) {
        	Objeto corazon = corazones.get(i);
        	
        	int x = 10 + (i * (20 + 5));
        	g.drawImage(corazon.getSprite(), x, 10, corazon.getAncho(), corazon.getAlto(), null);
        }
        
        //(PROVOCA QUE TARDE EN CARGAR OJO, PANTALLAZO BLANCO)
        // Agregar número de monedas
        Item moneda = new Item("resources/moneda.png", "Moneda", 33, 33, 0, 0);
       
        g.setFont(font);
        g.setColor(Color.WHITE);
        //int monedas = isaac.getMonedasRecolectadas(); // Obtener la cantidad de monedas del jugador
        String textoMonedas = "x"+isaac.getMonedasRecolectadas();
        //int xTexto = 10 + moneda.getAncho() + 5; // Posición x del texto al lado de la moneda
        //int yTexto = 48 + moneda.getAlto() / 2; // Posición y del texto centrado verticalmente
        g.drawString(textoMonedas, 48, 65);
        g.drawImage(moneda.getSprite(), 10, 38, moneda.getAncho(), moneda.getAlto(), null);

        g.drawString("Score: "+score, 850, 40);
    }
    
    //Variables globales para controlar la cantidad de piedras y cacas
    private int cantidadPiedras = 6;
    //private int cantidadPiedrasPicos = 3;
    private int cantidadCacas = 3;

    public void generarObjetos() {
        int areaAncho = 610;
        int areaAlto = 260;
        
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
    
    private void generarItemsTienda() {
    	List<Item> listaItems = new ArrayList<>();

    	Item item1 = new Item("resources/CorazonHorf.png",     "Corazon", 15, 35, 33, 0, 0);
        if(cintosRecogidos < 5) {
        	Item item2 = new Item("resources/Cinturon.png",       "Cinturon", 15, 35, 33, 0, 0);
            listaItems.add(item2);
        }
        Item item3 = new Item("resources/CebollaTriste.png",   "Cebolla", 15, 29, 51, 0, 0);
        Item item4 = new Item("resources/Jeringa.png",  	   "Jeringa", 15, 24, 42, 0, 0);
        if(coronasRecogidas < 5) {
        	Item item5 = new Item("resources/Corona.png",  	        "Corona", 15, 56, 35, 0, 0);
            listaItems.add(item5);
        }

        listaItems.add(item1);
        listaItems.add(item3);
        listaItems.add(item4);

        // Agregar dos elementos aleatorios de la lista a items
        Random random = new Random();
        int espacioItems = 0;
        while (powerUps.size() < 2 && listaItems.size() > 0) {
            int randomIndex = random.nextInt(listaItems.size());
            Item randomItem = listaItems.get(randomIndex);
            randomItem.setX(380+espacioItems);
            randomItem.setY(316);
            powerUps.add(randomItem);
            espacioItems+=277;
            listaItems.remove(randomIndex);
        }
    }

    private void eliminarItems() {
    	powerUps.clear();
    }
    
    private void eliminarMonedas() {
    	monedas.clear();
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
            enemigos.add(new Enemigo("resources/Horf.png", "resources/redtear.png", "Horf", 55, 55, velocidadH, puedeMoverseH, disparaH, dañoH, tamañoLagrimasH, velocidaLagrimasH, rangoLagrimasH, frecuenciaLagrimasH, vidaH, tiempoImunidadH, probabilidadMonedaH, x, y));
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
            enemigos.add(new Enemigo("resources/mosca.png", "resources/redtear.png", "Mosca", 34, 26, velocidadM, puedeMoverseM, disparaM, dañoM, tamañoLagrimasM, velocidaLagrimasM, rangoLagrimasM, frecuenciaLagrimasM, vidaM, tiempoImunidadM, probabilidadMonedaM, x, y));
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
            enemigos.add(new Enemigo("resources/skinny.png", "resources/redtear.png", "Skinny", 63, 80, velocidadS, puedeMoverseS, disparaS, dañoS, tamañoLagrimasS, velocidaLagrimasS, rangoLagrimasS, frecuenciaLagrimasS, vidaS, tiempoImunidadS, probabilidadMonedaS, x, y));
        }
    }
    
    public void eliminarEnemigos(){
    	enemigos.clear();
    }
    
    int nuevoAleatorio;
    public void nuevaSala() {
    	float randomNum = new Random().nextFloat();
    	//Generar tienda
        if (randomNum <= probabilidadTienda) {
        	eliminarObjetos();
            eliminarEnemigos();
            eliminarMonedas();
            eliminarItems();
            generarFondo("resources/tienda.png");
            generarItemsTienda();
        }
        //Generar salá normal
        else {
	        eliminarObjetos();
	        eliminarEnemigos();
	        eliminarMonedas();
            eliminarItems();
	        generarFondo("resources/mapa.png");
	        generarObjetos();
	        generarEnemigos();
        }
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
	    eliminarMonedas();
		generarFondo("resources/instrucciones.png"); 
		score = 0;
		isaac.setMonedasRecolectadas(0);
        nuevoJuego = true;
	}
	
	public Jugador getIsaac() {
		return isaac;
	}

    public int getScore() { 
		return score;
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