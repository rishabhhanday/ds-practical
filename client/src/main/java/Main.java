import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.NumberFormat;
import java.util.List;

public class Main {
    /**
     * Test Data
     */
    public static final ClientInfo[] clients = {
            new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1"),
            new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 0, 2, "ABC123/4"),
            new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 16, 10, 0, "HMA304/9"),
            new ClientInfo("Rem Collier", ClientInfo.MALE, 44, 5, 3, "COL123/3"),
            new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 4, 7, "QUN987/4"),
            new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 5, 2, "XYZ567/9")
    };


    /**
     * This is the starting point for the application. Here, we must
     * get a reference to the Broker Service and then invoke the
     * getQuotations() method on that service.
     * <p>
     * Finally, you should print out all quotations returned
     * by the service.
     *
     * @param args args[0] - broker remote reference name
     *             args[1] - optional hostname to create/get registry.
     */
    public static void main(String[] args) throws NotBoundException, RemoteException {
        if (args.length < 1) {
            throw new RuntimeException("Remote reference name is missing for broker");
        }

        Registry registry = null;
        try {
            if (args.length == 2) {
                registry = LocateRegistry.getRegistry(args[1]); // broker
            } else {
                registry = LocateRegistry.createRegistry(1099);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        BrokerService brokerService = (BrokerService) registry.lookup(args[0]);
        // Create the broker and run the test data
        for (ClientInfo info : clients) {
            displayProfile(info);

            // Retrieve quotations from the broker and display them...
            try {
                List<Quotation> quotations = brokerService.getQuotations(info);
                for (Quotation quotation : quotations) {
                    displayQuotation(quotation);
                }
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            // Print a couple of lines between each client
            System.out.println("\n");
        }
    }

    /**
     * Display the client info nicely.
     *
     * @param info
     */
    public static void displayProfile(ClientInfo info) {
        System.out.println("|=================================================================================================================|");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println(
                "| Name: " + String.format("%1$-29s", info.name) +
                        " | Gender: " + String.format("%1$-27s", (info.gender == ClientInfo.MALE ? "Male" : "Female")) +
                        " | Age: " + String.format("%1$-30s", info.age) + " |");
        System.out.println(
                "| License Number: " + String.format("%1$-19s", info.licenseNumber) +
                        " | No Claims: " + String.format("%1$-24s", info.noClaims + " years") +
                        " | Penalty Points: " + String.format("%1$-19s", info.points) + " |");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println("|=================================================================================================================|");
    }

    /**
     * Display a quotation nicely - note that the assumption is that the quotation will follow
     * immediately after the profile (so the top of the quotation box is missing).
     *
     * @param quotation
     */
    public static void displayQuotation(Quotation quotation) {
        System.out.println(
                "| Company: " + String.format("%1$-26s", quotation.company) +
                        " | Reference: " + String.format("%1$-24s", quotation.reference) +
                        " | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price)) + " |");
        System.out.println("|=================================================================================================================|");
    }
}
