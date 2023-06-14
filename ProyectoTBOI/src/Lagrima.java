public class Lagrima {

    private int tamaño;
    private int x;
    private int y;
    private int velocityX;
    private int velocityY;
    
    private long startTime; // Tiempo de inicio de vida de la lágrima
    private long lifetime; // Tiempo de vida máximo de la lágrima en milisegundos
    
    public Lagrima(int tamaño, int initialX, int initialY, int velocityX, int velocityY, float lifetimeSeconds) {
        this.tamaño = tamaño;
        x = initialX;
        y = initialY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.lifetime = (long) (lifetimeSeconds * 1000); // Convertir el tiempo de vida a milisegundos
        this.startTime = System.currentTimeMillis(); // Guardar el tiempo de inicio de vida
    }

    public void update() {
        x += velocityX;
        y += velocityY;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getLifetime() {
        return lifetime;
    }

    public void setLifetime(long lifetime) {
        this.lifetime = lifetime;
    }
}