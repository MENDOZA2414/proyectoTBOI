//PROYECTO FINAL
//MARIO ALBERTO CASTELLANOS
//JOSÉ MIGUEL MENDOZA ANAYA
//ALMA DELIA VARGAS GONZALEZ
//CHRISTIAN RODRIGUEZ MORENO

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ventana extends JFrame{
	
	private int largo = 1080;
	private int ancho = 690;
	private int contador = 0;

	//Ruta de recursos 
	private String ruta = "resources/"; 
	
	private Inicio inicio = new Inicio(ruta);
	private Menu menu = new Menu(ruta);
	private Juego juego = new Juego();
	private Sonido sonido = new Sonido();
	public Ventana() {
		//Propiedades de la ventana
		super("The binding of Isaac");
		setSize(largo, ancho);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		keysInicio();
		add(inicio);

	    setLocationRelativeTo(null);
	    setVisible(true);
	    setResizable(false);
	    
		//pantallaCarga();
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
					menu.setMenu1();
					opcion = 1;
					actualizar();
				}
				if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN){
					menu.setMenu2();
					opcion = 2;
					actualizar();
				}
				if (keyCode == 10 || keyCode == 32) { // Realizar algo cuando se presiona la tecla enter
					if(opcion == 1) {
						remove(menu);
						
						add(juego);
						juego.requestFocusInWindow(); // Establece el foco en el panel de juego
						keysJuego();
		                actualizar();
					}
					if(opcion == 2) {
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
			            juego.isaac.setVelocityX(-juego.isaac.getCharacterSpeed());
			            break;
			        case KeyEvent.VK_D:
			        	juego.isaac.setVelocityX(juego.isaac.getCharacterSpeed());
			            break;
			        case KeyEvent.VK_W:
			        	juego.isaac.setVelocityY(-juego.isaac.getCharacterSpeed());
			            break;
			        case KeyEvent.VK_S:
			        	juego.isaac.setVelocityY(juego.isaac.getCharacterSpeed());
			            break;
			        case KeyEvent.VK_UP:
			            juego.isaac.shootUp();
			            break;
			        case KeyEvent.VK_DOWN:
			            juego.isaac.shootDown();
			            break;
			        case KeyEvent.VK_LEFT:
			            juego.isaac.shootLeft();
			            break;
			        case KeyEvent.VK_RIGHT:
			            juego.isaac.shootRight();
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
					juego.isaac.setVelocityX(0);
		        } else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
		        	juego.isaac.setVelocityY(0);
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