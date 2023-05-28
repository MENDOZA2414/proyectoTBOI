import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

public class Menu extends JPanel {
    private String ruta;
    private JPanel fondo;
    private JPanel fondo2;
    public Menu (String ruta) {
        this.ruta=ruta;
        //Propiedades del panel
		setLayout(null);

		//Fondo de inicio
		fondo = new JPanel();
		fondo.setBackground(Color.white);
		fondo.setSize(800, 500);
		fondo.setLocation(0,0);
		add(fondo);

        fondo2 = new JPanel();
		fondo2.setBackground(Color.white);
		fondo2.setSize(800, 500);
		fondo2.setLocation(0,0);
		//add(fondo);

		
        ImageIcon imagen = new ImageIcon(ruta+"menu1.png");
        ImageIcon imagen2 = new ImageIcon(ruta+"menu2.png");
		
        JLabel carga = new JLabel(imagen);
		fondo.add(carga);
        		
		//Imagen fondoMenu1 = new Imagen(ruta+"menu1.png",fondo);
        //Imagen fondoMenu2 = new Imagen(ruta+"menu1.png",fondo2);
    }

    public void setMenu1(){
        remove(fondo2);
        remove(fondo);
        
        add(fondo);
        fondo.revalidate();
        fondo.repaint();
        
    }
    public void setMenu2(){
        remove(fondo2);
        remove(fondo);
       
        add(fondo2);
        fondo2.revalidate();
        fondo2.repaint();
    }

}
