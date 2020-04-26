package cz.muni.fi.pa165.hauntedhouses.service;

import com.github.javafaker.Faker;
import cz.muni.fi.pa165.hauntedhouses.dao.SpecterDao;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * @author Jan Horvath
 */

@Service
public class SpecterServiceImpl implements SpecterService {

    private static final int MAX_SPECTER_ABILITIES = 3;

    @Autowired
    AbilityService abilityService;

    @Autowired
    GameInstanceService gameInstanceService;

    @Autowired
    SpecterDao specterDao;

    @Override
    public Specter generateRandomSpecter() {
        Faker faker = new Faker();
        Random random = new Random();

        Specter specter = new Specter();
        specter.setName(faker.witcher().monster());
        specter.setStartOfHaunting(LocalTime.of(random.nextInt(24), 0));
        specter.setEndOfHaunting(LocalTime.of(random.nextInt(24), 0));
        specter.setDescription("No description");

        List<Ability> allAbilities = abilityService.getAllAbilities();
        if (allAbilities.size() < MAX_SPECTER_ABILITIES) {
            specter.setAbilities(allAbilities);
        } else {
            Collections.shuffle(allAbilities);
            specter.setAbilities(allAbilities.subList(0, random.nextInt(MAX_SPECTER_ABILITIES)));
        }

        specterDao.createSpecter(specter);
        return specter;
    }

    @Override
    public Specter getByGameInstanceId(Long gameInstanceId) {
        GameInstance gameInstanceById = gameInstanceService.getGameInstanceById(gameInstanceId);
        if (gameInstanceById == null) {
            throw new DataRetrievalFailureException("No such game instance found");
        }
        return specterDao.getSpecterByGameInstance(gameInstanceById);
    }
}
