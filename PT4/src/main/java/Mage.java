import lombok.*;
import jakarta.persistence.*;

@NoArgsConstructor
@Entity
public class Mage {
    @Id
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int level;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    private Tower tower;
}
