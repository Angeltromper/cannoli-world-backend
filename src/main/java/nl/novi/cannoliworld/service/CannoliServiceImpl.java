package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.exeptions.RecordNotFoundException;
import nl.novi.cannoliworld.models.Cannoli;
import nl.novi.cannoliworld.repositories.CannoliRepository;
import nl.novi.cannoliworld.repositories.FileUploadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CannoliServiceImpl implements CannoliService {
    private final CannoliRepository cannoliRepository;
    private final FileUploadRepository fileUploadRepository;

    public CannoliServiceImpl(CannoliRepository cannoliRepository,
                              FileUploadRepository fileUploadRepository) {
        this.cannoliRepository = cannoliRepository;
        this.fileUploadRepository = fileUploadRepository;
    }

    @Override
    public List<Cannoli> getCannolis() { return cannoliRepository.findAll(); }

    @Override
    public Cannoli getCannoli(Long id) {
        return cannoliRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Cannoli niet gevonden"));
    }

    @Override
    public List<Cannoli> findCannoliListByName(String cannoliName) {
        var list = cannoliRepository.findByCannoliNameContainingIgnoreCase(cannoliName);
        if (list.isEmpty()) throw new RecordNotFoundException("geen cannoli gevonden met de naam " + cannoliName);
        return list;
    }

    @Override
    public List<Cannoli> findCannoliListByType(String cannoliType) {
        var list = cannoliRepository.findByCannoliTypeContainingIgnoreCase(cannoliType);
        if (list.isEmpty()) throw new RecordNotFoundException("geen cannoli's gevonden");
        return list;
    }

    @Override
    public Cannoli createCannoli(Cannoli cannoli) { return cannoliRepository.save(cannoli); }

    @Override
    public Cannoli updateCannoli(Long id, Cannoli cannoli) {
        var existing = cannoliRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("cannoli niet gevonden.."));
        existing.setCannoliName(cannoli.getCannoliName());
        existing.setCannoliType(cannoli.getCannoliType());
        existing.setDescription(cannoli.getDescription());
        existing.setIngredients(cannoli.getIngredients());
        existing.setPrice(cannoli.getPrice());
        return cannoliRepository.save(existing);
    }

    @Override
    public void deleteCannoli(Long id) {
        if (!cannoliRepository.existsById(id)) {
            throw new RecordNotFoundException("Cannoli %d not found".formatted(id));
        }
        cannoliRepository.deleteById(id);
    }

    @Override
    public void assignImageToCannoli(String fileName, Long id) {
        var cannoli = cannoliRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("cannoli niet gevonden"));
        var image = fileUploadRepository.findByFileName(fileName).orElseThrow(() -> new RecordNotFoundException("image niet gevonden"));
        cannoli.setImage(image);
        cannoliRepository.save(cannoli);
    }
}
