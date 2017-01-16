import java.util.Random;

public class Enemy{
    public int x,y;
    public int hp;
    public Random rng = new Random();
    public boolean living = true;
    
    public Enemy(int x, int y, int hp){
	this.x = x;
	this.y = y;
	this.hp = hp;
    }

    public void act(String[][] map, String[][] dynamicMap, Player p){
	if(living){
	    int xDir = rng.nextInt(3)-1;
	    int yDir = 0;
	    if(xDir == 0){
		yDir = rng.nextInt(3)-1;
	    }
	    dynamicMap[x][y] = " ";
	
	    if(canMoveTo(x+xDir, y+yDir, map, dynamicMap)){
		x += xDir;
		y += yDir;
	    }

	    if(dynamicMap[x+xDir][y+yDir].equals("@")){
		p.message = "E did 1 damage";
		p.hp -= 1;
	    }

	    dynamicMap[x][y] = "E";

	}
    }

    public boolean canMoveTo(int x, int y, String[][] map, String[][] dynamicMap){
	return (map[x][y].equals(".") || map[x][y].equals("+") || map[x][y].equals("#") || map[x][y].equals("%") || map[x][y].equals("*")) && dynamicMap[x][y].equals(" ");
    }
}
