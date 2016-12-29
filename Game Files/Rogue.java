import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import java.util.Properties;
import java.util.Random;

public class Rogue{
    public ConsoleSystemInterface csi = new WSwingConsoleInterface("Rogue by Md Abedin and Othman Bichouna");
    public Player p = new Player(40, 11);
    public Floor floor = new Floor();
    public boolean running;
    public Random rng = new Random();

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
	stats += " Ac: ";
	stats += p.ac;
	stats += " Exp: ";
	stats += p.exp;
	stats += "/";
	stats += p.maxexp;
	stats += " Bumps: ";
	stats += p.bumps;
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
    
    public void run(){
	running = true;
	csi.cls();
	printFloor();
	csi.saveBuffer();
	printPlayer();
	
	while(running){
	    int key = csi.inkey().code;
	    p.act(key, floor.map);
	    updateScreen();
	}
    }

    public static void main(String[] args){
	new Rogue().run();
    }
}
