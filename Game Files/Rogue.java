import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import java.util.Random;
import java.util.ArrayList;

public class Rogue{
    public ConsoleSystemInterface csi = new WSwingConsoleInterface("Rogue by Md Abedin and Othman Bichouna");
    public ArrayList<Floor> floors = new ArrayList<Floor>();
    public int currentFloor = 0;
    public Floor floor;
    public Player p;
    public Room currentRoom;
    public int totalFloors = 3;
    public boolean running = true;

    public Rogue(){
	for(int i = 0; i < totalFloors; i++){
	    floors.add(new Floor(i, totalFloors));
	}

	floor = floors.get(currentFloor);
	p = new Player(floor.rooms.get(0).centerX, floor.rooms.get(0).centerY);
	currentRoom = floor.rooms.get(0);
	initialize();
    }

    public void initialize(){
	csi.cls();
	updateFloor();
	printFloor();
	csi.saveBuffer();
	printPlayer();
	printStats();
	csi.refresh();
    }

    public void printFloor(){
	for(Room r: floors.get(currentFloor).rooms){
	    printRoom(r);
	}

	if(floors.get(currentFloor).map[p.x+1][p.y] == "#" || floors.get(currentFloor).map[p.x+1][p.y] == "+"){
	    csi.print(p.x + 1, p.y + 1, floors.get(currentFloor).map[p.x+1][p.y]);
	}
	if(floors.get(currentFloor).map[p.x-1][p.y] == "#" || floors.get(currentFloor).map[p.x-1][p.y] == "+"){
	    csi.print(p.x - 1, p.y + 1, floors.get(currentFloor).map[p.x-1][p.y]);
	}
	if(floors.get(currentFloor).map[p.x][p.y+1] == "#" || floors.get(currentFloor).map[p.x][p.y+1] == "+"){
	    csi.print(p.x, p.y + 2, floors.get(currentFloor).map[p.x][p.y+1]);
	}
	if(floors.get(currentFloor).map[p.x][p.y-1] == "#" || floors.get(currentFloor).map[p.x][p.y-1] == "+"){
	    csi.print(p.x, p.y, floors.get(currentFloor).map[p.x][p.y-1]);
	}
    }

    public void printRoom(Room r){
	if(r.discovered){
	    if(r.isInside){
		for(int x = r.x; x < r.x + r.xln; x++){
		    for(int y = r.y; y < r.y + r.yln; y++){
			csi.print(x, y+1, floors.get(currentFloor).map[x][y]);
			if(floor.dynamicMap[x][y].equals("E")){
			    csi.print(x, y+1, "E", CSIColor.RED);
			}
		    }
		}
	    }
	    else{
		for(int x = r.x; x < r.x + r.xln; x++){
		    for(int y = r.y; y < r.y + r.yln; y++){
			if(x == r.x || x == (r.x+r.xln-1) || y == r.y || y == (r.y+r.yln-1)){
			    csi.print(x, y+1, floors.get(currentFloor).map[x][y]);
			}
			else{
			    csi.print(x, y+1, " ");
			}
		    }
		}
	    }
	}
    }

    public void printStats(){
	String stats = "Level: ";
	stats += p.level;
	stats += " Gold: ";
	stats += p.gold;
	stats += " Hp: ";
	stats += p.hp;
	stats += "/";
	stats += p.maxhp;
	stats += " Str: ";
	stats += p.str;
	stats += " Ar: ";
	stats += p.ar;
	stats += " Exp: ";
	stats += p.exp;
	stats += "/";
	stats += p.maxexp;
	stats += " Floor: ";
	stats += currentFloor;
	csi.print(0, 23, stats, CSIColor.MAGENTA);
    }

    public void printMessage(){
	csi.print(0, 0, p.message, CSIColor.MAGENTA);
    }

    public void printPlayer(){
	csi.print(p.x, p.y + 1, "@", CSIColor.BRIGHT_GREEN);
    }

    public void updateScreen(){
	csi.cls();
	csi.restore();
	printFloor();
	csi.saveBuffer();
	printMessage();
	printStats();
	printPlayer();
	csi.refresh();
    }

    public boolean onStairs(){
	return p.x == floor.stairsX && p.y == floor.stairsY;
    }

    public boolean onAmulet(){
	return p.x == floor.amuletX && p.y == floor.amuletY;
    }

    public void changeFloor(int index){
	if(index >= 0 && index < totalFloors){
	    currentFloor = index;
	    floor = floors.get(currentFloor);
	    p.x = floor.rooms.get(0).centerX;
	    p.y = floor.rooms.get(0).centerY;
	    initialize();
	}
    }

    public void updateFloor(){
	for(int i = 0; i < floor.rooms.size(); i++){
	    Room room = floor.rooms.get(i);
	    room.isInside = room.isInside(p);

	    if(!room.discovered){
		room.discovered = room.isInside(p);
	    }
	    if(room.isInside){
		currentRoom = room;
	    }
	}
    }

    public void moveEnemies(){
	for(int i = 0; i < floor.enemies.size(); i++){
	    floor.enemies.get(i).act(floor.map, floor.dynamicMap, p, currentRoom);
	}
    }

    public void winGame(){
	csi.cls();
	csi.print(36, 12, "YOU WIN", CSIColor.YELLOW);
	csi.refresh();
    }

    public void loseGame(){
	csi.cls();
	csi.print(36, 12, "YOU DIED", CSIColor.RED);
	csi.refresh();
    }
    
    public void run(){
	while(running){
	    int key = csi.inkey().code;
	    p.act(key, floor.map, floor.dynamicMap, floor.enemies);
	    if(!p.hasAmulet && currentFloor + 1 == totalFloors && onAmulet()){
		p.hasAmulet = true;
		floor.removeAmulet();
	    }
	    
	    if(onStairs()){
		if(key == CharKey.MORETHAN){
		    floor.dynamicMap[p.x][p.y] = " ";
		    changeFloor(currentFloor + 1);
		}
		else if(key == CharKey.LESSTHAN){
		    if(p.hasAmulet && currentFloor == 0){
			winGame();
			running = false;
			break;
		    }
		    else{
			floor.dynamicMap[p.x][p.y] = " ";
			changeFloor(currentFloor - 1);
		    }
		}
	    }
	    moveEnemies();
	    if(p.hp <= 0){
		loseGame();
		running = false;
		break;
	    }
	    updateFloor();
	    updateScreen();
	    p.moved = false;
	}

    }
    
    public static void main(String[] args){
	Rogue r = new Rogue();
	//r.floors.get(r.currentFloor).itemGeneration(r.p);
	r.run();
    }
}
