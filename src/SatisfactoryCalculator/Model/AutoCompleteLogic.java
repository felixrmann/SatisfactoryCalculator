package SatisfactoryCalculator.Model;

import SatisfactoryCalculator.Service.ItemService;

import java.util.Collections;
import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2021-Januar-12
 */

public class AutoCompleteLogic {
    private static Vector<Item> allItem;
    private static Vector<String> allItemNames;

    public AutoCompleteLogic(){
        allItem = ItemService.getAllItem();
        allItemNames = new Vector<>();
        for (Item item : allItem){
            allItemNames.add(item.getItemName());
        }
        Collections.sort(allItemNames);
    }

    public Vector<String> getAllSuggestions(String input){
        if (input != null){
            Vector<String> outVector = new Vector<>();

            for (String s : allItemNames){
                if (s.startsWith(input)) outVector.add(s);
            }

            Collections.sort(outVector);
            return outVector;
        } else {
            return allItemNames;
        }
    }

    public Vector<String> getAllItemNames(){
        return allItemNames;
    }
}
