import java.util.*;

/**
 * This class packs boxes using three different algorithms
 * with items, attempting to optimise the amount of items
 * in a box with respect to their total weight and the
 * box' maximum weight capacity.
 */
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

    /**
    * Returns a list of packed boxes using the first fit method.
    * The LinkedList argument must have the type Item
    * <p>
    * This method always returns an ArrayList, whether or not any boxes have been packed.
    * It attempts to fit the item into the first possible extant box. If it does not fit,
    * it creates another box and puts the item inside.
    *
    * @param list   A LinkedList of items to pack into boxes
    * @return       An ArrayList of the packed boxes
     */
    private ArrayList<Box> doFirstFit(LinkedList<Item> list) {
        ArrayList<Box> result = new ArrayList<>();
        while (!list.isEmpty()) {
            if (result.isEmpty()) {
                //no existing boxes
                result.add(new Box(boxCap));  //add a new box
            }
            for (Box box : result) {
                if (list.peek().getWeight() <= box.getCapacity()) { //does our item fit in the box?
                    Item item = list.remove(); //fetch the item
                    box.getItems().add(item); // pack it
                    box.setCapacity(box.getCapacity() - item.getWeight()); //update our box' remaining capacity
                    break;
                }
                try {
                    if (list.peek().getWeight() > box.getCapacity() && result.get(result.indexOf(box) + 1) == null) ;
                    //if the item is too heavy for our box capacity and we've reached the end of packed boxlist
                } catch (IndexOutOfBoundsException ioobe) {
                    result.add(new Box(boxCap)); //create a new box!
                    break;
                }
            }
        }
        return result;
    }

	/**
     * Returns a list of packed boxes using the most room algorithm.
     * The LinkedList argument must have the type Item
     * <p>
     * This method always returns an ArrayList, whether or not any boxes have been packed.
     * It attempts to pack each item into the box with most room for it.
     * If the box with highest capacity still cannot fit the item, a new box is created.
     *
     * @param list  A LinkedList of items to pack into boxes
     * @return      an ArrayList of the packed boxes
     */
    private ArrayList<Box> doMostRoomFit(LinkedList<Item> list) {
        ArrayList<Box> result = new ArrayList<>();
        Box highestCapacityBox = null;
        while (!list.isEmpty()) {
            if (result.isEmpty()) {
                //no boxes
                result.add(new Box(boxCap)); //add a new box
                highestCapacityBox = result.get(0); //set it as current highest capacity box
            }
            for (Box box : result) {
                if(box.getCapacity() > highestCapacityBox.getCapacity()) {
                    //if current box has higher capacity than last maxcap box
                    highestCapacityBox = box; //set our box as current highest cap. box
                }
            }
            if (highestCapacityBox.getCapacity() >= list.peek().getWeight()) {
                //we have space!
                Item item = list.remove(); //fetch item
                highestCapacityBox.getItems().add(item); //box it
                //update our box' remaining capacity
                highestCapacityBox.setCapacity(highestCapacityBox.getCapacity() - item.getWeight());
            } else if (highestCapacityBox.getCapacity() < list.peek().getWeight()) {
                //item is too heavy for any box
                result.add(new Box(boxCap));  //add a new box
            }
        }
        //return our packed boxes
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
