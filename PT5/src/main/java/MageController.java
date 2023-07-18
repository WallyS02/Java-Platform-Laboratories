public class MageController {
    final private MageRepository mages;

    public MageController(MageRepository mages) {
        this.mages = mages;
    }

    public String find(String name) {
        try {
            Mage searchedMage = mages.find(name).orElseThrow();
            return searchedMage.toString();
        }
        catch (Exception e) {
            return "not found";
        }
    }
    public String delete(String name) {
        try {
            mages.delete(name);
            return "done";
        }
        catch (IllegalArgumentException e) {
            return "not found";
        }
    }
    public String save(String name, int level) {
        try {
            mages.save(new Mage(name, level));
            return "done";
        }
        catch (IllegalArgumentException e) {
            return "bad request";
        }
    }
}
