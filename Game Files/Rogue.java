import net.slashie.libjcsi.CSIColor;
import net.slashie.libjcsi.CharKey;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import java.util.Properties;

public class Rogue{
    private ConsoleSystemInterface csi = new WSwingConsoleInterface("Rogue");
    private int x = 40, y = 12;
    
    public void run(){
	boolean running = true;
	csi.cls();
	csi.print(0, 0, "Rogue", CSIColor.RED);
	csi.saveBuffer();
	csi.print(x, y, "@", CSIColor.RED);
	
	while(running){
	    int key = csi.inkey().code;
            switch (key){
            case CharKey.UARROW:
            	y--;
            	break;
            case CharKey.DARROW:
            	y++;
            	break;
            case CharKey.LARROW:
            	x--;
            	break;
            case CharKey.RARROW:
            	x++;
            	break;
            case CharKey.ESC:
            	running = false;
            }

	    csi.restore();
	    csi.print(x, y, "@", CSIColor.RED);
	    csi.refresh();
	}
    }
    
    public static void main(String[] args){
	new Rogue().run();
    }
}
