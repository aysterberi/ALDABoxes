import java.util.*;

public class Program {
    private ArrayList<Box> heapBoxes = new ArrayList<>();
    private ArrayList<Box> stackBoxes = new ArrayList<>();
    private PriorityQueue<Item> itemHeap = new PriorityQueue<>();
    private ArrayList<Item> maxList = new ArrayList<>();
    private Stack<Item> itemStack = new Stack<>();
    private Random rnd = new Random();
    private int boxCap;

    public Program() {

    }

    private void populate() {
        for (int i = 0; i < 10; i++) {
            int num = rnd.nextInt(10) + 1;
            Item item = new Item(num);
            itemHeap.add(item);
            itemStack.push(item);
            maxList.add(item);
            Collections.sort(maxList, Collections.reverseOrder());
        }
        System.out.println("Max Heap: " + maxList);
        System.out.println("Heap: " + itemHeap);
        System.out.println("Stack: " + itemStack);
    }

    private void setBoxCapacity() {
        boxCap = 10;
    }

    private ArrayList<Box> doStackFirstFit() {
        while (!itemStack.isEmpty()) {
            if (stackBoxes.isEmpty()) {
                stackBoxes.add(new Box(boxCap));
            }
            for (Box b : stackBoxes) {
                if (itemStack.peek().getWeight() <= b.getCapacity()) {
                    Item item = itemStack.pop();
                    b.getItems().add(item);
                    b.setCapacity(b.getCapacity() - item.getWeight());
                    break;
                }
                try {
                    if (itemStack.peek().getWeight() > b.getCapacity() && stackBoxes.get(stackBoxes.indexOf(b) + 1) == null);
                } catch (IndexOutOfBoundsException ioobe) {
                    stackBoxes.add(new Box(boxCap));
                    break;
                }
            }
        }
        return stackBoxes;
    }

    private ArrayList<Box> doHeapFirstFit() {
        while (!itemHeap.isEmpty()) {
            if (heapBoxes.isEmpty()) {
                heapBoxes.add(new Box(boxCap));
            }
            for (Box b : heapBoxes) {
                if (itemHeap.peek().getWeight() <= b.getCapacity()) {
                    Item item = itemHeap.poll();
                    b.getItems().add(item);
                    b.setCapacity(b.getCapacity() - item.getWeight());
                    break;
                }
                try {
                    if (itemHeap.peek().getWeight() > b.getCapacity() && heapBoxes.get(heapBoxes.indexOf(b) + 1) == null);
                } catch (IndexOutOfBoundsException ioobe) {
                    heapBoxes.add(new Box(boxCap));
                    break;
                }
            }
        }
        return heapBoxes;
    }

    public static void main(String[] args) {
        Program program = new Program();
        program.setBoxCapacity();
        program.populate();
        ArrayList<Box> heapFirstFit = program.doHeapFirstFit();
        ArrayList<Box> stackFirstFit = program.doStackFirstFit();
        System.out.println("First fit on heap (sorted): " + heapFirstFit + " Number of boxes: " + heapFirstFit.size());
        System.out.println("First fit on stack (unsorted): " + stackFirstFit + " Number of boxes: " + stackFirstFit.size());
    }
}
