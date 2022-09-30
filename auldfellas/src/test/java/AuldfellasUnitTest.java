import org.junit.BeforeClass;
import org.junit.Test;
import service.auldfellas.AFQService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import static org.junit.Assert.*;
import static service.core.ClientInfo.MALE;

public class AuldfellasUnitTest {
    private static Registry registry;

    @BeforeClass
    public static void setup() {
        QuotationService afqService = new AFQService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(afqService, 0);
            registry.bind("auldfellas", quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup("auldfellas");
        assertNotNull(service);
    }

    @Test
    public void generateQuotationTest() throws RemoteException, NotBoundException {
        QuotationService service = (QuotationService)
                registry.lookup("auldfellas");

        Quotation actualQuotation = service.generateQuotation(getClientInfoStub());

        assertNotNull(actualQuotation);
        assertTrue(actualQuotation instanceof Quotation);
        assertEquals(AFQService.COMPANY, actualQuotation.company);
        assertTrue(actualQuotation.reference.contains("AF"));
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