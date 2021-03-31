package SatisfactoryCalculator.Model;

import SatisfactoryCalculator.Service.BuildingService;
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
    private static Vector<Building> allBuilding;
    private static Vector<String> allBuildingNames;

    public AutoCompleteLogic(){
        allItem = ItemService.getAllItem();
        allBuilding = BuildingService.getAllBuilding();

        allItemNames = new Vector<>();
        allBuildingNames = new Vector<>();

        for (Item item : allItem){
            allItemNames.add(item.getItemName());
        }
        Collections.sort(allItemNames);

        for (Building building : allBuilding){
            allBuildingNames.add(building.getBuildingName());
        }
        Collections.sort(allBuildingNames);
    }

    public Vector<String> getAllItemSuggestions(String input){
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

    public Vector<String> getAllBuildingSuggestions(String input){
        if (input != null){
            Vector<String> outVector = new Vector<>();

            for (String s : allBuildingNames){
                if (s.startsWith(input)) outVector.add(s);
            }

            Collections.sort(outVector);
            return outVector;
        } else {
            return allBuildingNames;
        }
    }

    public Vector<String> getAllItemNames(){
        return allItemNames;
    }

    public Vector<String> getAllBuildingNames() {
        return allBuildingNames;
    }
}
