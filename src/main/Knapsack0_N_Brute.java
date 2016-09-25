package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by brewershan on 25/09/16.
 */
public class Knapsack0_N_Brute {

    public MultipleItem[] knapsack(MultipleItem[] items, int sackWeight) {
        if (items.length == 0) {
            return new MultipleItem[0];
        }
        List<Item> newItems = new ArrayList<>();
        for (MultipleItem i : items) {
            i.toSingle().forEach(newItems::add);
        }

        newItems = knapsack_recursive(newItems, sackWeight, new ArrayList<>());

        List<MultipleItem> l = new ArrayList<>();

        Map<String, List<Item>> map = new HashMap<>();
        for (Item i : newItems) {
            String name = i.name.substring(0, i.name.indexOf('_'));
            if (map.containsKey(name)) {
                map.get(name).add(i);
            } else {
                List<Item> temp = new ArrayList<>();
                temp.add(i);
                map.put(name, temp);
            }
        }

        l.addAll(map.values().stream().map(MultipleItem::new).collect(Collectors.toList()));

        return l.toArray(new MultipleItem[l.size()]);
    }

    public List<Item> knapsack_recursive(List<Item> items, int weight, List<Item> solution) {
        int w = weight(solution);
        List<Item> l = new ArrayList<>(solution);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).weight + w <=  weight) {
                List<Item> temp1 = new ArrayList<>(items);
                List<Item> temp2 = new ArrayList<>(solution);
                temp1.remove(i);
                temp2.add(items.get(i));
                l = max(
                        knapsack_recursive(
                                temp1,
                                weight,
                                temp2
                        ),
                        l
                );

            }
        }
        return l;
    }

    private int weight(List<Item> solution) {
        int i = 0;
        for (Item item: solution) {
            i = i + item.weight;
        }
        return i;
    }

    private List<Item> max(List<Item> items, List<Item> l) {
        return values(items) > values(l) ? items : l;
    }

    private int values(List<Item> solution) {
        int i = 0;
        for (Item item: solution) {
            i = i + item.value;
        }
        return i;
    }

    public static void main(String[] arg) {
        Knapsack0_N_Brute k = new Knapsack0_N_Brute();
        MultipleItem[] items =  new MultipleItem[] {new MultipleItem("A", 1,2,2), new MultipleItem("B",3,2,1),
                new MultipleItem("C", 2,7,3), new MultipleItem("D", 2,2,3),
                new MultipleItem("E", 2,4,1),new MultipleItem("F", 4,2,2)};
        items = k.knapsack(items, 9);
        int v = 0;
        for (MultipleItem i: items) {
            System.out.println(i.name + "  :  " + i.amount);
            v = v + i.value*i.amount;
        }
        System.out.println("Total : "+v);
    }
}
