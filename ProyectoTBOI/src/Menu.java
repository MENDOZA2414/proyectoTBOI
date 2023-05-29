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
    private ImageIcon imagen3;
    
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
        ImageIcon imagenTres = new ImageIcon(ruta+"menu3.png");
		
		// Redimensionar la imagen
        Image imagenGif = imagen.getImage();
		Image imagenRedimensionada = imagenGif.getScaledInstance(1080, 690, Image.SCALE_DEFAULT);
		 imagen1 = new ImageIcon(imagenRedimensionada);
		 carga = new JLabel(imagen1);

         // Redimensionar la imagen
         imagenGif = imagenDos.getImage();
         imagenRedimensionada = imagenGif.getScaledInstance(1080, 690, Image.SCALE_DEFAULT);
          imagen2 = new ImageIcon(imagenRedimensionada);

          imagenGif = imagenTres.getImage();
         imagenRedimensionada = imagenGif.getScaledInstance(1080, 690, Image.SCALE_DEFAULT);
          imagen3 = new ImageIcon(imagenRedimensionada);
       

        //carga  = new JLabel(imagen);
		fondo.add(carga);
    }

    public void setMenu1(){ 
    	carga.setIcon(imagen1);
        revalidate();
        repaint();
        
    }
    public void setMenu2(){ 
  
    	carga.setIcon(imagen2);
        revalidate();
        repaint();
    }
    public void setMenu3(){ 
  
    	carga.setIcon(imagen3);
        revalidate();
        repaint();
    }

}
