import java.util.Objects;
import java.util.Random;

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
        this.x = round(rand.nextDouble(0, xBound), precision);
        this.y = round(rand.nextDouble(0, yBound), precision);
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
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
