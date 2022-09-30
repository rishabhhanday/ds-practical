package service.registry;

import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This is a basic service registry implementation that is based on the registry used in
 * RMI systems.
 *
 * @author Rem
 */
public class ServiceRegistry {
    private final Map<String, Registry> registryMap = new HashMap<>();

    public void putRegistry(String name, Registry registry) {
        registryMap.put(name, registry);
    }

    public Service lookup(String name) {
        Registry registry = registryMap.get(name);
        try {
            return (Service) registry.lookup(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public  <T extends Service> T lookup(String name, Class<T> clazz) {
        Registry registry = registryMap.get(name);
        try {
            return (T) registry.lookup(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Set<String> list() {
        return registryMap.keySet();
    }
}
