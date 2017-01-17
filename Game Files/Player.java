import net.slashie.libjcsi.CharKey;
import java.util.ArrayList;

public class Player{
    public int x, y;
    public int level = 1, gold = 0, hp = 20, maxhp = 20, str = 4, ar = 0, exp = 0, maxexp = 30;
    public int bumps;
    public String message;
    public boolean hasAmulet = false;
    public boolean moved = false;

    public Player(int x, int y){
	this.x = x;
	this.y = y;
    }

    public void act(int key, String[][] map, String[][] dynamicMap, ArrayList<Enemy> enemies){
	moved = false;
	message = "";
	dynamicMap[x][y] = " ";
	int xDir = 0;
	int yDir = 0;

	if(key == CharKey.UARROW){
	    yDir--;
	}
	if(key == CharKey.DARROW){
	    yDir++;
	}
	if(key == CharKey.LARROW){
	    xDir--;
	}
	if(key == CharKey.RARROW){
	    xDir++;
	}
	
	if(canMoveTo(x+xDir, y+yDir, map, dynamicMap) && xDir + yDir != 0){
	    x += xDir;
	    y += yDir;
	    dynamicMap[x][y] = "@";
	    moved = true;
	}
	else if(dynamicMap[x+xDir][y+yDir].equals("E")){
	    message = "Attacked E";
	    for(Enemy e: enemies){
		if(e.living && e.x == x+xDir && e.y == y+yDir){
		    dynamicMap[x+xDir][y+yDir] = " ";
		    e.hp -= 1;
		    if(e.hp <= 0){
			e.living = false;
		    }
		}
	    }
	    moved = true;
	}
	
	dynamicMap[x][y] = "@";
    }

    public boolean canMoveTo(int x, int y, String[][] map, String[][] dynamicMap){
	return (map[x][y].equals(".") || map[x][y].equals("+") || map[x][y].equals("#") || map[x][y].equals("%") || map[x][y].equals("*")) && dynamicMap[x][y].equals(" ");
    }

    public void pickUp(Item i){
      
    }
}
