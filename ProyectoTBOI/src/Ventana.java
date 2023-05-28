//PROYECTO FINAL
//MARIO ALBERTO CASTELLANOS
//JOSÉ MIGUEL MENDOZA ANAYA
//ALMA DELIA VARGAS GONZALEZ
//CHRISTIAN RODRIGUEZ MORENO

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
	
	
	public Ventana() {
		//Propiedades de la ventana
		super("The binding of Isaac");
		setSize(largo, ancho);
		setVisible(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	    setResizable(false);
		//pantallaCarga();
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