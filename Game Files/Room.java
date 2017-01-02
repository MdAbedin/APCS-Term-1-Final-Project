import net.slashie.libjcsi.CSIColor;
import java.util.Random;

public class Room{
    public int x, y;
    public int xln, yln;
    public int centerX, centerY;
    public int section;

    public Room(int x, int y, int xln, int yln){
	this.x = x;
	this.y = y;
	this.xln = xln;
	this.yln = yln;
	setCenters();
	setSection();
    }

    public void setCenters(){
	this.centerX = x + xln/2;
	this.centerY = y + yln/2;
    }
    
    public void setSection(){
	section = centerX / 26 + 3 * (centerY / 7);
    }

    public boolean canConnect(Room other){
	boolean b;

	if(section / 3 == other.section / 3){
	    b = other.section == section + 1 || other.section == section - 1 || other.section == section;
	}
	else{
	    b = other.section == section + 3 || other.section == section - 3;
	}
	
	return b;
    }

    public int[] pickExit(Room other, Random rng){
	//optimize
	int left = x - (other.x + other.xln - 1);
	int right = other.x - (x + xln - 1);
	int up = y - (other.y + other.yln - 1);
	int down = other.y - (y + yln - 1);
	int[] p = new int[2];

	int h = Math.max(left, right);
	int v = Math.max(up, down);

	if((left > 0 || right > 0) && (up > 0 || down > 0)){
	    if(right < v && right > 0){
		p[0] = x + xln - 1;
		p[1] = rng.nextInt(yln - 2) + 1 + y;
	    }
	    if(left < v && left > 0){
		p[0] = x;
		p[1] = rng.nextInt(yln - 2) + 1 + y;
	    }
	    if(up < h && up > 0){
		p[0] = rng.nextInt(xln - 2) + 1 + x;
		p[1] = y;
	    }
	    if(down < h && down > 0){
		p[0] = rng.nextInt(xln - 2) + 1 + x;
		p[1] = y + yln - 1;
	    }
	}
	else{
	    if(right > 0){
		p[0] = x + xln - 1;
		p[1] = rng.nextInt(yln - 2) + 1 + y;
	    }
	    if(left > 0){
		p[0] = x;
		p[1] = rng.nextInt(yln - 2) + 1 + y;
	    }
	    if(up > 0){
		p[0] = rng.nextInt(xln - 2) + 1 + x;
		p[1] = y;
	    }
	    if(down > 0){
		p[0] = rng.nextInt(xln - 2) + 1 + x;
		p[1] = y + yln - 1;
	    }
	}
	
	return p;
    }
}
