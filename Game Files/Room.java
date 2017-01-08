import java.util.Random;
import java.util.ArrayList;

public class Room{
    public int x, y;
    public int xln, yln;
    public int centerX, centerY;
    public int section;
    public ArrayList<Integer> connectedRooms = new ArrayList<Integer>();

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
	    b = other.section == section + 1 || other.section == section - 1;
	}
	else{
	    b = other.section == section + 3 || other.section == section - 3;
	}

	b = !connectedRooms.contains(other.section) && b;
	
	return b;
    }

    public int[] pickExit(Room other, Random rng){
	int[] exit = new int[2];
	int left = x - (other.x + other.xln - 1);
	int right = other.x - (x + xln - 1);
	int up = y - (other.y + other.yln - 1);
	int down = other.y - (y + yln - 1);
	
	if(right > 0){
	    exit[0] = x + xln - 1;
	    exit[1] = centerY;
	}
	if(left > 0){
	    exit[0] = x;
	    exit[1] = centerY;
	}
	if(up > 0){
	    exit[0] = centerX;
	    exit[1] = y;
	}
	if(down > 0){
	    exit[0] = centerX;
	    exit[1] = y + yln - 1;
	}

	return exit;
    }
}
