import net.slashie.libjcsi.CharKey;

public class Player{
    public int x, y;
    public int level = 1, gold = 0, hp = 25, maxhp = 25, str = 16, ac = 6, exp = 0, maxexp = 30;
    public int bumps;
    public String message;
    
    public Player(int x, int y){
	this.x = x;
	this.y = y;
    }
    
    public void act(int key, String[][] map){
	message = "";
	
	switch (key){
	case CharKey.UARROW:
	    if(map[x][y-1].equals(".")){
		y--;
	    }
	    else{
		message = "Bumped into a " + map[x][y-1] + " above you";
		bumps++;
	    }
	    break;
	case CharKey.DARROW:
	    if(map[x][y+1].equals(".")){
		y++;
	    }
	    else{
		message = "Bumped into a " + map[x][y+1] + " under you";
		bumps++;
	    }
	    break;
	case CharKey.LARROW:
	    if(map[x-1][y].equals(".")){
		x--;
	    }
	    else{
		message = "Bumped into a " + map[x-1][y] + " left of you";
		bumps++;
	    }
	    break;
	case CharKey.RARROW:
	    if(map[x+1][y].equals(".")){
		x++;
	    }
	    else{
		message = "Bumped into a " + map[x+1][y] + " right of you";
		bumps++;
	    }
	    break;
	}
    }
}