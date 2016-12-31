import net.slashie.libjcsi.CSIColor;
import java.util.Random;

public class Path{
  public int exitx;
  public int exity;
  public int startx;
  public int starty;
  public Random rng = new Random();

  // Old code, used as a reference
  //int temp = rng.nextInt(xLength);
  //if (temp == 0){
  //  temp++;
  //}else if(temp == xLength){
  //  temp--;
  //}

  //int temp = rng.nextInt(xLength);
  //while (temp == 0 || temp == xLength){
    //temp = rng.nextInt(xLength);
    //}


  public Path(Room room){
    int side = rng.nextInt(4);
    // Key: 0 = Top, 1 = Right, 2 = Bottom, 3 = Left
    if (side == 0){
      startx = room.topLeftx + rng.nextInt(xlength - 1) + 1;
      starty = room.topLefty;
    }else if(side == 1){
      startx = room.topLeftx + room.xLength - 1;
      starty = room.topLefty + rng.nextInt(yLength - 1) + 1;
    }else if (side == 2){
      startx = room.topLeftx + rng.nextInt(xLength - 1) + 1;
      starty = room.topLefty + room.yLength - 1;
    }else if(side == 3){
      startx = room.topLeftx;
      starty = room.topLefty + rng.nextInt(yLength - 1) + 1;
  }
}
