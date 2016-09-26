package main;

import java.util.*;

/**
 * Created by brewershan on 26/09/16.
 */
public class Knapsack0_N_Graph {

    public MultipleItem[] knapsack(MultipleItem[] items, int sackWeight) {
        List<MultipleItem> list = toGraph(items, sackWeight).list;

        return list.toArray(new MultipleItem[list.size()]);
    }

    public Node toGraph(MultipleItem[] m, int sackWeight) {

        Set<Node> checked = new HashSet<>();
        List<MultipleItem> items = new ArrayList<>();
        //Init the base of the tree.
        for (int i = 0; i < m.length; i++) {
            items.add(new MultipleItem(m[i].name, m[i].weight, m[i].value,  0));
        }
        Node baseNode = new Node(items, 0, 0);
        Node bestNode = baseNode;
        //System.out.println(baseNode);
        Stack<Node> stack = new Stack<>();
        stack.push(baseNode);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            //System.out.println(node);
            if (checked.contains(node) || node.potency < bestNode.totalValue) {
                continue;
            }
            checked.add(node);
            Queue<Node> toBeAdded = new PriorityQueue<>();
            //Makes a new set of nodes for each type of node.
            for (int i = 0; i < node.list.size(); i++) {
                //Makes a node only if there is items available to do so.
                if (node.list.get(i).amount + 1 <= m[i].amount) {
                    List<MultipleItem> temp = new ArrayList<>();
                    for (int j = 0; j < node.list.size(); j++ ) {
                        MultipleItem g = node.list.get(j);
                        temp.add(new MultipleItem(g.name, g.weight, g.value, g.amount));
                    }

                    temp.get(i).amount = node.list.get(i).amount + 1;
                    Node tempNode = new Node(temp, node.totalWeight+m[i].weight,
                            node.totalValue+m[i].value);
                    //Ensures duplicate nodes don't get run again.
                    // Also ensures weight stays correct
                    if (!checked.contains(tempNode) && tempNode.totalWeight <= sackWeight) {
                        tempNode.potency = greedyCheck(tempNode, m, sackWeight);
                        //If the potency is greater than the current best weight then add it to list to be checked.
                        if (tempNode.potency >= bestNode.totalValue) {
                            // Check if there is a better best node.
                            if (tempNode.totalValue > bestNode.totalValue) {
                                bestNode = tempNode;
                            }
                            //System.out.println("Making it here");
                            toBeAdded.offer(tempNode);
                        }
                    }

                }
                while (!toBeAdded.isEmpty()) {
                    stack.push(toBeAdded.poll());
                }
            }
        }
        //System.out.println(bestNode);
        return bestNode;
    }



    public int greedyCheck(Node node, MultipleItem[] m, int sackWeight) {
        List<MultipleItem> tempList = new ArrayList<>();
        for (int i = 0; i < m.length; i++) {
            tempList.add(new MultipleItem(m[i].name,
                    m[i].weight,
                    m[i].value,
                    m[i].amount - node.list.get(i).amount));
        }
        Queue<Item> q = new PriorityQueue<>();
        for (MultipleItem i : m) {
            i.toSingle().forEach(q::offer);
        }
        int availableWeight = sackWeight - node.totalWeight;
        int totalValue = 0;

        while (availableWeight - q.peek().weight >= 0) {
            Item i = q.poll();
            availableWeight = availableWeight - i.weight;
            totalValue = totalValue + i.value;
        }
    //System.out.println(node.totalValue + totalValue);
        return node.totalValue + totalValue;
    }

    public static void main(String[] arg) {
        Knapsack0_N_Graph k = new Knapsack0_N_Graph();
        MultipleItem[] items =  new MultipleItem[] {new MultipleItem("A", 1,2,2), new MultipleItem("B",3,2,1),
                new MultipleItem("C", 2,7,3), new MultipleItem("D", 2,2,3),
                new MultipleItem("E", 2,4,1),new MultipleItem("F", 4,2,2)};
        items = k.knapsack(items, 9);
        int v = 0;
        int w = 0;
        for (MultipleItem i: items) {
            System.out.println(i.name + "  :  " + i.amount);
            v = v + i.value*i.amount;
            w = w + i.weight*i.amount;
        }
        System.out.println("Total Value : "+v);
        System.out.println("Total Weight : "+w);
    }

}
