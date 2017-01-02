import net.slashie.libjcsi.CSIColor;
import java.util.Random;
import java.util.ArrayList;

public class Floor{
    public String[][] map = new String[80][22];
    public Random rng = new Random();
    public ArrayList<Room> rooms = new ArrayList<Room>();
    
    public Floor(){
	initialize();
	for(int i = 0; i < rng.nextInt(5) + 5; i++){
	    makeRoom();
	}
    }

    public void initialize(){
	for(int x = 0; x < map.length; x++){
	    for(int y = 0; y < map[0].length; y++){
		map[x][y] = " ";
	    }
	}
    }
    
    public void makeRoom(){
	boolean done = false;

	while(!done){
	    int xln = rng.nextInt(27)+5;
	    int x = rng.nextInt(79-xln)+1;
	    int yln = rng.nextInt(6)+4;
	    int y = rng.nextInt(21-yln)+1;
	    Room room = new Room(x, y, xln, yln);
	    
	    if(fits(room)){
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

		rooms.add(room);
		done = true;
	    }
	}
    }

    public boolean fits(Room room){
	for(int x = room.x; x < room.x + room.xln; x++){
	    for(int y = room.y; y < room.y + room.yln; y++){
		if(!map[x][y].equals(" ") || !map[x+1][y].equals(" ") || !map[x-1][y].equals(" ") || !map[x][y+1].equals(" ") || !map[x][y-1].equals(" ")){
		    return false;
		}
	    }
	}

	return true;
    }
}
