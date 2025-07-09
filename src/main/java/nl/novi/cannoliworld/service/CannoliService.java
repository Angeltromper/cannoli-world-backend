
package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.models.Cannoli;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CannoliService {
    List<Cannoli> getCannolis();

    List<Cannoli> findCannoliListByName(String cannoliName);

    List<Cannoli> findCannoliListByType(String cannoliType);

    Cannoli getCannoli(Long id);

    Cannoli createCannoli(Cannoli cannoli);

    void updateCannoli(Cannoli cannoli);

    void deleteCannoli(Long id);

    void assignImageToCannoli(String fileName, Long id);
}
