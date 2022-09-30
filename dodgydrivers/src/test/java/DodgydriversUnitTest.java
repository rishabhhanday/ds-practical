import org.junit.BeforeClass;
import org.junit.Test;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
import service.dodgydrivers.DDQService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import static org.junit.Assert.*;
import static service.core.ClientInfo.MALE;

public class DodgydriversUnitTest {
    private static Registry registry;

    @BeforeClass
    public static void setup() {
        QuotationService ddqService = new DDQService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(ddqService, 0);
            registry.bind("dodgydrivers", quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup("dodgydrivers");
        assertNotNull(service);
    }

    @Test
    public void generateQuotationTest() throws RemoteException, NotBoundException {
        QuotationService service = (QuotationService)
                registry.lookup("dodgydrivers");

        Quotation actualQuotation = service.generateQuotation(getClientInfoStub());

        assertNotNull(actualQuotation);
        assertTrue(actualQuotation instanceof Quotation);
        assertEquals(DDQService.COMPANY, actualQuotation.company);
        assertTrue(actualQuotation.reference.contains("DD"));
    }

    private ClientInfo getClientInfoStub() {
        return new ClientInfo(
                "Rishabh",
                MALE, 24,
                3,
                2,
                UUID.randomUUID().toString());
    }
}