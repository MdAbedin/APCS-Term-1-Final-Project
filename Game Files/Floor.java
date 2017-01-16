import java.util.Random;
import java.util.ArrayList;

public class Floor{
    public String[][] map = new String[80][22];
    public String[][] dynamicMap = new String[80][22];
    public Random rng = new Random();
    public ArrayList<Room> rooms = new ArrayList<Room>();
    public ArrayList<Integer> sections = new ArrayList<Integer>();
    public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    public int stairsX, stairsY;
    public int amuletX, amuletY;

    public Floor(int currentFloor, int totalFloors){
	initialize();
	for(int i = 0; i < rng.nextInt(6) + 4; i++){
	    makeRoom();
	    connectRooms();
	}
	placeStairs();
	makeEnemy();
	
	if(currentFloor + 1 == totalFloors){
	    placeAmulet();
	}
    }

    public void initialize(){
	for(int x = 0; x < map.length; x++){
	    for(int y = 0; y < map[0].length; y++){
		map[x][y] = " ";
		dynamicMap[x][y] = " ";
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
		if(!map[x][y].equals(" ") || !map[x+1][y].equals(" ") || !map[x-1][y].equals(" ") || !map[x][y+1].equals(" ") || !map[x][y-1].equals(" ") || !map[x+1][y+1].equals(" ") || !map[x+1][y-1].equals(" ") || !map[x-1][y+1].equals(" ") || !map[x-1][y-1].equals(" ")){
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
	int[] iExit = i.pickExit(j);
	int[] jExit = j.pickExit(i);

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

    public void placeAmulet(){
	boolean done = false;

	while(!done){
	    int i = rng.nextInt(rooms.size());
	    if(map[rooms.get(i).centerX][rooms.get(i).centerY].equals(".")){
		map[rooms.get(i).centerX][rooms.get(i).centerY] = "*";
		amuletX = rooms.get(i).centerX;
		amuletY = rooms.get(i).centerY;
		done = true;
	    }

	}
    }

    public void itemGeneration(Player p){
	int num = rng.nextInt(5) + 1 + p.level;
        ArrayList<Item> items = new ArrayList<Item>();
	for (int i = 0; i < num; i++){
	    items.add(new Item(p));
	}
  for (int x = 0; x < num; x++){
    Room roomAt = rooms.get(rng.nextInt(rooms.size() - 1));
    Item goop = items.get(x);
    if (goop.type.equals("weapon")){
      map[roomAt.x + rng.nextInt(roomAt.xln - 2) + 1][roomAt.y + rng.nextInt(roomAt.yln - 2) + 1] = ")";
    }else if (goop.type.equals("chest")){
      map[roomAt.x + rng.nextInt(roomAt.xln - 2) + 1][roomAt.y + rng.nextInt(roomAt.yln - 2) + 1] = "&";
    }else if (goop.type.equals("hands")){
      map[roomAt.x + rng.nextInt(roomAt.xln - 2) + 1][roomAt.y + rng.nextInt(roomAt.yln - 2) + 1] = "<";
    }else if (goop.type.equals("feet")){
      map[roomAt.x + rng.nextInt(roomAt.xln - 2) + 1][roomAt.y + rng.nextInt(roomAt.yln - 2) + 1] = ">";
    }else if (goop.type.equals("food")){
      map[roomAt.x + rng.nextInt(roomAt.xln - 2) + 1][roomAt.y + rng.nextInt(roomAt.yln - 2) + 1] = "0";
    }else if (goop.type.equals("ring")){
      map[roomAt.x + rng.nextInt(roomAt.xln - 2) + 1][roomAt.y + rng.nextInt(roomAt.yln - 2) + 1] = "{";
    }else if (goop.type.equals("amulet")){
      map[roomAt.x + rng.nextInt(roomAt.xln - 2) + 1][roomAt.y + rng.nextInt(roomAt.yln - 2) + 1] = "]";
    }
  }
    }

    public void removeAmulet(){
	map[amuletX][amuletY] = ".";
    }

    public void makeEnemy(){
	boolean done = false;
        Room r = rooms.get(rng.nextInt(rooms.size()));
	
	while(!done){
	    int x = r.x + 1 + rng.nextInt(r.xln - 2);
	    int y = r.y + 1 + rng.nextInt(r.yln - 2);
	    if(map[x][y].equals(".")){
		enemies.add(new Enemy(x, y, 2));
		done = true;
	    }
	}
    }
}
