import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class QuadraticClient {
    public static void main(String[] args){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");

            QuadraticSolver solver = (QuadraticSolver) registry.lookup("QuadraticSolverService");

            double a = 1, b = 5, c = -2;
            QuadraticSolution solution = solver.solveQuadratic(a,b,c);

            System.out.println("Result: " + solution);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
