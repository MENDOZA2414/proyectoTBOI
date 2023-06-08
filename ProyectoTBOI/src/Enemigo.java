import java.util.Random;

public class Enemigo extends Entidad {
    public Enemigo(String spritePath, String tearPath, String nombre, int ancho, int alto, int speed, boolean canShoot, int tearSpeed, int shootDelay, int life, int x, int y) {
        super(spritePath, tearPath, nombre, ancho, alto, speed, canShoot, tearSpeed, shootDelay, life, x, y);
    }

	public void mover() {
		
		Random random = new Random();
        int cambioX = random.nextInt(3) - 1; //Valores aleatorios entre -1 y 1
        int cambioY = random.nextInt(3) - 1; //Valores aleatorios entre -1 y 1

        int nuevaX = super.getX() + super.getSpeed() * cambioX;
        int nuevaY = super.getY() + super.getSpeed() * cambioY;
        
        if(!(nuevaX < 80 || nuevaX > Juego.WIDTH - 110|| nuevaY < 80 || nuevaY > Juego.HEIGHT - 110)) {
            super.setX(nuevaX);
            super.setY(nuevaY);
        }
    }
}