import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IngresarNombre extends JPanel {
	JButton btnSiguiente;
	
	public IngresarNombre(String ruta) {
		setLayout(null);

		//Fondo de inicio
		JPanel fondo = new JPanel();
		fondo.setBackground(Color.white);
		fondo.setSize(1080, 690);
		fondo.setLocation(0,0);
		
		JTextField campoNombre= new JTextField();
		campoNombre.setSize(400,50);
		campoNombre.setLocation(330, 310);
		campoNombre.setFont(new Font("Courier New", Font.PLAIN, 25));
		
		ImageIcon icono = new ImageIcon(ruta+"botonSiguiente.png");
         btnSiguiente = new JButton(icono);
         
         btnSiguiente.setSize(icono.getIconWidth(), icono.getIconHeight());
         btnSiguiente.setLocation(430, 450);
         btnSiguiente.setOpaque(false);
         btnSiguiente.setContentAreaFilled(false);
         btnSiguiente.setBorderPainted(false);
        // btnSiguiente.setFocusPainted(false);
		
		Imagen fondoInicio = new Imagen(ruta+"ingresarNombre.png",1080,690,fondo);
		
		add(campoNombre);
		add(btnSiguiente);
		add(fondo);
		
		repaint();
		revalidate();
		
				
	}
	
	public JButton getBotonSiguiente() {
		return btnSiguiente;
	}
}
