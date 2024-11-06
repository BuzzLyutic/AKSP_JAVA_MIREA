import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QuadraticSolver extends Remote {
    QuadraticSolution solveQuadratic(double a, double b, double c) throws RemoteException;
}
