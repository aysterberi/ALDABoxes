import java.util.ArrayList;

/**
 * Created by Joakim on 2016-03-02.
 */
public class Box {
    private int capacity;
    private ArrayList<Item> items;

    public Box(int capacity) {
        this.capacity = capacity;
        items = new ArrayList<>();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        String result = "";
        for(Item item : items) {
            result += item.toString();
        }
        return result;
    }
}
