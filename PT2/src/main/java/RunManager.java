public class RunManager implements Runnable {
    final private ResourceManager resources;
    final private ResultManager results;
    private boolean isWorking;

    public RunManager(ResourceManager resources, ResultManager results) {
        this.isWorking = true;
        this.resources = resources;
        this.results = results;
    }

    public void stop() {
        isWorking = false;
    }

    @Override
    public void run() {
        while(isWorking) {
            long number = resources.getNumber();
            boolean primeTest = getPrimeTest(number);
            results.addResult(number, primeTest);
        }
    }

    public static boolean getPrimeTest(long number) {
        if (number < 2)
            return false;
        for (long i = 2; i < number; i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }
}
