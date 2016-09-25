package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brewershan on 25/09/16.
 */
public class MultipleItem {
    public String name;
    public int weight;
    public int value;
    public int amount;

    public MultipleItem (String name, int weight, int value, int amount) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.amount = amount;
    }

    public MultipleItem (List<Item> items) {
        for (Item i: items) {
            name = i.name.substring(0, i.name.indexOf('_'));
            weight = i.weight;
            value = i.value;
        }
        amount = items.size();
    }

    public List<Item> toSingle() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            items.add(new Item(name+"_"+i,weight,value));
        }
        return items;
    }
}
