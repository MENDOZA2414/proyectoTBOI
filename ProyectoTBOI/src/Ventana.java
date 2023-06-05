//PROYECTO FINAL
//MARIO ALBERTO CASTELLANOS
//JOSÉ MIGUEL MENDOZA ANAYA
//ALMA DELIA VARGAS GONZALEZ
//CHRISTIAN RODRIGUEZ MORENO

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Ventana extends JFrame{
	
	private int largo = 1080;
	private int ancho = 690;
	private int contador = 0;

	
	//Ruta de recursos 
	private String ruta = "resources/"; 

	private IngresarNombre ingresarNombre = new IngresarNombre(ruta); // <---------------------------------------
	private PanelLeaderBoard panelLeaderBoard;

	private Inicio inicio = new Inicio(ruta);
	private Menu menu = new Menu(ruta);
	private Juego juego = new Juego();
	private GameOver gameover = new GameOver(ruta);
	private Sonido sonido = new Sonido();

	public String rutaTxt = ruta+"scores.txt"; // <---------------------------------------
    public Score score;// <---------------------------------------
    public ListaScores listaScores;//<--------
	public Ventana() {
		//Propiedades de la ventana
		super("The binding of Isaac");
		setSize(largo, ancho);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		score= new Score() ;// <---------------------------------------
	    listaScores= new ListaScores();// <---------------------------------------
	    cargarTxtScores();// <---------------------------------------
	    panelLeaderBoard= new PanelLeaderBoard(ruta,listaScores.obtenerLista()); 
	    
		keysInicio();
		add(inicio);

	    setLocationRelativeTo(null);
	    setVisible(true);
	    setResizable(false);
		//pantallaCarga();
	    
	    Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(juego.getIsaac().getVidas() == 0) {
                	remove(juego);
                	add(gameover);
                    actualizar();
                }
            }
        }, 0, 100);
	}
	//------ mensaje de dialogo customizable----
    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }
	 //-------funcion para agregar del archivo txt los scores a la lista --------
    private void cargarTxtScores() {
        File ruta = new File(rutaTxt);
        try{
            FileReader fr = new FileReader(ruta);
            BufferedReader br = new BufferedReader(fr);

            String linea = null;
            while((linea = br.readLine())!=null){ // --este while agrega a la lista todos los Scores del txt--
                StringTokenizer st = new StringTokenizer(linea, "|"); // esto separa el renglon del txt para poder asignarle al score sus atributos
                score = new Score();
                score.setNombre(st.nextToken());
                score.setScore(Integer.parseInt(st.nextToken())); //conversion a float
                
                listaScores.agregarScore(score);
            }
            br.close();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
  //----- funcion para pasar lo de la lista Scores al txt----
    public void grabar_txt(){
        FileWriter fw;
        PrintWriter pw;
        try{
            fw = new FileWriter(rutaTxt);
            pw = new PrintWriter(fw);
            
            for(int i = 0; i < listaScores.cantidadScore(); i++){
            	score = listaScores.obtenerScore(i);
                pw.println(String.valueOf(score.getNombre()+"|"+score.getScore())); //pasa lo del objeto al archivo
            }
             pw.close();
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public void crearNuevoScore(String nombre,int puntaje ){
    	// evaluar antes si no existe nombre igual
        score = new Score(nombre,puntaje);
        
        
        listaScores.agregarScore(score);
        listaScores.ordenarScores();//   ordenar el array antes de grabar
       // mensaje("Creado correctamente");
        grabar_txt();
    }
	public void keysInicio(){
		inicio.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent  e) {
				
				char keyCode = e.getKeyChar();
				if (keyCode == KeyEvent.VK_SPACE) {
                    inicio.setFocusable(false);
                    remove(inicio);
                    add(menu);
                    menu.requestFocusInWindow(); // Establece el foco en el panel de menú
                    keysMenu();
                    actualizar();
                }
			}

			@Override
			public void keyReleased(KeyEvent  e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent  e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		inicio.setFocusable(true);
        inicio.requestFocusInWindow();
	}

	public void keysMenu(){
		menu.addKeyListener(new KeyListener() {
			int opcion = 1;

			@Override
			public void keyPressed(KeyEvent  e) {
			
				int keyCode = e.getKeyCode(); 
				
				System.out.println(keyCode);
				if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP){
					
					if(opcion>1){
						opcion --;
					}
					actualizar();
				}
				if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN){
					
					if(opcion<3){
						opcion ++;
					}
					actualizar();
				}
				if(opcion==1){
					menu.setMenu1();
				}
				if(opcion==2){
					menu.setMenu2();
				}
				if(opcion==3){
					menu.setMenu3();
				}
				if (keyCode == 10 || keyCode == 32) { // Realizar algo cuando se presiona la tecla enter
					if(opcion == 1) {
						remove(menu);
						
						add(juego);
						juego.requestFocusInWindow(); // Establece el foco en el panel de juego
						keysJuego();
		                actualizar();
					}
					if(opcion==2) {
						remove(menu);
						add(panelLeaderBoard);
						 actualizar();
					}
					if(opcion == 3) {
						System.exit(0);
					}
					//remove(inicio);
					//add(menu);
					//actualizar();
        		}
			}

			@Override
			public void keyReleased(KeyEvent  e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent  e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		menu.setFocusable(true);
        menu.requestFocusInWindow();
	}
	
	public void keysJuego() {
		juego.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyPressed(KeyEvent e) {
			    int keyCode = e.getKeyCode();			    	
		    	switch (keyCode) {
		    	case KeyEvent.VK_A:
		    		juego.getIsaac().setVelocityX(-juego.getIsaac().getCharacterSpeed());
		    		break;
		    	case KeyEvent.VK_D:
		    		juego.getIsaac().setVelocityX(juego.getIsaac().getCharacterSpeed());
		    		break;
		    	case KeyEvent.VK_W:
		    		juego.getIsaac().setVelocityY(-juego.getIsaac().getCharacterSpeed());
		    		break;
		    	case KeyEvent.VK_S:
		    		juego.getIsaac().setVelocityY(juego.getIsaac().getCharacterSpeed());
		    		break;
		    	case KeyEvent.VK_UP:
		    		juego.getIsaac().shootUp();
		    		break;
		    	case KeyEvent.VK_DOWN:
		    		juego.getIsaac().shootDown();
		    		break;
		    	case KeyEvent.VK_LEFT:
		    		juego.getIsaac().shootLeft();
		    		break;
		    	case KeyEvent.VK_RIGHT:
		    		juego.getIsaac().shootRight();
		    		break;
		    	case KeyEvent.VK_M:
		    		if (sonido.getClip().isRunning()) {   //Musica que puso Chris
		    			System.out.println("Muted...");
		    			sonido.getClip().stop();
		    		} else {
		    			System.out.println("Playing...");
		    			sonido.getClip().start();
		    		}
		    		break;
		    	case KeyEvent.VK_ESCAPE:
		    		System.out.println("Exit...");
		    		System.exit(0);
		    		break;
		    	}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
					juego.getIsaac().setVelocityX(0);
		        } else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
		        	juego.getIsaac().setVelocityY(0);
		        }	
			}
			
		});
		
		juego.setFocusable(true);
        juego.requestFocusInWindow();
	}
	
	
	
	//Metodo para la pantalla de carga
	public void pantallaCarga() {
		Carga carga = new Carga(ruta);
	    add(carga);
	    Tiempo(carga);
	    actualizar();
	}
	
	//Hace invisible el panel cuando pasan 3 segundos	
	public void Tiempo(JPanel panelCarga) {
		Timer timer = new Timer();
		TimerTask tarea = new TimerTask() {
			@Override
			public void run() {
				contador++;
				if(contador == 3) {
					
					panelCarga.setVisible(false);
					timer.cancel();	
				}
				System.out.println(contador);
			}
		};
		timer.schedule(tarea,0,1000);
	}

	//Metodo para actualizar la ventana
	public void actualizar(){
		repaint();
		revalidate();
	}
	
	//Remueve todos los paneles
	public void removerPaneles() {
		
	}
}