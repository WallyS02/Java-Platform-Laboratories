import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
public class Tower {
    @Id
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int height;
    @Getter
    @Setter
    @OneToMany(mappedBy = "tower", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Mage> mages;
}
