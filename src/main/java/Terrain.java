import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Terrain {

    private final double length;
    private final double width;
    private Vector2D globalMaxPosition;

    public Terrain(double length, double width) {
        this.length = length;
        this.width = width;
    }
}
