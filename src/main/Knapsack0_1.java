package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brewershan on 18/09/16.
 */
public class Knapsack0_1 {

    public Item[] knapsack(Item[] items, int sackWeight) {
        if (items.length == 0) {
            return new Item[0];
        }
        D[][] cache = new D[items.length][sackWeight+1];

        cache = init(cache, items);

        for (int i = 1; i < cache.length; i++) {
            for (int j = 0; j < cache[0].length; j++) {
                D v1 = cache[i-1][j];
                D v2 = new D(0, new ArrayList<>());
                //System.out.println();
                //print(cache);
                //System.out.println();
                if (j - items[i].weight >= 0) {
                    D temp = cache[i-1][j - items[i].weight];
                    Item item = items[i];
                    List<Item> it = new ArrayList<>(temp.list);
                    it.add(item);
                    v2 = new D(temp.total + item.value, it);
                }

                cache[i][j] = max(v1, v2);
            }
        }
        //print(cache);
        List<Item> i = cache[items.length-1][cache[items.length-1].length-1].list;
        return i.toArray(new Item[i.size()]);
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
        Knapsack0_1 k = new Knapsack0_1();
        Item[] items =  new Item[] {new Item("A", 1,2), new Item("B",3,2), new Item("C", 2,7), new Item("D", 2,2),
                new Item("E", 2,4),new Item("F", 4,2)};
        items = k.knapsack(items, 7);
        for (Item i: items) {
            System.out.println(i.name);
        }
    }
}
