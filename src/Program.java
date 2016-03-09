import java.util.*;

public class Program {
    private LinkedList<Item> list = new LinkedList<>();
    private Random rnd = new Random();
    private int boxCap;

    public Program() {

    }

    private LinkedList<Item> populate() {
        for (int i = 0; i < 10; i++) {
            int num = rnd.nextInt(10) + 1;
            Item item = new Item(num);
            list.add(item);
        }
        return list;
    }

    private void setBoxCapacity() {
        boxCap = 10;
    }

    private ArrayList<Box> doFirstFit(LinkedList<Item> list) {
        ArrayList<Box> result = new ArrayList<>();
        while (!list.isEmpty()) {
            if (result.isEmpty()) {
                result.add(new Box(boxCap));
            }
            for (Box box : result) {
                if (list.peek().getWeight() <= box.getCapacity()) {
                    Item item = list.remove();
                    box.getItems().add(item);
                    box.setCapacity(box.getCapacity() - item.getWeight());
                    break;
                }
                try {
                    if (list.peek().getWeight() > box.getCapacity() && result.get(result.indexOf(box) + 1) == null) ;
                } catch (IndexOutOfBoundsException ioobe) {
                    result.add(new Box(boxCap));
                    break;
                }
            }
        }
        return result;
    }

    private ArrayList<Box> doMostRoomFit(LinkedList<Item> list) {
        ArrayList<Box> result = new ArrayList<>();
        Box highestCapacityBox = null;
        while (!list.isEmpty()) {
            if (result.isEmpty()) {
                result.add(new Box(boxCap));
                highestCapacityBox = result.get(0);
            }
            for (Box box : result) {
                if(box.getCapacity() > highestCapacityBox.getCapacity()) {
                    highestCapacityBox = box;
                }
            }
            if (highestCapacityBox.getCapacity() >= list.peek().getWeight()) {
                Item item = list.remove();
                highestCapacityBox.getItems().add(item);
                highestCapacityBox.setCapacity(highestCapacityBox.getCapacity() - item.getWeight());
                break;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Program program = new Program();
        LinkedList<Item> min, max, rnd;
        ArrayList<Box> minResult, maxResult, rndResult;
        long startTime, endTime, minDuration, maxDuration, rndDuration;
        program.setBoxCapacity();

        // Populate the lists with the same data to enable comparisons
        LinkedList<Item> list = program.populate();
        min = new LinkedList<>(list);
        Collections.sort(min);
        max = new LinkedList<>(list);
        Collections.sort(max, Collections.reverseOrder());
        rnd = new LinkedList<>(list);

        System.out.println("First fit:");
        System.out.println("Ascending: " + min);
        System.out.println("Descending: " + max);
        System.out.println("Random: " + rnd);

        startTime = System.nanoTime();
        minResult = program.doFirstFit(min);
        endTime = System.nanoTime();
        minDuration = endTime - startTime;

        startTime = System.nanoTime();
        maxResult = program.doFirstFit(max);
        endTime = System.nanoTime();
        maxDuration = endTime - startTime;

        startTime = System.nanoTime();
        rndResult = program.doFirstFit(rnd);
        endTime = System.nanoTime();
        rndDuration = endTime - startTime;

        System.out.println("Sorted ascending: " + minResult + " Number of boxes: " + minResult.size() + " Time taken: " + minDuration);
        System.out.println("Sorted descending: " + maxResult + " Number of boxes: " + maxResult.size() + " Time taken: " + maxDuration);
        System.out.println("Random placement: " + rndResult + " Number of boxes: " + rndResult.size() + " Time taken: " + rndDuration);
        System.out.println();
        System.out.println("Most room fit:");

    }
}
