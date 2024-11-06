import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class QuadraticServer {
    public static void main(String[] args) {
        try {
            QuadraticSolverImpl solver = new QuadraticSolverImpl();

            Registry registry = LocateRegistry.createRegistry(1099);

            registry.bind("QuadraticSolverService", solver);

            System.out.println("Server is ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
