package nl.novi.cannoliworld.service;
import nl.novi.cannoliworld.models.Cannoli;
import java.util.List;

public interface CannoliService {
    List<Cannoli> getCannolis();
    List<Cannoli> findCannoliListByName(String cannoliName);
    List<Cannoli> findCannoliListByType(String cannoliType);
    Cannoli getCannoli(Long id);
    Cannoli createCannoli(Cannoli cannoli);
    Cannoli updateCannoli(Long id, Cannoli cannoli);
    void deleteCannoli(Long id);
    void assignImageToCannoli(String fileName, Long id);
}
