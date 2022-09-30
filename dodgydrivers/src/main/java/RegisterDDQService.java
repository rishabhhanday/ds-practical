import service.core.QuotationService;
import service.dodgydrivers.DDQService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RegisterDDQService {

    /**
     * Binds DDQService
     *
     * @param args passed as a command line argument
     *             args[0] - remote reference name for DDQService object
     *             args[1] - optional hostname to create/get the registry
     **/
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("AFQService - remote reference missing in passed args.");
        }

        QuotationService ddqService = new DDQService();
        try {
            Registry registry;
            if (args.length > 1) {
                String host = args[1];
                registry = LocateRegistry.getRegistry(host);
            } else {
                registry = LocateRegistry.createRegistry(1099);
            }

            QuotationService quotationService = (QuotationService) UnicastRemoteObject.exportObject(ddqService, 0);
            registry.bind(args[0], quotationService);

            System.out.println("STOPPING SERVER SHUTDOWN");
            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
}