package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import org.springframework.stereotype.Service;

/**
 *@author David Hofman
 */

@Service
public interface GameInstanceService {

    GameInstance getGameInstanceById(Long id);

    GameInstance getGameInstanceByPlayerId(Long id);

    void createGameInstance(GameInstance gameInstance);

    void createGameInstanceWithRandomSpecter(GameInstance gameInstance);

    GameInstance updateGameInstance(GameInstance gameInstance);

    void deleteGameInstance(GameInstance gameInstance);
}

