import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static boolean toFile = false;
    private static ResourceManager resourceManager;

    public static void main(String[] args) {
        int numberOfThreads = Integer.parseInt(args[0]);
        resourceManager = new ResourceManager();
        ResultManager resultManager = new ResultManager();
        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<RunManager> threadRunners = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            RunManager runManager = new RunManager(resourceManager, resultManager);
            threadRunners.add(runManager);
            Thread thread = new Thread(runManager);
            threads.add(thread);
            thread.start();
        }
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Enter command.");
            System.out.println("1 -> Get divisors of number.");
            System.out.println("2 -> Load numbers from file.");
            System.out.println("3 -> Save results to a file?");
            System.out.println("4 -> Exit the program.");
            int userInput = Integer.parseInt(scanner.next());
            switch (userInput) {
                case 1 -> {
                    System.out.println("Enter a number to test if it is prime: ");
                    try {
                        long user_number = scanner.nextLong();
                        resourceManager.setNumber(user_number);
                    } catch (Exception e) {
                        System.out.println("Wrong number.");
                    }
                }
                case 2 -> {
                    System.out.println("Enter a filename.");
                    try {
                        String filename = scanner.next();
                        loadFromFile(filename);
                    } catch (Exception e) {
                        System.out.println("Wrong number.");
                    }
                }
                case 3 -> {
                    toFile = !toFile;
                    if (toFile)
                        System.out.println("Results will be saved to a file.");
                    else
                        System.out.println("Results will not be saved to a file.");
                }
                case 4 -> {
                    System.out.println("Exiting program.");
                    running = false;
                }
                default -> System.out.println("Enter correct command.");
            }
        }
        for (RunManager threadRunner : threadRunners) {
            threadRunner.stop();
        }
        for (Thread thread : threads) {
            thread.interrupt();
        }
        HashMap<Long, Boolean> results = resultManager.getResult();
        printResults(results);
        if (toFile)
            saveToFile(results);
    }

    public static void printResults(HashMap<Long, Boolean> results) {
        for (long number : results.keySet()) {
            System.out.println(number + " -> " + results.get(number));
        }
    }

    public static void saveToFile(HashMap<Long, Boolean> results) {
        try {
            String filename = "results.txt";
            File saveFile = new File(filename);
            if (saveFile.createNewFile()) {
                System.out.println("File created: " + saveFile.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter fileWriter = new FileWriter(filename);
            for (long number : results.keySet()) {
                String res = number + " -> " + results.get(number) + "\n";
                fileWriter.append(res);
            }
            fileWriter.close();
        } catch(Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void loadFromFile(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLong()) {
                try {
                    long number = scanner.nextLong();
                    resourceManager.setNumber(number);
                } catch (Exception e) {
                    System.out.println("Wrong number.");
                }
            }
        } catch(Exception e) {
            System.err.println("No file found at path: " + filename);
        }
    }
}
