import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Imagen {

	public Imagen(String nombre,JPanel pantalla) {
		ImageIcon imagen = new ImageIcon(nombre);
		JLabel carga = new JLabel(imagen);
		pantalla.add(carga);
	}
	
	public Imagen(String nombre, int ancho, int alto, JPanel pantalla) {
		ImageIcon imagen = new ImageIcon(nombre);
		Image imagenGif = imagen.getImage();
		
		// Redimensionar la imagen
		Image imagenRedimensionada = imagenGif.getScaledInstance(ancho, alto, Image.SCALE_DEFAULT);
		ImageIcon imagen2 = new ImageIcon(imagenRedimensionada);
		JLabel carga = new JLabel(imagen2);
		
		pantalla.add(carga);
	}
}
