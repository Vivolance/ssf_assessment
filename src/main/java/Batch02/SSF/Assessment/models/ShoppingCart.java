package Batch02.SSF.Assessment.models;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart {
    public ArrayList<Item> items;
    public HashMap<String,Item> itemsHashMap;

    public ShoppingCart() {
        this.items = new ArrayList<Item>();
        this.itemsHashMap = new HashMap<String, Item>();
    }

    public static ArrayList<Item> updateList(HashMap<String,Item> itemsHashMap) {
        ArrayList<Item> itemList = new ArrayList<Item>();
        itemList.addAll(itemsHashMap.values());
        return itemList;
    }

    public void addItem(Item item) {
        String itemName = item.getName().toLowerCase();
        if (this.itemsHashMap.containsKey(itemName)) {
            Item currItem = this.itemsHashMap.get(itemName);
            currItem.addQuantity(item.getQuantity());
        } else {
            this.itemsHashMap.put(itemName, item);
        }
    
        this.items = updateList(this.itemsHashMap);
    }
}