import net.slashie.libjcsi.CSIColor;
import java.util.Random;

public class Room{
    public int x, y;
    public int xln, yln;
    public int centerX, centerY;

    public Room(int x, int y, int xln, int yln){
	this.x = x;
	this.y = y;
	this.xln = xln;
	this.yln = yln;
	setCenters();
    }

    public void setCenters(){
	this.centerX = x + xln/2;
	this.centerY = y + yln/2;
    }
}
