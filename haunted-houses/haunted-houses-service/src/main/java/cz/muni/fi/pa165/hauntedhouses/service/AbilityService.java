package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Petr Vitovsky
 */
@Service
public interface AbilityService {
    /**
     * Finds Ability with given ID in database
     * @param id ID of the Ability
     * @return Ability with given ID if it exists, null otherwise
     */
    Ability getAbilityById(Long id);

    /**
     * Finds Ability with given name in database
     * @param name Name of the Ability
     * @return Ability with given name if it exists, null otherwise
     */
    Ability getAbilityByName(String name);

    /**
     * Finds all Abilities in database
     * @return List of all Abilities
     */
    List<Ability> getAllAbilities();

    /**
     * Creates new Ability in the database
     * @param ability New Ability
     * @throws org.springframework.dao.DataAccessException if constraints are violated
     */
    void createAbility(Ability ability);

    /**
     * Deletes Ability from database
     * @param ability Ability for deletion
     */
    void deleteAbility(Ability ability);

    /**
     * Updates the Ability in the database
     * @param ability Ability for update
     * @throws org.springframework.dao.DataAccessException if constraints are violated
     * @return Updated Ability
     */
    Ability updateAbility(Ability ability);
}
