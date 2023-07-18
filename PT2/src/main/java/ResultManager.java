import java.util.HashMap;

public class ResultManager {
    final public HashMap<Long, Boolean> result;
    private boolean isWorking;

    public ResultManager() {
        isWorking = false;
        result = new HashMap<>();
    }

    public HashMap<Long, Boolean> getResult() {
        return result;
    }

    public synchronized void addResult(long number, boolean res) {
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
        if (number != -1) {
            result.put(number, res);
            System.out.println(number + " result is ready.");
        }
        isWorking = false;
        notifyAll();
    }
}
