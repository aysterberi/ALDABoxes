import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class Program {
    private ArrayList<Box> boxes = new ArrayList<>();
    private PriorityQueue<Item> itemHeap = new PriorityQueue<>();
    private Random rnd = new Random();
    private int boxCap;

    public Program() {

    }

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

    private void doFirstFit() {
        if(boxes.isEmpty()) {
            createBox();
        }

        for(Box box : boxes) {
            if(itemHeap.peek().getWeight() < box.getCapacity()) {
                Item item = itemHeap.poll();
                box.getItems().add(item);
                box.setCapacity(box.getCapacity() - item.getWeight());
            } else if(itemHeap.peek().getWeight() > box.getCapacity()) {
                createBox();
            }
        }
        System.out.println(boxes);
    }

    public static void main(String[] args) {
        Program program = new Program();
        program.setBoxCapacity();
        program.populateHeap();
        program.doFirstFit();

    }
}
