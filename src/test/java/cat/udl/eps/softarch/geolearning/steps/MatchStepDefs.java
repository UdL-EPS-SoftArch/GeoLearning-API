package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.repository.MatchRepository;
import cat.udl.eps.softarch.geolearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MatchStepDefs {
    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private MatchRepository matchRepository;
}
