public class InitTestData {
    private final MageService mageService;
    private final TowerService towerService;

    public InitTestData(MageService mageService, TowerService towerService) {
        this.mageService = mageService;
        this.towerService = towerService;
    }

    public void init() {
        Mage m1 = new Mage();
        m1.setName("Wird");
        m1.setLevel(15);
        Mage m2 = new Mage();
        m2.setName("Lilur");
        m2.setLevel(8);
        Mage m3 = new Mage();
        m3.setName("Turand");
        m3.setLevel(6);
        Mage m4 = new Mage();
        m4.setName("Tomir");
        m4.setLevel(2);

        mageService.add(m1);
        mageService.add(m2);
        mageService.add(m3);
        mageService.add(m4);

        Tower t1 = new Tower();
        t1.setName("White");
        t1.setHeight(1500);
        Tower t2 = new Tower();
        t2.setName("Black");
        t2.setHeight(1000);
        Tower t3 = new Tower();
        t3.setName("Yellow");
        t3.setHeight(500);
        Tower t4 = new Tower();
        t4.setName("Green");
        t4.setHeight(250);

        towerService.add(t1);
        towerService.add(t2);
        towerService.add(t3);
        towerService.add(t4);
    }
}
