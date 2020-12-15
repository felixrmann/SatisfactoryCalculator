package SatisfactoryCalculator.Model;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-07
 */

public class Item {

    private String itemUUID;
    private String itemName;

    public Item(){}

    public Item(String itemUUID, String itemName){
        this.itemUUID = itemUUID;
        this.itemName = itemName;
    }

    public String getItemUUID() {
        return itemUUID;
    }

    public void setItemUUID(String materialUUID) {
        this.itemUUID = materialUUID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String toString(){
        return "Item: \n" +
                "UUID: " + itemUUID + "\n" +
                "Name: " + itemName;
    }
}
