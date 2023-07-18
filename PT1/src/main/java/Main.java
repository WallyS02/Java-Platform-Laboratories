import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    static final int NUMBER_OF_MAGES = 12;
    public static void main(String[] args) throws FileNotFoundException {
        Map<Mage, Integer> statistics;
        switch (args[0]) {
            case "noSort":
                statistics = new HashMap<>();
                break;
            case "naturalSort":
                statistics = new TreeMap<>();
                break;
            case "alternativeSort":
                MageComparator comp = new MageComparator();
                statistics = new TreeMap<>(comp);
                break;
            default:
                throw new RuntimeException("Wrong sorting option selected");
        }
        Mage[] mageArray = new Mage[NUMBER_OF_MAGES];
        File mageNames = new File("src\\main\\java\\mageNames.txt");
        Scanner mageScan = new Scanner(mageNames);
        for (int i = 0; i < NUMBER_OF_MAGES; i++){
            String name = mageScan.nextLine();
            mageArray[i] = initMage(args[0], name);
        }
        setApprentices(mageArray);
        mageArray[0].print(1);
        mageArray[0].getDescendants(statistics);
        System.out.println('\n');
        printMap(statistics);
    }

    public static Mage initMage(String sorting, String name) {
        Random rand = new Random();
        int level = rand.nextInt(50);
        double rangeMin = 0.0, rangeMax = 25.0;
        double power = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
        return new Mage(name, level, power, sorting);
    }

    public static void setApprentices(Mage[] mageArray){
        for (int i = 1; i < 5; i++) {
            mageArray[0].addApprentice(mageArray[i]);
        }
        for (int i = 5; i < 8; i++) {
            mageArray[1].addApprentice(mageArray[i]);
        }
        for (int i = 8; i < 10; i++) {
            mageArray[2].addApprentice(mageArray[i]);
        }
        for (int i = 10; i < 11; i++) {
            mageArray[5].addApprentice(mageArray[i]);
        }
        for (int i = 11; i < 12; i++) {
            mageArray[6].addApprentice(mageArray[i]);
        }
    }

    public static void printMap(Map<Mage, Integer> map){
        for (Mage mage : map.keySet()) {
            System.out.println(mage + ", apprentices: " + map.get(mage));
        }
    }
}