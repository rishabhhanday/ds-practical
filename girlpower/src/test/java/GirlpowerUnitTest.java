import org.junit.BeforeClass;
import org.junit.Test;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
import service.girlpower.GPQService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import static org.junit.Assert.*;
import static service.core.ClientInfo.MALE;

public class GirlpowerUnitTest {
    private static Registry registry;

    @BeforeClass
    public static void setup() {
        QuotationService gpqService = new GPQService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(gpqService, 0);
            registry.bind("girlpower", quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup("girlpower");
        assertNotNull(service);
    }

    @Test
    public void generateQuotationTest() throws RemoteException, NotBoundException {
        QuotationService service = (QuotationService)
                registry.lookup("girlpower");

        Quotation actualQuotation = service.generateQuotation(getClientInfoStub());

        assertNotNull(actualQuotation);
        assertTrue(actualQuotation instanceof Quotation);
        assertEquals(GPQService.COMPANY, actualQuotation.company);
        assertTrue(actualQuotation.reference.contains("GP"));
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