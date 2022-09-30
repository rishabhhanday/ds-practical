package service.core;

import service.registry.Service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for defining the behaviours of the broker service
 *
 * @author Rem
 */
public interface BrokerService extends Remote, Service {
    public List<Quotation> getQuotations(ClientInfo info) throws RemoteException;
}
