import java.awt.Color;
import javax.swing.JPanel;

public class GameOver extends JPanel{
	public GameOver(String ruta) {
		//Propiedades del panel
		setLayout(null);

		//Fondo de inicio
		JPanel fondo = new JPanel();
		fondo.setBackground(Color.white);
		fondo.setSize(1080, 690);
		fondo.setLocation(0,0);
		add(fondo);
				
		Imagen fondoInicio = new Imagen(ruta+"gameOver.png",1080,650,fondo);
	}
}