import net.slashie.libjcsi.CharKey;

public class Player{
    public int x, y;
    public int level = 1, gold = 0, hp = 20, maxhp = 20, str = 4, ar = 0, exp = 0, maxexp = 30;
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
	    if(canMoveTo(x, y-1, map)){
		y--;
	    }
	    else{
		message = "Bumped into a " + map[x][y-1] + " above you";
		bumps++;
	    }
	    break;
	case CharKey.DARROW:
	    if(canMoveTo(x, y+1, map)){
		y++;
	    }
	    else{
		message = "Bumped into a " + map[x][y+1] + " under you";
		bumps++;
	    }
	    break;
	case CharKey.LARROW:
	    if(canMoveTo(x-1, y, map)){
		x--;
	    }
	    else{
		message = "Bumped into a " + map[x-1][y] + " left of you";
		bumps++;
	    }
	    break;
	case CharKey.RARROW:
	    if(canMoveTo(x+1, y, map)){
		x++;
	    }
	    else{
		message = "Bumped into a " + map[x+1][y] + " right of you";
		bumps++;
	    }
	    break;
	}
    }

    public boolean canMoveTo(int x, int y, String[][] map){
	return map[x][y].equals(".") || map[x][y].equals("+") || map[x][y].equals("#") || map[x][y].equals("%");
    }
}
