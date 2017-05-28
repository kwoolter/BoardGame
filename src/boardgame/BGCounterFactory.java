/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author KeithW
 */
public class BGCounterFactory {

    private static ArrayList<ArrayList<BGCounter>> levels;
    private static int iCurrentLevel;

    public BGCounterFactory() {
        iCurrentLevel = 0;
    }

    static public void load() {

        levels = new ArrayList<ArrayList<BGCounter>>();

        ArrayList<BGCounter> list;

        // Level 1
        list = new ArrayList<BGCounter>();

        loadUp(list, new BGCounter("DnD", "Mage", "mage.png", 10), 10);
        loadUp(list, new BGCounter("DnD", "Dwarf", "dwarf.png", 20), 10);
        loadUp(list, new BGCounter("DnD", "Ranger", "ranger.png", 30), 10);
        loadUp(list, new BGCounter("Family", "Monty", "montyhead.png", 40), 10);
        // loadUp(list, new BGCounter("Object", "Bomb", "bomb.png", 40, true, true, false), 10);

        levels.add(list);
        list = new ArrayList<BGCounter>(list);

        // Level 2

        loadUp(list, new BGCounter("Family", "Jack", "jackhead.png", 40), 10);
        loadUp(list, new BGCounter("Object", "Bomb", "bomb.png", 40, true, true, false), 10);

        levels.add(list);
        list = new ArrayList<BGCounter>(list);

        loadUp(list, new BGCounter("Family", "Dad", "DadHead.png", 40), 10);

        levels.add(list);
        list = new ArrayList<BGCounter>(list);

        loadUp(list, new BGCounter("Family", "Rosie", "RosieHead.png", 40), 10);
        loadUp(list, new BGCounter("Object", "Boulder", "boulder.png", 40, false, false, false), 2);

        levels.add(list);

        list = new ArrayList<BGCounter>(list);
        loadUp(list, new BGCounter("Object", "Bomb", "bomb.png", 40, true, true, false), 10);
        loadUp(list, new BGCounter("Bonus", "Treasure", "treasure.png", 500), 5);
        levels.add(list);

        list = new ArrayList<BGCounter>(list);
        loadUp(list, new BGCounter("Mystery", "Mystery", "mystery.png", 10, true, true, true), 2);
        levels.add(list);

        list = new ArrayList<BGCounter>(list);
        loadUp(list, new BGCounter("Kiddie", "Waybuloo1", "waybuloo1.png", 50), 10);
        levels.add(list);

        list = new ArrayList<BGCounter>(list);
        loadUp(list, new BGCounter("Kiddie", "Waybuloo2", "waybuloo2.png", 50), 10);
        levels.add(list);

        list = new ArrayList<BGCounter>(list);
        loadUp(list, new BGCounter("Kiddie", "Waybuloo3", "waybuloo3.png", 50), 10);
        levels.add(list);


        list = new ArrayList<BGCounter>(list);
        loadUp(list, new BGCounter("Kiddie", "Waybuloo4", "waybuloo4.png", 50), 5);
        levels.add(list);

        list = new ArrayList<BGCounter>(list);
        loadUp(list, new BGCounter("Family", "Mum", "MumHead.png", 40), 5);
        levels.add(list);

        list = new ArrayList<BGCounter>(list);

        loadUp(list, new BGCounter("DnD", "Mage", "mage.png", 10), 10);
        loadUp(list, new BGCounter("DnD", "Dwarf", "dwarf.png", 20), 10);
        loadUp(list, new BGCounter("DnD", "Ranger", "ranger.png", 30), 15);
        loadUp(list, new BGCounter("Object", "Boulder", "boulder.png", 40, false, false, false), 2);
        loadUp(list, new BGCounter("Family", "Jack", "jackhead.png", 40), 10);
        loadUp(list, new BGCounter("Family", "Dad", "DadHead.png", 40), 10);
        loadUp(list, new BGCounter("Family", "Rosie", "RosieHead.png", 40), 15);
        loadUp(list, new BGCounter("Object", "Bomb", "bomb.png", 40, true, true, false), 5);

        loadUp(list, new BGCounter("Mystery", "Mystery", "mystery.png", 10, true, false, true), 2);
        loadUp(list, new BGCounter("Bonus", "Treasure", "treasure.png", 500), 2);

        levels.add(list);
        list = new ArrayList<BGCounter>(list);

        // Level 4
        list = new ArrayList<BGCounter>();
        loadUp(list, new BGCounter("DnD", "Mage", "mage.png", 10), 10);
        loadUp(list, new BGCounter("DnD", "Dwarf", "dwarf.png", 20), 10);
        loadUp(list, new BGCounter("DnD", "Ranger", "ranger.png", 30), 15);
        loadUp(list, new BGCounter("Object", "Boulder", "boulder.png", 40, false, false, false), 2);
        loadUp(list, new BGCounter("Family", "Jack", "jackhead.png", 40), 10);
        loadUp(list, new BGCounter("Family", "Dad", "DadHead.png", 40), 10);
        loadUp(list, new BGCounter("Family", "Rosie", "RosieHead.png", 40), 15);
        loadUp(list, new BGCounter("Object", "Bomb", "bomb.png", 40, true, true, false), 5);

        loadUp(list, new BGCounter("Mystery", "Mystery", "mystery.png", 10, true, false, true), 2);
        loadUp(list, new BGCounter("Bonus", "Treasure", "treasure.png", 500), 2);

        levels.add(list);

        list = new ArrayList<BGCounter>(list);

    }

    static private void loadUp(ArrayList<BGCounter> list, BGCounter counter, int iCount) {


        for (int i = 0; i < iCount; i++) {
            list.add(new BGCounter(counter));
        }
    }

    static BGCounter getRandom(int iLevel) {

        int iLevelSelected = iLevel;
        ArrayList<BGCounter> list;

        if (iLevelSelected > levels.size()) {

            iLevelSelected = levels.size();

        }

        Random randomGenerator = new Random();
        list = levels.get(iLevelSelected - 1);

        return new BGCounter(list.get(randomGenerator.nextInt(list.size())));

    }
}
