import java.util.HashMap;
import java.util.Objects;

public class Step {

    final private Point location;
    private Step prior;
    private double h;
    private double g;

    public Step(Point location, Point end, Step prior){
        this(location, end);
        this.prior = prior;
        this.g = prior.getG() + 1;
    }

    public Step(Point location, Point end){
        this.location = location;
        this.prior = null;
        this.g = 0;
        this.h = Math.abs(location.x-end.x)+Math.abs(location.y-end.y);
    }

    public Step getPrior() {
        return prior;
    }
    public Point getLocation() {
        return location;
    }

    public double getF() {
        return g+h;
    }
    public double getG() {
        return g;
    }

    public boolean equals(Object o){
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }else{
            Step s = (Step) o;
            if(this.getPrior()==null){
                return s.location.equals(this.location) && s.getPrior() == null;
            }else if(s.getPrior()==null){
                return s.location.equals(this.location) && this.getPrior() == null;
            }
                return s.location.equals(this.location) && s.getPrior().equals(this.getPrior());
        }
    }
    public int hashCode(){
        return Objects.hash(this.getLocation(), this.getPrior());
    }
}