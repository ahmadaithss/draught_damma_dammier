package damme.jeu.com.jeudamme.models;

public class Player {
	private  PionColor pionColor;
	private String name;
	private int id;
	public Player(String name, int id) {
		this.name = name;
		this.id = id;
	}
	public Player(String name,int id,PionColor pionColor){
		this(name,id);
		this.pionColor=pionColor;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public PionColor getPionColor() {
		return pionColor;
	}

	public void setPionColor(PionColor pionColor) {
		this.pionColor = pionColor;
	}

	@Override
	public boolean equals(Object obj) {
		Player p=(Player)obj;
		return p.id==id ;
	}
}
