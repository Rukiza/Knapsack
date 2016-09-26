package main;

/**
 * Created by brewershan on 25/09/16.
 */
public class Item implements Comparable<Item>{
    public String name;
    public int weight;
    public int value;

    public Item (String name, int weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }


    @Override
    public int compareTo(Item o) {
        double o1 = value/weight;
        double o2 = o.value/weight;
        if (o1 > o2) {
            return 1;
        } else if (o1 == o2) {
            return 0;
        } else {
            return  - 1;
        }

    }
}
