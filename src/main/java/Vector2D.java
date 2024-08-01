import java.util.Objects;

public class Vector2D {

    private final double x;
    private final double y;
    private static final int precision = 2; //Nachkommastellen

    public Vector2D(double x, double y) {
        this.x = round(x,precision);
        this.y = round(y,precision);
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

    // Distance between two vectors
    public double distance(Vector2D other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    //length of a vector
    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    public Vector2D closestPointOnLineSegment(Vector2D a, Vector2D b) {
        double lengthSquared = a.sub(b).magnitude() * a.sub(b).magnitude();
        if (lengthSquared == 0.0) return a;

        double t = Math.max(0, Math.min(1, this.sub(a).dot(b.sub(a)) / lengthSquared));
        return a.add(b.sub(a).scale(t));
    }

    public Vector2D normalize() {
        double magnitude = magnitude();
        if (magnitude == 0) {
            // To avoid division by zero, return the zero vector
            return new Vector2D(0.0, 0.0);
        }
        return new Vector2D(x / magnitude, y / magnitude);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("Vector[%.2f | %.2f]", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return Double.compare(vector2D.x, x) == 0 && Double.compare(vector2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
