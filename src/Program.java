import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class Program {
    private ArrayList<Box> boxes = null;
    private PriorityQueue<Item> itemHeap = new PriorityQueue<>();
    private Random rnd = new Random();
    private int boxCap;

    private void populateHeap() {
        for(int i = 0; i < 10; i++) {
            int num = rnd.nextInt(10) + 1;
            Item item = new Item(num);
            itemHeap.add(item);
        }
        System.out.println(itemHeap);
    }

    private void setBoxCapacity() {
        boxCap = rnd.nextInt(10) + 1;
    }

    private void createBox() {
        Box box = new Box(boxCap);
        boxes.add(box);
    }

    public static void main(String[] args) {
        Program program = new Program();
        program.setBoxCapacity();
        program.populateHeap();
    }
}
