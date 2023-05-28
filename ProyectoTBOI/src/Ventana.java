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
	
	private int largo = 800;
	private int ancho = 500;
	private int contador = 0;

	//Ruta de recursos 
	private String ruta = "resources/"; 
	
	private Inicio inicio = new Inicio(ruta);
	private Menu menu = new Menu(ruta);
	
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
			int opcion;

			@Override
			public void keyPressed(KeyEvent  e) {
			
				int keyCode = e.getKeyCode(); 
				
				System.out.println(keyCode);
				if(keyCode == KeyEvent.VK_W){
					menu.setMenu1();
					opcion = 1;
					actualizar();
				}
				if(keyCode == KeyEvent.VK_S){
					menu.setMenu2();
					opcion = 2;
					actualizar();
				}
				if (keyCode == 32) { // Realizar algo cuando se presiona la tecla enter
					if(opcion == 1) {
						//mapa instrucciones
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