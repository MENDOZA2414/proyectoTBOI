import java.awt.Color;
import javax.swing.JPanel;

public class Carga extends JPanel {
	public Carga(String ruta) {
		//Propiedades del panel
		setLayout(null);

		//Fondo de inicio
		JPanel fondo = new JPanel();
		fondo.setBackground(Color.black);
		fondo.setSize(1080, 690);
		fondo.setLocation(0,0);
		add(fondo);
				
		Imagen fondoInicio = new Imagen(ruta+"splash.gif",1080,690,fondo);
	}
}