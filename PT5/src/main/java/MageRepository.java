import java.util.ArrayList;
import java.util.Optional;
import java.util.Collection;

public class MageRepository {
    final private Collection<Mage> mages;

    public MageRepository() {
        mages = new ArrayList<>();
    }

    public Optional<Mage> find(String name) {
        Mage searchedMage = null;
        for(Mage mage : mages) {
            if(mage.getName().equals(name)) {
                searchedMage = mage;
                break;
            }
        }
        if(searchedMage == null) {
            return Optional.empty();
        }
        else {
            return Optional.of(searchedMage);
        }
    }

    public void delete(String name) throws IllegalArgumentException {
        Mage searchedMage = null;
        for(Mage mage : mages) {
            if(mage.getName().equals(name)) {
                searchedMage = mage;
                break;
            }
        }
        if(searchedMage != null) {
            mages.remove(searchedMage);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public void save(Mage newMage) throws IllegalArgumentException {
        for(Mage mage : mages) {
            if(mage.getName().equals(newMage.getName())) {
                throw new IllegalArgumentException();
            }
        }
        mages.add(newMage);
    }
}
