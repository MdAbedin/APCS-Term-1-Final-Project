import java.util.Random;

public class Enemy{
    public int x,y;
    public int hp;
    public Random rng = new Random();
    
    public Enemy(int x, int y, int hp){
	this.x = x;
	this.y = y;
	this.hp = hp;
    }

    public void act(String[][] map, Player p){
	int xDir = rng.nextInt(3)-1;
	int yDir = rng.nextInt(3)-1;
	
	if(canMoveTo(x+xDir, y+yDir, map)){
	    x += xDir;
	    y += yDir;
	}
    }

    public boolean canMoveTo(int x, int y, String[][] map){
	return map[x][y].equals(".") || map[x][y].equals("+") || map[x][y].equals("#") || map[x][y].equals("%") || map[x][y].equals("*");
    }
}
