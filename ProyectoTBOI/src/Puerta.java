public class Puerta extends Objeto {
    private boolean abierta;

    public Puerta(String spritePath, int tamaño, int x, int y, boolean abierta) {
        super(spritePath, tamaño, x, y);
        this.abierta = abierta;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }
}