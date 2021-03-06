package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.GameInstanceDao;
import cz.muni.fi.pa165.hauntedhouses.dao.PlayerDao;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * @author David Hofman
 */

@Service
public class GameInstanceServiceImpl implements GameInstanceService {

    private GameInstanceDao gameInstanceDao;
    private PlayerDao playerDao;
    private SpecterService specterService;
    private HouseService houseService;

    @Autowired
    public GameInstanceServiceImpl(GameInstanceDao gameInstanceDao,
                                   PlayerDao playerDao,
                                   @Lazy SpecterService specterService,
                                   HouseService houseService) {
        this.gameInstanceDao = gameInstanceDao;
        this.playerDao = playerDao;
        this.specterService = specterService;
        this.houseService = houseService;
    }

    @Override
    public GameInstance getGameInstanceById(Long id) {
        return gameInstanceDao.getGameInstanceById(id);
    }

    @Override
    public GameInstance getGameInstanceByPlayerId(Long id) {
        return playerDao.getPlayerById(id).getGameInstance();
    }

    @Override
    public List<GameInstance> getAllGameInstances() {
        return gameInstanceDao.getAllGameInstances();
    }

    @Override
    public void createGameInstance(GameInstance gameInstance) {
        Specter specter = gameInstance.getSpecter();
        specter.setHouse(houseService.getRandomHouse());
        gameInstance.setHouses(new HashSet<>(houseService.getSubsetWithSpecificHouse(specter.getHouse().getId())));
        gameInstanceDao.createGameInstance(gameInstance);
    }

    @Override
    public void createGameInstanceWithRandomSpecter(GameInstance gameInstance) {
        Specter specter = specterService.generateRandomSpecter();
        gameInstance.setSpecter(specter);
        specter.setGameInstance(gameInstance);
        specter.setHouse(houseService.getRandomHouse());
        gameInstance.setHouses(new HashSet<>(houseService.getSubsetWithSpecificHouse(specter.getHouse().getId())));
        gameInstanceDao.createGameInstance(gameInstance);
    }

    @Override
    public GameInstance updateGameInstance(GameInstance gameInstance) {
        return gameInstanceDao.updateGameInstance(gameInstance);
    }

    @Override
    public void deleteGameInstance(GameInstance gameInstance) {
        System.err.println("Deleting GI through SERVICE");
        gameInstanceDao.deleteGameInstance(gameInstance);
        List<GameInstance> allGameInstances = gameInstanceDao.getAllGameInstances();
    }
}

