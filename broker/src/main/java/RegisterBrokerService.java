import service.broker.LocalBrokerService;
import service.core.BrokerService;
import service.registry.ServiceRegistry;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RegisterBrokerService {

    /**
     * Binds localBrokerService that contains lookup registries to get quotation running on different container
     *
     * @param args passed as a command line argument
     *             args[0] - remote reference name for broker object
     *             args[1] - remote reference name for quotation object seperated by ','
     *             args[2] - hostname to lookup for quotation objects
     *             args[3] - Optional hostname to bind the BrokerService
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            throw new RuntimeException("Bind reference name is missing for broker and quotation service.");
        }

        try {
            Registry registry;
            if (args.length == 4) {
                registry = LocateRegistry.getRegistry(args[3]);
            } else {
                registry = LocateRegistry.createRegistry(1099);
            }

            ServiceRegistry serviceRegistry = new ServiceRegistry();

            List<Registry> registries = Arrays
                    .stream(args[2].split(","))
                    .map(host -> {
                        try {
                            return LocateRegistry.getRegistry(host);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());

            List<String> remoteReferenceNames = Arrays
                    .stream(args[1].split(","))
                    .collect(Collectors.toList());

            if (registries.size() != remoteReferenceNames.size()) {
                throw new RuntimeException("Number of hostnames should match with number of remoteReferenceNames");
            }

            for (int i = 0; i < registries.size(); i++) {
                serviceRegistry.putRegistry(remoteReferenceNames.get(i), registries.get(i));
            }

            BrokerService localBrokerService = new LocalBrokerService(serviceRegistry);
            BrokerService brokerService = (BrokerService) UnicastRemoteObject.exportObject(localBrokerService, 0);
            registry.bind(args[0], brokerService);

            System.out.println("STOPPING SERVER SHUTDOWN");
            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
}