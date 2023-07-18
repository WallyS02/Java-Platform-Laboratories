import java.util.List;

public class MageService {
    private final MageRepository repo;

    public MageService(MageRepository repo) {
        this.repo = repo;
    }

    public List<Mage> findAllMages() {
        return repo.findAll();
    }

    public List<Mage> findAllMagesWithLevelHigherThan(int level) {
        return repo.findAllMagesWithLevelHigherThan(level);
    }

    public List<Mage> findAllMagesFromTower(Tower tower) {
        return repo.findAllMagesFromTower(tower);
    }

    public List<Mage> sortedMagesWithLetterA() {
        return repo.sortedMagesWithLetterA();
    }

    public void delete(Mage mage) {
        repo.delete(mage);
    }

    public void add(Mage mage) {
        repo.add(mage);
    }

    public void printMages() {
        System.out.println("Mages: ");
        int index = 1;
        for (Mage mage : repo.findAll())  {
            String towerName = mage.getTower() == null ? "no tower" : mage.getTower().getName();
            System.out.println(index + ". Mage " + mage.getName() + ", level: " + mage.getLevel() +  " -> " + towerName);
            index += 1;
        }
    }
}
