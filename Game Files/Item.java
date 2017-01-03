import net.slashie.libjcsi.CSIColor;
import java.util.Random;

public class Item{
  public int x, y;
  public boolean pickedUp;
  public String name;
  public int stats, secondStat;
  public String type;
  public Random rng = new Random();
  public String rarity;
  public double raritybonus = 1.0;
  public String[] adjectives = {"useless","steep","faint","stereotyped","cheerful","worthless","parallel","jazzy",
  "filthy","torpid","dead","wry","unique","draconic","rhetorical","stupid","jealous","victorious","ruined",
  "magnificent","ambiguous","vengeful","nappy","deadly","striped","wise","quizzical","miniature","well-off",
  "imperfect","flaming","somber","evasive","valuable","alluring","black-and-white","fearful","drab","squeamish",
  "ludicrous","massive","fortunate","uttermost","lethal","maniacal","curious","magical","tested","ancient","handy",
  "godly","historical","hideous","tight","dull","enchanted","ordinary","golden","mithril","great","apathetic","stale",
  "zealous","threatening","skinny","holistic","aquatic","premium","incredible","old","handsome","ashamed","present",
  "animated","blushing","repulsive","scary","needless","nostalgic","possessive","righteous","malicious","furtive",
  "abject","keen","fabulous","caring","kindhearted","petite","voracious","evil","low","clumsy","pumped","wet",
  "rightful","soggy","rambunctious","rapid","spotty"};
  public String[] weapons = {"sword","long sword","broad sword","spear","mace","axe","halberd","lance","javelin","daggers"};
  public String[] armor = {"chest","chestplate","jerkin","shirt","robe"};
  public String[] hands = {"gauntlets","gloves"};
  public String[] feet = {"shoes","greaves","boots","sandals","slippers"};
  public String[] food = {"apple","cabbage","potion","meat stick","turkey","potato","cheese stick","onion"}
  public String[] jewel = {"diamond","ruby","emerald","sapphire","topaz","pearl","garnet","opal"}
  public Item(Player p){
    int temp = rng.nextInt(7);
    if (temp == 0){
      //weapons: stat increase to str
      type = "weapon";
      rarity = rare(p);
      name = adjective[rng.nextInt(100)] + " " + weapons[rng.nextInt(10)];
      stats = (int)((rng.nextInt(3) * p.level + 1) * raritybonus);
      pickedUp = false;
    }else if (temp == 1){
      //chest, hands, feet: add to ar
      type = "chest";
      rarity = rare(p);
      name = adjective[rng.nextInt(100)] + " " + armor[rng.nextInt(5)];
      stats = (int)((rng.nextInt(4) * p.level + 1) * raritybonus);
      pickedUp = false;
    }else if (temp == 2){
      type = "hands";
      rarity = rare(p);
      name = adjective[rng.nextInt(100)] + " " + hands[rng.nextInt(2)];
      stats = (int)((rng.nextInt(2) * p.level + 1) * raritybonus);
      pickedUp = false;
    }else if (temp == 3){
      type = "feet";
      rarity = rare(p);
      name = adjective[rng.nextInt(100)] + " " + hands[rng.nextInt(5)];
      stats = (int)((rng.nextInt(3) * p.level + 1) * raritybonus);
      pickedUp = false;
    }else if (temp == 4){
      //food: the stat should be addition to health when used
      type = "food";
      name = adjective[rng.nextInt(100)] + " " + food[rng.nextInt(8)];
      stats = rng.nextInt(10) + 5 + p.level;
      pickedUp = false;
    }else if (temp == 5){
      type = "ring";
      rarity = rare(p);
      name = adjective[rng.nextInt(100)] + jewel[rng.nextInt(8)] + " ring";
      //rings and amulets add to both str and ar
      stats = (int)((rng.nextInt(3) * p.level + 1) * raritybonus);
      secondStat = (int)((rng.nextInt(2) * p.level + 1) * raritybonus);
      pickedUp = false;
    }else if (temp == 6){
      type = "amulet";
      rarity = rare(p);
      name = adjective[rng.nextInt(100)] + jewel[rng.nextInt(8)] + " amulet";
      stats = (int)((rng.nextInt(4) * p.level + 1) * raritybonus);
      secondStat = (int)((rng.nextInt(3) * p.level + 1) * raritybonus);
      pickedUp = false;
  }

  public String rare(Player p){
    if (rng.nextInt(11) - p.level < 0){
      raritybonus = 1.5;
      return "rare";
    }
    if (rng.nextInt(21) - p.level < 0){
      raritybonus = 2.0;
      return "epic";
    }
    if (rng.nextInt(51) - p.level < 0){
      raritybonus = 3.0;
      return "legendary";
    }
}
