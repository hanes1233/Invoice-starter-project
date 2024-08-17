package cz.itnetwork.controller;
//region imports
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.service.PersonService;
import cz.itnetwork.dto.statisticsDTO.PersonStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//endregion

@RestController
@RequestMapping("/api")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping({"/persons/{id}","/persons/{id}/"})
    public PersonDTO getPerson(@PathVariable Long id) {
        return personService.getPerson(id);
    }

    @PostMapping({"/persons","/persons/"})
    public PersonDTO addPerson(@RequestBody PersonDTO personDTO) {
        return  personService.addPerson(personDTO);
    }

    @GetMapping({"/persons","/persons/"})
    public List<PersonDTO> getPersons() {
        return personService.getAll();
    }

    @DeleteMapping("/persons/{personId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long personId) {
        personService.removePerson(personId);
    }

    @PutMapping({"/persons/{id}","/persons/{id}/"})
    public PersonDTO updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        return personService.updatePerson(id,personDTO);
    }

    @GetMapping({"/persons/statistics","/persons/statistics/"})
    public List<PersonStatistics> getPersonStatistics() {
        return personService.getStatistics();
    }

}

