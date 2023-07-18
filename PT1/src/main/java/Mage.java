import java.util.*;

public class Mage implements Comparable<Mage> {
    private final String name;
    private final int level;
    private final double power;
    private Set<Mage> apprentices;

    public Mage(String name, int level, double power, String sorting) {
        this.name = name;
        this.level = level;
        this.power = power;
        switch (sorting) {
            case "noSort":
                apprentices = new HashSet<>();
                break;
            case "naturalSort":
                apprentices = new TreeSet<>();
                break;
            case "alternativeSort":
                MageComparator comp = new MageComparator();
                apprentices = new TreeSet<>(comp);
                break;
        }
    }

    public double getPower() {
        return power;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public void addApprentice(Mage mage) {
        apprentices.add(mage);
    }

    public int getDescendants(Map<Mage, Integer> statistics) {
        int sum = 0;
        for (Mage mage : apprentices) {
            sum += mage.getDescendants(statistics) + 1;
        }
        statistics.put(this, sum);
        return sum;
    }

    @Override
    public boolean equals(Object other){
        if (this == other) {
            return true; //very same object
        }
        if (other == null || getClass() != other.getClass()) {
            return false; //other is null or final classes are different
        }
        Mage mage = (Mage) other;
        if(level != mage.level){
            return false;
        }
        if(power != mage.power){
            return false;
        }
        if (!Objects.equals(name, mage.name)) {
            return false; //check each field
        }
        return apprentices.equals(mage.apprentices);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, level, power, apprentices);
    }

    @Override
    public String toString(){
        return "Mage{" + "name=" + name + " level=" + level + " power=" + power + "}";
    }

    @Override
    public int compareTo(Mage other) {
        int ret = name.compareTo(other.name);
        if (ret == 0) {
            ret = level - other.level;
        }
        if (ret == 0) {
            ret = (int)power - (int)other.power;
        }
        return ret;
    }

    public void print(int depth){
        StringBuilder dive = new StringBuilder();
        for(int i = 0; i < depth; i++){
            dive.append("-");
        }
        System.out.println(dive.toString() + this);
        for (Mage obj : apprentices) {
            obj.print(depth + 1);
        }
    }
}
