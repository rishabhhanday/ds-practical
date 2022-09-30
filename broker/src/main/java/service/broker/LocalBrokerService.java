package service.broker;

import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
import service.registry.ServiceRegistry;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the broker service that uses the Service Registry.
 *
 * @author Rem
 */
public class LocalBrokerService implements BrokerService {
    private final ServiceRegistry serviceRegistry;

    public LocalBrokerService(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public List<Quotation> getQuotations(ClientInfo info) throws RemoteException {
        List<Quotation> quotations = new LinkedList<>();

        for (String name : serviceRegistry.list()) {
            QuotationService service = serviceRegistry.lookup(name, QuotationService.class);
            quotations.add(service.generateQuotation(info));
        }

        return quotations;
    }
}
