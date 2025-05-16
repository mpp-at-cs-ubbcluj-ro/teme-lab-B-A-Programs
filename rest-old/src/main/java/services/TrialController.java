package services;

import domain.Trial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.TrialRepository;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/competition/trials")
public class TrialController {
    @Autowired
    TrialRepository trialRepository;

    @GetMapping
    public Collection<Trial> get() {
        return trialRepository.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Trial trial = trialRepository.getById(id);
        if (trial == null)
            return new ResponseEntity<>("Trial not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(trial, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Trial trial) {
        try {
            return new ResponseEntity<>(trialRepository.add(trial), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Trial trial, @PathVariable Long id) {
        if (trialRepository.getById(id) == null)
            return new ResponseEntity<>("Trial not found", HttpStatus.NOT_FOUND);
        try {
            trialRepository.update(trial, id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(trialRepository.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus remove(@PathVariable Long id) {
        if (trialRepository.getById(id) == null)
            return HttpStatus.NOT_FOUND;
        trialRepository.delete(id);
        return HttpStatus.OK;
    }
}
