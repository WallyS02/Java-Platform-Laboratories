import java.util.List;

public class TowerService {
    public final TowerRepository repo;

    public TowerService(TowerRepository repo) {
        this.repo = repo;
    }

    public List<Tower> findAllTowers() {
        return repo.findAll();
    }

    public List<Tower> findAllTowersWithHeight(int height) {
        return repo.findAllTowersWithHeight(height);
    }

    public void delete(Tower tower) {
        repo.delete(tower);
    }

    public void add(Tower tower) {
        repo.add(tower);
    }

    public void printTowers() {
        System.out.println("Towers: ");
        int index = 1;
        for (Tower tower : repo.findAll()) {
            System.out.println(index + ". " + tower.getName() + " tower.");
            index += 1;
        }
    }
}
