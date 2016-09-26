package main;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable{
        public List<MultipleItem> list;
        public int totalValue;
        public int totalWeight;
        public int potency = 0;

        public Node (List<MultipleItem> list, int totalWeight, int totalValue) {
            this.list = new ArrayList<>(list);
            this.totalWeight = totalWeight;
            this.totalValue = totalValue;
        }

        public Node (List<MultipleItem> list, int totalWeight, int totalValue, MultipleItem update) {
            this.list = new ArrayList<>(list);
            this.totalWeight = totalWeight;
            this.totalValue = totalValue;
            list.stream().filter(i -> i.name.equals(update.name)).forEach(i -> i.amount = update.amount);
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (totalValue != node.totalValue) return false;
        if (totalWeight != node.totalWeight) return false;
        return list.equals(node.list);

    }

    @Override
    public int hashCode() {
        int result = list.hashCode();
        result = 31 * result + totalValue;
        result = 31 * result + totalWeight;
        return result;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Node) {
            Node node = (Node) o;
            return node.potency - potency;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Node{" +
                "list=" + list +
                ", totalValue=" + totalValue +
                ", totalWeight=" + totalWeight +
                ", potency=" + potency +
                '}';
    }
}