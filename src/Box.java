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
        String str = "[";
            for (Item item : items) {
                if (item != null) {
                    str += item.toString() + ", ";
                }
            }
        String result = str.substring(0, str.length() - 2);
        result += "]";
        return result;
    }
}
