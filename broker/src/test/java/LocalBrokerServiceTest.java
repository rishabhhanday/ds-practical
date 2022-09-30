import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.auldfellas.AFQService;
import service.broker.LocalBrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.dodgydrivers.DDQService;
import service.girlpower.GPQService;
import service.registry.ServiceRegistry;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.UUID;

import static service.core.ClientInfo.MALE;

public class LocalBrokerServiceTest {
    private static ServiceRegistry serviceRegistry = new ServiceRegistry();
    private static Registry registry;

    @BeforeClass
    public static void init() throws RemoteException {
        registry = LocateRegistry.createRegistry(1099);
    }

    private void unbind() throws NotBoundException, RemoteException {
        registry.unbind("girlpower");
        registry.unbind("auldfellas");
        registry.unbind("dodgydrivers");
        registry.unbind("broker");
    }

    private void bind() throws AlreadyBoundException, RemoteException {
        serviceRegistry = new ServiceRegistry();
        serviceRegistry.putRegistry("girlpower", registry);
        serviceRegistry.putRegistry("auldfellas", registry);
        serviceRegistry.putRegistry("dodgydrivers", registry);

        registry.bind("girlpower", new GPQService());
        registry.bind("auldfellas", new AFQService());
        registry.bind("dodgydrivers", new DDQService());
        registry.bind("broker", new LocalBrokerService(serviceRegistry));
    }

    @Test
    public void getEmptyQuotationTest() throws RemoteException, NotBoundException, AlreadyBoundException {
        registry.bind("broker", new LocalBrokerService(serviceRegistry));

        LocalBrokerService localBrokerService = (LocalBrokerService) registry
                .lookup("broker");

        List<Quotation> quotations = localBrokerService
                .getQuotations(getClientInfoStub());

        Assert.assertTrue(quotations.isEmpty());

        registry.unbind("broker");
    }

    @Test
    public void getAllQuotationsTest() throws RemoteException, AlreadyBoundException, NotBoundException {
        bind();

        final LocalBrokerService localBrokerService = (LocalBrokerService) registry
                .lookup("broker");

        List<Quotation> quotations = localBrokerService.getQuotations(getClientInfoStub());

        Assert.assertEquals(3, quotations.size());
        Assert.assertEquals(
                1,
                quotations
                        .stream()
                        .filter(quotation -> quotation.reference.contains("AF"))
                        .count()
        );
        Assert.assertEquals(
                1,
                quotations
                        .stream()
                        .filter(quotation -> quotation.reference.contains("DD"))
                        .count()
        );
        Assert.assertEquals(
                1,
                quotations
                        .stream()
                        .filter(quotation -> quotation.reference.contains("GP"))
                        .count()
        );

        unbind();
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
