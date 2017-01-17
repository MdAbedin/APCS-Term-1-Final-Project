import java.util.Random;

public class Enemy{
    public int x,y;
    public int hp;
    public Random rng = new Random();
    public boolean living = true;
    public int aggro = 0;
    
    public Enemy(int x, int y, int hp){
	this.x = x;
	this.y = y;
	this.hp = hp;
    }

    public void act(String[][] map, String[][] dynamicMap, Player p, Room room){
	if(living){
	    dynamicMap[x][y] = " ";
	    int xDir = rng.nextInt(3)-1;
	    int yDir = 0;
	    if(xDir == 0){
		yDir = rng.nextInt(3)-1;
	    }
	    
	    if(x > room.x && x < room.x + room.xln && y > room.y && y < room.y + room.yln){
		aggro = 10;
	    }

	    if(aggro > 0){
		xDir = (int)(Math.cbrt(Math.cbrt(p.x - x)));
		yDir = 0;
		if(xDir == 0){
		    yDir = (int)(Math.cbrt(Math.cbrt(p.y - y)));
		}

		aggro--;
	    }

	    if(canMoveTo(x+xDir, y+yDir, map, dynamicMap)){
		x += xDir;
		y += yDir;
	    }
	    else if(dynamicMap[x+xDir][y+yDir].equals("@")){
		p.message = "E did 1 damage";
		p.hp -= 1;
	    }
	    else{
		if(xDir != 0){
		    xDir = 0;
		    yDir = (int)(Math.cbrt(Math.cbrt(p.y - y)));
		    if(!canMoveTo(x, y+yDir, map, dynamicMap)){
			yDir *= -1;
		    }
		}
		else{
		    yDir = 0;
		    xDir = (int)(Math.cbrt(Math.cbrt(p.x - x)));
		    if(!canMoveTo(x+xDir, y, map, dynamicMap)){
			xDir *= -1;
		    }		    
		}

		x += xDir;
		y += yDir;
	    }

	    dynamicMap[x][y] = "E";
	}
    }

    public boolean canMoveTo(int x, int y, String[][] map, String[][] dynamicMap){
	return (map[x][y].equals(".") || map[x][y].equals("+") || map[x][y].equals("#") || map[x][y].equals("%") || map[x][y].equals("*")) && dynamicMap[x][y].equals(" ");
    }
}
