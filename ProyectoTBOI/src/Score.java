
public class Score {
	private String nombre;
    private int score;
    
    public Score(String nombre, int score) {
    	this.nombre=nombre;
    	this.score = score;
    }
    public Score() {
    	
    }
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}

