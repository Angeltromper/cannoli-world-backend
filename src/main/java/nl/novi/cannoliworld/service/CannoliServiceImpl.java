package nl.novi.cannoliworld.service;

import nl.novi.cannoliworld.exeptions.RecordNotFoundException;
import nl.novi.cannoliworld.models.Cannoli;
import nl.novi.cannoliworld.repositories.CannoliRepository;
import nl.novi.cannoliworld.repositories.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class CannoliServiceImpl implements CannoliService {

    private final CannoliRepository cannoliRepository;

    private PhotoService photoService;

    private final FileUploadRepository fileUploadRepository;


    @Autowired
    public CannoliServiceImpl(CannoliRepository cannoliRepository,
                              FileUploadRepository fileUploadRepository) {
        this.cannoliRepository = cannoliRepository;
        this.fileUploadRepository = fileUploadRepository;
    }

    @Override
    public List<Cannoli> getCannolis() { return cannoliRepository.findAll(); }


    @Override
    public Cannoli getCannoli(Long id) {
        Optional<Cannoli> cannoli = cannoliRepository.findById(id);

        if (cannoli.isPresent()) {
            return cannoli.get();
        } else {
            throw new RecordNotFoundException("Cannoli niet gevonden");
        }
    }

    @Override
    public List<Cannoli> findCannoliListByName(String cannoliName) {
        var optionalCannoliList = cannoliRepository.findByCannoliNameContainingIgnoreCase(cannoliName);

        if (optionalCannoliList.isEmpty()) {
            throw new RecordNotFoundException("geen cannoli gevonden met de naam" + cannoliName);
        }

        return optionalCannoliList;
    }

    @Override
    public List<Cannoli> findCannoliListByType(String cannoliType) {
        var optionalCannoliList = cannoliRepository.findByCannoliTypeContainingIgnoreCase(cannoliType);

        if (optionalCannoliList.isEmpty()) {
            throw new RecordNotFoundException("geen cannoli's gevonden");
        }

        return optionalCannoliList;
    }

    @Override
    public Cannoli createCannoli(Cannoli cannoli) {

        cannoli.setId(cannoli.getId());
        cannoli.setCannoliName(cannoli.getCannoliName());
        cannoli.setCannoliType(cannoli.getCannoliType());
        cannoli.setDescription(cannoli.getDescription());
        cannoli.setIngredients(cannoli.getIngredients());
        cannoli.setPrice(cannoli.getPrice());

        return cannoliRepository.save(cannoli);
    }

    @Override
    public void updateCannoli(Cannoli cannoli) {

        Optional<Cannoli> optionalCannoli = cannoliRepository.findById(cannoli.getId());

        if (optionalCannoli.isEmpty()) {
            throw new RecordNotFoundException("cannoli niet gevonden..");
        } else {

            Cannoli cannoli1 = optionalCannoli.get();
            cannoli1.setId(cannoli.getId());
            cannoli1.setCannoliName(cannoli.getCannoliName());
            cannoli1.setCannoliType(cannoli.getCannoliType());
            cannoli1.setDescription(cannoli.getDescription());
            cannoli1.setIngredients(cannoli.getIngredients());
            cannoli1.setPrice(cannoli.getPrice());

            cannoliRepository.save(cannoli1);


        }
    }

    @Override
    public void deleteCannoli(Long id) {
        cannoliRepository.deleteById(id);
    }


    @Override
    public void assignPictureToCannoli(String fileName, Long id) {

        var optionalCannoli = cannoliRepository.findById(id);
        var optionalPicture = fileUploadRepository.findByFileName(fileName);

        if (optionalCannoli.isPresent() && optionalPicture.isPresent()) {
            var product = optionalCannoli.get();
            var picture = optionalPicture.get();

            product.setPicture(picture);

            cannoliRepository.save(product);

        } else {

            throw new RecordNotFoundException();
        }
    }

    public List<Cannoli> findProductListByType(String productType) {
        return null;
    }
}




