import java.util.ArrayList;

public class ResourceManager {
    final private ArrayList<Long> numbers;
    private boolean isWorking;

    public ResourceManager() {
        numbers = new ArrayList<>();
        isWorking = false;
    }

    public synchronized void setNumber(long newNumber) {
        while (isWorking) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted!");
                return;
            }
        }
        isWorking = true;
        numbers.add(newNumber);
        isWorking = false;
        notifyAll();
    }

    public synchronized long getNumber() {
        while (numbers.size() == 0 || isWorking) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted!");
                return -1;
            }
        }
        isWorking = true;
        long currentNumber = numbers.remove(numbers.size() - 1);
        isWorking = false;
        notifyAll();
        return currentNumber;
    }
}
