/**
 * Created by Joakim on 2016-03-02.
 */
public class Item implements Comparable<Item> {
    private int weight;

    public Item(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Item other) {
        if(this.weight < other.weight) {
            return -1;
        } else if(this.weight > other.weight) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "" + this.weight;
    }
}
