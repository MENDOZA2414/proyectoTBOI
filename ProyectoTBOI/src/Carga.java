import java.awt.Color;
import javax.swing.JPanel;

public class Carga extends JPanel {
	public Carga(String ruta) {
		//Propiedades del panel
		//setBackground(Color.decode(""));
		setLayout(null);
		
		//Fondo componentes del panel
		JPanel fondo = new JPanel();
	//	fondo.setBackground(Color.decode(""));
		fondo.setSize(500, 300);
		fondo.setLocation(0,150);
		add(fondo);
		
		//Agrega imagen a la pantalla de carga
		Imagen splash = new Imagen(ruta + "", 400,300,fondo);
	}
}