package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by brewershan on 25/09/16.
 */
public class Knapsack0_N_Dynamic {

    public MultipleItem[] knapsack(MultipleItem[] items, int sackWeight) {
        if (items.length == 0) {
            return new MultipleItem[0];
        }
        List<Item> newItems = new ArrayList<>();
        for (MultipleItem i : items) {
            i.toSingle().forEach(newItems::add);
        }

        D[][] cache = new D[newItems.size()][sackWeight+1];

        cache = init(cache, newItems.toArray(new Item[newItems.size()]));

        for (int i = 1; i < cache.length; i++) {
            for (int j = 0; j < cache[0].length; j++) {
                D v1 = cache[i-1][j];
                D v2 = new D(0, new ArrayList<>());
                //System.out.println();
                //print(cache);
                //System.out.println();
                if (j - newItems.get(i).weight >= 0) {
                    D temp = cache[i-1][j - newItems.get(i).weight];
                    Item item = newItems.get(i);
                    List<Item> it = new ArrayList<>(temp.list);
                    it.add(item);
                    v2 = new D(temp.total + item.value, it);
                }

                cache[i][j] = max(v1, v2);
            }
        }
        //print(cache);
        newItems = cache[cache.length-1][cache[cache.length-1].length-1].list;
        List<MultipleItem> l = new ArrayList<>();

        Map<String, List<Item>> map = new HashMap<>();
        for (Item i : newItems) {
            //System.out.println("Cats");
            String name = i.name.substring(0, i.name.indexOf('_'));
            if (map.containsKey(name)) {
                map.get(name).add(i);
            } else {
                List<Item> temp = new ArrayList<>();
                temp.add(i);
                map.put(name, temp);
            }
        }
        //print(cache);
        l.addAll(map.values().stream().map(MultipleItem::new).collect(Collectors.toList()));

        return l.toArray(new MultipleItem[l.size()]);
    }

    private D[][] init(D[][] cache, Item[] items) {
        for (int i = 0; cache.length > 0 && i < cache[0].length; i++) {
            D v1 = new D(0, new ArrayList<>());
            if (items[0].weight <= i) {
                //System.out.println("Should be here");
                List<Item> it = new ArrayList<>();
                it.add(items[0]);
                v1 = new D(items[0].value, it);
            }
            cache[0][i] = v1;
        }
        return cache;
    }

    private void print(D[][] cache) {
        for (int i = 0; i < cache.length; i++) {
            for (int j = 0; j < cache[0].length; j++) {
                System.out.print(cache[i][j] != null ? "\t"+cache[i][j].total+"\t" : "\tN\t");
            }
            System.out.println();
        }
    }

    private D max(D d1, D d2) {
        return d1.total > d2.total ? d1 : d2;
    }

    public class D  {

        public int total;
        public List<Item> list;

        public D (int total, List<Item> list)  {
            this.total = total;
            this.list = list;
        }
    }

    public static void main(String[] arg) {
        Knapsack0_N_Dynamic k = new Knapsack0_N_Dynamic();
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
