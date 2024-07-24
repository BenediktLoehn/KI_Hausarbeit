import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Random;

@Getter
@Setter
public class Vector2D {

    private Random rand = new Random();
    private double x;
    private double y;
    private int precision = 2; //Nachkommastellen

    public Vector2D(double x, double y) {
        this.x = round(x,precision);
        this.y = round(y,precision);
    }

    //random vector for starting position
    public Vector2D(int xBound, int yBound) {
        this.x = round(rand.nextDouble(xBound, yBound), precision);
        this.y = round(rand.nextDouble(xBound, yBound), precision);
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(round(this.x + other.x,precision),round(this.y + other.y, precision));
    }

    public Vector2D sub(Vector2D other){
        return new Vector2D(round(this.x - other.x,precision),round(this.y - other.y, precision));
    }

    public Vector2D scale(double scalar) {
        return new Vector2D(round(this.x * scalar,precision),round(this.y * scalar,precision));
    }

    private double round(double input, int digits){
        return Math.round(input * Math.pow(10,digits)) /Math.pow(10,digits);
    }

    public Vector2D elementWiseMul(Vector2D other) {
        return new Vector2D(round(this.x * other.x, precision), round(this.y * other.y, precision));
    }

    // Distance between two vectors
    public double distance(Vector2D other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    @Override
    public String toString() {
        return String.format("Vector[%s | %s]",x,y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
