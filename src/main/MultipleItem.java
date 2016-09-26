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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultipleItem that = (MultipleItem) o;

        if (weight != that.weight) return false;
        if (value != that.value) return false;
        if (amount != that.amount) return false;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + weight;
        result = 31 * result + value;
        result = 31 * result + amount;
        return result;
    }

    @Override
    public String toString() {
        return "MultipleItem{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", value=" + value +
                ", amount=" + amount +
                '}';
    }
}
