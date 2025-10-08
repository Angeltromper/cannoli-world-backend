package nl.novi.cannoliworld.service;
import nl.novi.cannoliworld.exceptions.RecordNotFoundException;
import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getPersonList() {
        return personRepository.findAll();
    }

    @Override
    public List<Person> findPersonListByPersonFirstname(String personFirstname) {
        var list = personRepository.findByPersonFirstnameContainingIgnoreCase(personFirstname);
        if (list.isEmpty()) {
            throw new RecordNotFoundException("oeps er ging iets fout.. Gebruiker met voornaam " + personFirstname + " bestaat niet..");
        }
        return list;
    }

    @Override
    public List<Person> findPersonListByPersonLastname(String personLastname) {
        var list = personRepository.findByPersonLastnameContainingIgnoreCase(personLastname);
        if (list.isEmpty()) {
            throw new RecordNotFoundException("oeps er ging iets fout.. Gebruiker met achternaam " + personLastname + " bestaat niet..");
        }
        return list;
    }

    @Override
    public Person getPerson(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("persoon niet gevonden..."));
    }

   /*
    public Person getPerson(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            return person.get();
        } else {
            throw new RecordNotFoundException("persoon niet gevonden..");
        }
    }
*/
    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public Person updatePerson(Long id, Person changes) {
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("persoon bestaat niet..."));

        entity.setPersonFirstname(changes.getPersonFirstname());
        entity.setPersonLastname(changes.getPersonLastname());
        entity.setPersonStreetName(changes.getPersonStreetName());
        entity.setPersonHouseNumber(changes.getPersonHouseNumber());
        entity.setPersonHouseNumberAdd(changes.getPersonHouseNumberAdd());
        entity.setPersonCity(changes.getPersonCity());
        entity.setPersonZipcode(changes.getPersonZipcode());

        return personRepository.save(entity);
    }


    /*
    public void updatePerson(Long id, Person person) {
        Optional<Person> optionalPerson = personRepository.findById(id);

        if (optionalPerson.isEmpty()) {
            throw new RecordNotFoundException("persoon bestaat niet..");
        } else {
            Person person1 = optionalPerson.get();
            person1.setId(id);
            person1.setPersonFirstname(person.getPersonFirstname());
            person1.setPersonLastname(person.getPersonLastname());
            person1.setPersonStreetName(person.getPersonStreetName());
            person1.setPersonHouseNumber(person.getPersonHouseNumber());
            person1.setPersonHouseNumberAdd(person.getPersonHouseNumberAdd());
            person1.setPersonCity(person.getPersonCity());
            person1.setPersonZipcode(person.getPersonZipcode());
            personRepository.save(person1);
        }
    }
*/
    @Override
    public void deletePerson(Long id) {
        boolean exists = personRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("gebruiker met id " + id + " bestaat niet");
        }
        personRepository.deleteById(id);
    }

    @Override
    public Person getByUsername(String username) {
        return personRepository.findByUserUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("Geen persoon gekoppeld aan user " + username));
    }
}
