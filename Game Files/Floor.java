import net.slashie.libjcsi.CSIColor;

public class Floor{
    public String[][] map = new String[80][22];

    public Floor(){
	for(int x = 0; x < map.length; x++){
	    for(int y = 0; y < map[0].length; y++){
		map[x][y] = "X";
	    }
	}
	
	for(int x = 35; x < 45; x++){
	    for(int y = 7; y < 17; y++){
		map[x][y] = ".";
	    }
	}
    }
}
