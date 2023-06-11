
public class Lagrima {

    private int tamaño;
    private int x;
    private int y;
    private int velocityX;
    private int velocityY;
    
    public Lagrima(int tamaño, int initialX, int initialY, int velocityX, int velocityY) {
    	this.tamaño = tamaño;
    	x = initialX;
        y = initialY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public void update() {
        x += velocityX;
        y += velocityY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

	public int getTamaño() {
		return tamaño;
	}
}