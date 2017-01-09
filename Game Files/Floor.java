import java.util.Random;
import java.util.ArrayList;

public class Floor{
    public String[][] map = new String[80][22];
    public Random rng = new Random();
    public ArrayList<Room> rooms = new ArrayList<Room>();
    public ArrayList<Integer> sections = new ArrayList<Integer>();
    public int stairsX, stairsY;

    public Floor(){
	initialize();
	for(int i = 0; i < rng.nextInt(6) + 4; i++){
	    makeRoom();
	    connectRooms();
	}
	placeStairs();
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
	    int xln = rng.nextInt(22)+5;
	    int x = rng.nextInt(79-xln)+1;
	    int yln = rng.nextInt(4)+4;
	    int y = rng.nextInt(21-yln)+1;
	    Room room = new Room(x, y, xln, yln);

	    if(!sections.contains(room.section) && reachable(room) && fits(room)){
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

		//map[room.centerX][room.centerY] = Integer.toString(room.section);
		rooms.add(room);
		sections.add(room.section);
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

    public boolean reachable(Room room){
	if(rooms.size() == 0){
	    return true;
	}

	for(Room r: rooms){
	    if(room.canConnect(r)){
		return true;
	    }
	}

	return false;
    }

    public void connectRooms(){
	for(int i = 0; i < rooms.size(); i++){
	    for(int j = i + 1; j < rooms.size(); j++){
		if(rooms.get(i).canConnect(rooms.get(j))){
		    connect(rooms.get(i), rooms.get(j));
		    rooms.get(i).connectedRooms.add(rooms.get(j).section);
		    rooms.get(j).connectedRooms.add(rooms.get(i).section);
		}
	    }
	}
    }

    public void connect(Room i, Room j){
	int[] iExit = i.pickExit(j, rng);
	int[] jExit = j.pickExit(i, rng);

	map[iExit[0]][iExit[1]] = "+";
	map[jExit[0]][jExit[1]] = "+";

	makePath(iExit, jExit);
    }

    public void makePath(int[] i, int[] j){
	int xDir = (int)(Math.cbrt(Math.cbrt(j[0] - i[0])));
	int yDir = (int)(Math.cbrt(Math.cbrt(j[1] - i[1])));
	int currentX = i[0];
	int currentY = i[1];
	int finalX = j[0];
	int finalY = j[1];

	if(map[currentX+1][currentY].equals(".") || map[currentX-1][currentY].equals(".")){
	    finalX -= xDir;
	}
	else{
	    finalY -= yDir;
	}

	while(currentX != finalX || currentY != finalY){
	    if(currentX != finalX && (map[currentX + xDir][currentY].equals(" ") || map[currentX + xDir][currentY].equals("#"))){
		map[currentX + xDir][currentY] = "#";
		currentX += xDir;
	    }
	    else if(currentY != finalY && (map[currentX][currentY + yDir].equals(" ") || map[currentX][currentY + yDir].equals("#"))){
		map[currentX][currentY + yDir] = "#";
		currentY += yDir;
	    }
	    else{
		currentX = finalX;
		currentY = finalY;
	    }
	}
    }

    public void placeStairs(){
	int i = rng.nextInt(rooms.size());
        map[rooms.get(i).centerX][rooms.get(i).centerY] = "%";
	stairsX = rooms.get(i).centerX;
	stairsY = rooms.get(i).centerY;
    }

    public void itemGeneration(Player p){
	int num = rng.nextInt(5) + 1 + p.level;
        ArrayList<Item> items = new ArrayList<Item>();
	for (int i = 0; i < num; i++){
	    //items.add(Item(p));
	}

	System.out.println(items.get(0).type);
	
    }
    
}
