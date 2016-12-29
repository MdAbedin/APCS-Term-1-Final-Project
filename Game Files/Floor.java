import net.slashie.libjcsi.CSIColor;
import java.util.Random;

public class Floor{
    public String[][] map = new String[80][22];
    public Random rng = new Random();
    public Floor(){
	for(int x = 0; x < map.length; x++){
	    for(int y = 0; y < map[0].length; y++){
		map[x][y] = " ";
	    }
	}

	makeRoom();
    }

    public void makeRoom(){
// The Length of the Room will always be at least 5
  int xln = rng.nextInt(27)+5;
	int x = rng.nextInt(80-xln);
	int yln = rng.nextInt(7)+5;
	int y = rng.nextInt(22-yln);

	for(int c = x; c < x + xln; c++){
	    for(int r = y; r < y + yln; r++){
		if(r == y || r == y+yln-1){
		    map[c][r] = "-";
		}
		else if(c == x || c == x+xln-1){
		    map[c][r] = ":";
		}
		else{
		    map[c][r] = ".";
		}
	    }
	}
    }

//  public void makePath(){
//    int startxy;
//    if (rng.nextInt(1) == 1){
//      startxy =
//    }
//  }
}
