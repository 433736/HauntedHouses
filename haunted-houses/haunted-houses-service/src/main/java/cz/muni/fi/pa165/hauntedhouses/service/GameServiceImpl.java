package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author Petr Vitovsky
 */
@Service
public class GameServiceImpl implements GameService {

    private HouseService houseService;

    @Autowired
    public GameServiceImpl(HouseService houseService) {
        this.houseService = houseService;
    }

    @Override
    public boolean checkAnswer(House house, GameInstance instance) {
        if (house == null || instance == null) {
            throw new IllegalArgumentException("Parameters cannot be null!");
        }

        instance.setBanishesAttempted(instance.getBanishesAttempted() + 1);

        House correctHouse = instance.getSpecter().getHouse();
        if (!house.equals(correctHouse)) {
            return false;
        }

        instance.setBanishesRequired(instance.getBanishesRequired() - 1);
        instance.getSpecter().setHouse(houseService.getRandomHouse());
        return true;
    }
}
