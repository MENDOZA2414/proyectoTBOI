import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Puerta extends Objeto {
    private boolean abierta;
    private String rutaOriginal;

    public Puerta(String spritePath, int tamaño, int x, int y, boolean abierta) {
        super(spritePath, tamaño, x, y);
        rutaOriginal=spritePath;
        this.abierta = abierta;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    public void setSpriteCerrado(){
        try {
            this.setSprite(ImageIO.read(new File(rutaOriginal)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }
    public void setSpriteAbierto(int puerta) {
        switch(puerta){
            case 0:
            try {
                this.setSprite(ImageIO.read(new File("resources/puertaArriba.png")));  
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;

        case 1:    
        try {
            this.setSprite(ImageIO.read(new File("resources/puertaAbajo.png")));  ;
        } catch (IOException e) {
            e.printStackTrace();
        }
            break;

        case 2:
        try {
            this.setSprite(ImageIO.read(new File("resources/puertaIzquierda.png")));  ;
        } catch (IOException e) {
            e.printStackTrace();
        }
            break;

        case 3:
        try {
            this.setSprite(ImageIO.read(new File("resources/puertaDerecha.png")));  ;
        } catch (IOException e) {
            e.printStackTrace();
        }
            break;
        }
        
	}
}