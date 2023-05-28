import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

public class Menu extends JPanel {

    private JPanel fondo;
    private JLabel carga ;
    private ImageIcon imagen ;
    private ImageIcon imagen2;
    
    public Menu (String ruta) {
        //Propiedades del panel
		setLayout(null);

		//Fondo de inicio
		fondo = new JPanel();
		fondo.setBackground(Color.white);
		fondo.setSize(800, 500);
		fondo.setLocation(0,0);
		add(fondo);

		imagen  = new ImageIcon(ruta+"menu1.png");
        imagen2 = new ImageIcon(ruta+"menu2.png");
		
        carga  = new JLabel(imagen);
		fondo.add(carga);
    }

    public void setMenu1(){ //W
    	carga.setIcon(imagen);
        revalidate();
        repaint();
        
    }
    public void setMenu2(){ // S
  
    	carga.setIcon(imagen2);
        revalidate();
        repaint();
    }

}
