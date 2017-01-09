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
    public Player p;
    public int currentRoom = 0;
    public int totalFloors = 10;

    public Rogue(){
	floors.add(new Floor(1, totalFloors));
	p = new Player(floors.get(currentFloor).rooms.get(0).centerX, floors.get(currentFloor).rooms.get(0).centerY);
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
	csi.print(p.x, p.y + 1, floors.get(currentFloor).map[p.x][p.y]);
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
	stats += " Bumps: ";
	stats += p.bumps;
	stats += " Floor: ";
	stats += currentFloor + 1;
	stats += " Room: ";
	stats += currentRoom;
	csi.print(0, 24, stats, CSIColor.MAGENTA);
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
	return p.x == floors.get(currentFloor).stairsX && p.y == floors.get(currentFloor).stairsY;
    }
    
    public void newFloor(){
	floors.add(new Floor(currentFloor + 1, totalFloors));
	currentFloor++;
	p.x = floors.get(currentFloor).rooms.get(0).centerX;
	p.y = floors.get(currentFloor).rooms.get(0).centerY;
	initialize();
    }

    public void updateFloor(){
	for(int i = 0; i < floors.get(currentFloor).rooms.size(); i++){
	    Room room = floors.get(currentFloor).rooms.get(i);
	    if(!room.discovered){
		room.discovered = room.isInside(p);
	    }
	    room.isInside = room.isInside(p);
	    if(room.isInside(p)){
		currentRoom = i;
	    }
	}
    }
    
    public void run(){
	initialize();
	while(true){
	    int key = csi.inkey().code;
	    p.act(key, floors.get(currentFloor).map);
	    updateFloor();
	    if(onStairs()){
		if(key == CharKey.M && currentFloor != totalFloors - 1){
		    newFloor();
		}
		else if(key == CharKey.N){
		    currentFloor--;
		    p.x = floors.get(currentFloor).rooms.get(0).centerX;
		    p.y = floors.get(currentFloor).rooms.get(0).centerY;
		    csi.cls();
		    updateFloor();
		    printFloor();
		    csi.saveBuffer();
		    printPlayer();
		    printStats();
		    csi.refresh();
		}
	    }
	    updateScreen();
	}
    }

    public static void main(String[] args){
	Rogue r = new Rogue();
	//r.floor.itemGeneration(r.p);
	r.run();
    }
}
