import java.util.*;

public class Program {
    private LinkedList<Item> list = new LinkedList<>();
    private Random rnd = new Random();
    private int boxCap;

    public Program() {

    }

    private LinkedList<Item> populate() {
        for (int i = 0; i < 30; i++) {
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
            } else if (highestCapacityBox.getCapacity() < list.peek().getWeight()) {
                result.add(new Box(boxCap));
            }
        }
        return result;
    }

    private ArrayList<Box> doBestFit(LinkedList<Item> list) {
        ArrayList<Box> result = new ArrayList<>();
        Box bestBox = null;
        while (!list.isEmpty()) {
            if(result.isEmpty()) {
                result.add(new Box(boxCap));
                bestBox = result.get(0);
            }
            Item item = list.remove();
            while(true) {
                for(Box b : result) {
                    if(bestBox.getCapacity() < b.getCapacity()) {
                        bestBox = b;
                    }
                }
                if(item.getWeight() == boxCap) {
                    result.add(new Box(boxCap));
                    result.get(result.size() - 1).getItems().add(item);
                    break;
                }
                if(bestBox.getCapacity() == item.getWeight()) {
                    bestBox.getItems().add(item);
                    bestBox.setCapacity(0);
                    break;
                } else if(bestBox.getCapacity() > item.getWeight()) {
                    for (Box b : result) {
                        if(b.getCapacity() < bestBox.getCapacity() && b.getCapacity() >= item.getWeight()) {
                            bestBox = b;
                        }
                    }
                    bestBox.getItems().add(item);
                    bestBox.setCapacity(bestBox.getCapacity() - item.getWeight());
                    break;
                } else {
                    result.add(new Box(boxCap));
                    bestBox = result.get(result.size() - 1);
                    bestBox.getItems().add(item);
                    bestBox.setCapacity(bestBox.getCapacity() - item.getWeight());
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
        long startTime, endTime, minDuration, maxDuration, rndDuration;
        program.setBoxCapacity();

        // Populate the lists with the same data to enable comparisons
        LinkedList<Item> list = program.populate();
        min = new LinkedList<>(list);
        Collections.sort(min);
        max = new LinkedList<>(list);
        Collections.sort(max, Collections.reverseOrder());
        rnd = new LinkedList<>(list);

        System.out.println("Ascending:  " + min);
        System.out.println("Descending: " + max);
        System.out.println("Random:     " + rnd);
        System.out.println();
        System.out.println("First fit:");

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

        System.out.println("Ascending:  " + minResult + " Number of boxes: " + minResult.size() + " Time taken: " + minDuration);
        System.out.println("Descending: " + maxResult + " Number of boxes: " + maxResult.size() + " Time taken: " + maxDuration);
        System.out.println("Random:     " + rndResult + " Number of boxes: " + rndResult.size() + " Time taken: " + rndDuration);
        System.out.println();

        min = new LinkedList<>(list);
        Collections.sort(min);
        max = new LinkedList<>(list);
        Collections.sort(max, Collections.reverseOrder());
        rnd = new LinkedList<>(list);

        System.out.println("Most room fit:");

        startTime = System.nanoTime();
        minResult.clear();
        minResult = program.doMostRoomFit(min);
        endTime = System.nanoTime();
        minDuration = endTime - startTime;

        startTime = System.nanoTime();
        maxResult.clear();
        maxResult = program.doMostRoomFit(max);
        endTime = System.nanoTime();
        maxDuration = endTime - startTime;

        startTime = System.nanoTime();
        rndResult.clear();
        rndResult = program.doMostRoomFit(rnd);
        endTime = System.nanoTime();
        rndDuration = endTime - startTime;

        System.out.println("Ascending:  " + minResult + " Number of boxes: " + minResult.size() + " Time taken: " + minDuration);
        System.out.println("Descending: " + maxResult + " Number of boxes: " + maxResult.size() + " Time taken: " + maxDuration);
        System.out.println("Random:     " + rndResult + " Number of boxes: " + rndResult.size() + " Time taken: " + rndDuration);
        System.out.println();

        System.out.println("Best fit:");

        min = new LinkedList<>(list);
        Collections.sort(min);
        max = new LinkedList<>(list);
        Collections.sort(max, Collections.reverseOrder());
        rnd = new LinkedList<>(list);

        startTime = System.nanoTime();
        minResult.clear();
        minResult = program.doBestFit(min);
        endTime = System.nanoTime();
        minDuration = endTime - startTime;

        startTime = System.nanoTime();
        maxResult.clear();
        maxResult = program.doBestFit(max);
        endTime = System.nanoTime();
        maxDuration = endTime - startTime;

        startTime = System.nanoTime();
        rndResult.clear();
        rndResult = program.doBestFit(rnd);
        endTime = System.nanoTime();
        rndDuration = endTime - startTime;

        System.out.println("Ascending:  " + minResult + " Number of boxes: " + minResult.size() + " Time taken: " + minDuration);
        System.out.println("Descending: " + maxResult + " Number of boxes: " + maxResult.size() + " Time taken: " + maxDuration);
        System.out.println("Random:     " + rndResult + " Number of boxes: " + rndResult.size() + " Time taken: " + rndDuration);
        System.out.println();

    }
}
