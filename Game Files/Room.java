import net.slashie.libjcsi.CSIColor;
import java.util.Random;

public class Room{
  public int xLength;
  public int yLength;
  public int topLeftx;
  public int topLefty;

/**The Room object will hold the important information for the Room so
 * that it can be connected to other rooms.
 */
  public Room(int xLength, int yLength; int topLeftx, topLefty){
    this.xLength = xLength;
    this.yLength = yLength;
    this.topLeftx = topLeftx;
    this.topLefty = topLefty;
  }
}
