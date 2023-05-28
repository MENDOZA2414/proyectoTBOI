import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Inicio extends JPanel{
	public Inicio(String ruta) {
		//Propiedades del panel
		setLayout(null);

		//Fondo de inicio
		JPanel fondo = new JPanel();
		fondo.setBackground(Color.white);
		fondo.setSize(800, 500);
		fondo.setLocation(0,0);
		add(fondo);
				
		Imagen fondoInicio = new Imagen(ruta+"inicio.png",fondo);
	}
}