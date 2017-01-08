import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import java.util.Random;

public class Rogue{
    public ConsoleSystemInterface csi = new WSwingConsoleInterface("Rogue by Md Abedin and Othman Bichouna");
    public Floor floor = new Floor();
    public Player p = new Player(floor.rooms.get(0).centerX, floor.rooms.get(0).centerY);

    public void initialize(){
	csi.cls();
	printFloor();
	csi.saveBuffer();
	printPlayer();
	printStats();
	csi.refresh();
    }

    public void printFloor(){
	for(int x = 0; x < floor.map.length; x++){
	    for(int y = 0; y < floor.map[0].length; y++){
		csi.print(x, y + 1, floor.map[x][y]);
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
	stats += " X: ";
	stats += p.x;
	stats += " Y: ";
	stats += p.y;
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
	printMessage();
	printStats();
	printPlayer();
	csi.refresh();
    }

    public boolean onStairs(){
	return p.x == floor.stairsX && p.y == floor.stairsY;
    }

    public void newFloor(){
	floor = new Floor();
	p = new Player(floor.rooms.get(0).centerX, floor.rooms.get(0).centerY);
	csi.cls();
	initialize();
    }

    public void run(){
	initialize();
	while(true){
	    p.act(csi.inkey().code, floor.map);
	    updateScreen();
	    if(onStairs()){
		newFloor();
	    }
	}
    }

    public static void main(String[] args){
	Rogue r = new Rogue();
	//r.floor.itemGeneration(r.p);
	r.run();
    }
}
