import java.io.Serializable;

public class QuadraticSolution implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double root1;
    private Double root2;
    private boolean hasRealRoots;

    public QuadraticSolution(Double root1, Double root2, boolean hasRealRoots){
        this.root1 = root1;
        this.root2 = root2;
        this.hasRealRoots = hasRealRoots;
    }

    public Double getRoot1() {
        return root1;
    }

    public Double getRoot2() {
        return root2;
    }

    public boolean HasRealRoots() {
        return hasRealRoots;
    }

    @Override
    public String toString(){
        if (hasRealRoots){
            return "Корни: корень1 = " + root1 + ", корень2 = " + root2;

        } else {
            return "Не имеет действительных корней.";
        }
    }
}
