import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class Main {
    final public static String persistenceUnitName = "Per";

    public static void main(String[] args) {
        EntityManagerFactory enf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Per");
        MageService mageService = new MageService(new MageRepository(emf));
        TowerService towerService = new TowerService(new TowerRepository(emf));
        InitTestData init = new InitTestData(mageService, towerService);
        init.init();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while(!exit) {
            System.out.println("addMage | addTower | removeMage | removeTower | printMages | printTowers | printAll | query | exit");
            System.out.println("Command: ");
            String command = scanner.next();
            switch (command) {
                case "addMage" -> {
                    Mage mage = new Mage();
                    System.out.println("Enter name: ");
                    String name = scanner.next();
                    mage.setName(name);
                    System.out.println("Enter level: ");
                    int level = scanner.nextInt();
                    mage.setLevel(level);
                    System.out.println("Select tower (to not choose enter -1)");
                    List<Tower> towers = towerService.findAllTowers();
                    System.out.println("-1. No tower");
                    towerService.printTowers();
                    int towerIndex = scanner.nextInt();
                    if (towerIndex != -1)
                        mage.setTower(towers.get(towerIndex - 1));
                    mageService.add(mage);
                }
                case "addTower" -> {
                    Tower tower = new Tower();
                    System.out.println("Enter tower name: ");
                    String name = scanner.next();
                    tower.setName(name);
                    System.out.println("Enter tower height: ");
                    int height = scanner.nextInt();
                    tower.setHeight(height);
                    towerService.add(tower);
                }
                case "removeMage" -> {
                    System.out.println("Select mage to remove");
                    mageService.printMages();
                    int mageIdx = scanner.nextInt() - 1;
                    Mage mageToDelete = mageService.findAllMages().get(mageIdx);
                    mageService.delete(mageToDelete);
                }
                case "removeTower" -> {
                    System.out.println("Select tower to remove");
                    towerService.printTowers();
                    int towerIdx = scanner.nextInt() - 1;
                    Tower towerToDelete = towerService.findAllTowers().get(towerIdx);
                    towerService.delete(towerToDelete);
                }
                case "printMages" -> mageService.printMages();
                case "printTowers" -> towerService.printTowers();
                case "printAll" -> {
                    mageService.printMages();
                    towerService.printTowers();
                }
                case "query" -> {
                    System.out.println("1. Get mages with level higher than | 2. Get list of sorted mages names with letter a ");
                    while (true) {
                        int queryIdx = scanner.nextInt();
                        if (queryIdx == 1) {
                            System.out.println("Enter level");
                            int level = scanner.nextInt();
                            List<Mage> results = mageService.findAllMagesWithLevelHigherThan(level);
                            for (Mage mage : results) {
                                System.out.println(mage.getName() + " -> " + mage.getLevel());
                            }
                            break;
                        } else if (queryIdx == 2) {
                            List<Mage> results = mageService.sortedMagesWithLetterA();
                            for (Mage mage : results) {
                                System.out.println(mage.getName());
                            }
                            break;
                        }
                    }
                }
                case "exit" -> exit = true;
                default -> System.out.println("addMage | addTower | removeMage | removeTower | printMages | printTowers | printAll | query | exit");
            }
        }
        enf.close();
    }
}
