import net.slashie.libjcsi.CSIColor;
import java.util.Random;

public class Path{
  public int exitx;
  public int exity;
  public int startx;
  public int starty;
  public Random rng = new Random();

  // Old code, may be used if the while loops in the path don't work
  //int temp = rng.nextInt(xLength);
  //if (temp == 0){
  //  temp++;
  //}else if(temp == xLength){
  //  temp--;
  //}


  public Path(Room room){
    int side = rng.nextInt(3);
    if (side == 0){
      int temp = rng.nextInt(xLength);
      while (temp == 0 || temp == xLength){
        temp = rng.nextInt(xLength);
      }
      startx = room.topLeftx + temp;
      starty = room.topLefty;
    }else if(side == 1){
      int temp = rng.nextInt(yLength);
      while (temp == 0 || temp == yLength){
        temp = rng.nextInt(yLength);
      }
      startx = room.topLeftx + room.xLength - 1;
      starty = room.topLefty + rng.nextInt(yLength);
    }else if (side == 2){
      while (temp == 0 || temp == xLength){
        temp = rng.nextInt(xLength);
      }
      startx = room.topLeftx + temp;
      starty = room.topLefty + room.yLength - 1;
    }else if(side == 3){
      int temp = rng.nextInt(yLength);
      while (temp == 0 || temp == yLength){
        temp = rng.nextInt(yLength);
      }
      startx = room.topLeftx;
      starty = room.topLefty + temp;
  }
}
