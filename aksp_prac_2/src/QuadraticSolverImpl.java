import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class QuadraticSolverImpl extends UnicastRemoteObject implements QuadraticSolver {
    protected QuadraticSolverImpl() throws RemoteException {
        super();
    }

    @Override
    public QuadraticSolution solveQuadratic(double a, double b, double c) throws RemoteException {
        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return new QuadraticSolution(null, null, false);
        } else {
            double sqrtDiscriminant = Math.sqrt(discriminant);
            double root1 = (-b + sqrtDiscriminant) / (2 * a);
            double root2 = (-b - sqrtDiscriminant) / ( 2 * a);
            return new QuadraticSolution(root1, root2, true);
        }
    }
}
