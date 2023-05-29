import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Image;

public class Menu extends JPanel {

    private JPanel fondo;
    private JLabel carga ;
    private ImageIcon imagen1 ;
    private ImageIcon imagen2;
    
    public Menu (String ruta) {
        //Propiedades del panel
		setLayout(null);

		//Fondo de inicio
		fondo = new JPanel();
		fondo.setBackground(Color.white);
		fondo.setSize(1080, 690);
		fondo.setLocation(0,0);
		add(fondo);

		ImageIcon imagen  = new ImageIcon(ruta+"menu1.png");
        ImageIcon imagenDos = new ImageIcon(ruta+"menu2.png");
		
        Image imagenGif = imagen.getImage();
		
		// Redimensionar la imagen
		Image imagenRedimensionada = imagenGif.getScaledInstance(1080, 690, Image.SCALE_DEFAULT);
		 imagen1 = new ImageIcon(imagenRedimensionada);
		 carga = new JLabel(imagen1);

         imagenGif = imagenDos.getImage();
		
         // Redimensionar la imagen
         imagenRedimensionada = imagenGif.getScaledInstance(1080, 690, Image.SCALE_DEFAULT);
          imagen2 = new ImageIcon(imagenRedimensionada);
       

        //carga  = new JLabel(imagen);
		fondo.add(carga);
    }

    public void setMenu1(){ //W
    	carga.setIcon(imagen1);
        revalidate();
        repaint();
        
    }
    public void setMenu2(){ // S
  
    	carga.setIcon(imagen2);
        revalidate();
        repaint();
    }

}
