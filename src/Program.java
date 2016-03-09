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
        while(!list.isEmpty()) {
            if(result.isEmpty()) {
                result.add(new Box(boxCap));
            }
            for(Box box : result) {
                if(list.peek().getWeight() <= box.getCapacity()) {
                    Item item = list.remove();
                    box.getItems().add(item);
                    box.setCapacity(box.getCapacity() - item.getWeight());
                    break;
                }
                try {
                    if(list.peek().getWeight() > box.getCapacity() && result.get(result.indexOf(box) + 1) == null);
                } catch(IndexOutOfBoundsException ioobe) {
                    result.add(new Box(boxCap));
                    break;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Program program = new Program();
        LinkedList<Item> min, max, rnd;
        ArrayList<Box> minResult, maxResult, rndResult;
        program.setBoxCapacity();

        // Populate the lists with the same data to enable comparisons
        LinkedList<Item> list = program.populate();
        min = new LinkedList<>(list);
        Collections.sort(min);
        max = new LinkedList<>(list);
        Collections.sort(max, Collections.reverseOrder());
        rnd = new LinkedList<>(list);

        minResult = program.doFirstFit(min);
        maxResult = program.doFirstFit(max);
        rndResult = program.doFirstFit(rnd);

        System.out.println("Sorted ascending: " + minResult + " Number of boxes: " + minResult.size());
        System.out.println("Sorted descending: " + maxResult + " Number of boxes: " + maxResult.size());
        System.out.println("Random placement: " + rndResult + " Number of boxes: " + rndResult.size());

    }
}
