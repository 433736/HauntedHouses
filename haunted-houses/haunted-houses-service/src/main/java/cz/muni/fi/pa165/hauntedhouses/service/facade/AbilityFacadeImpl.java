package cz.muni.fi.pa165.hauntedhouses.service.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.AbilityFacade;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Jan Horvath
 */

@Transactional
@Service
public class AbilityFacadeImpl implements AbilityFacade {

    private MappingService mappingService;
    private AbilityService abilityService;

    @Autowired
    public AbilityFacadeImpl(MappingService mappingService,
                             AbilityService abilityService) {
        this.mappingService = mappingService;
        this.abilityService = abilityService;
    }

    @Override
    public AbilityDTO getAbilityById(Long id) {
        Ability abilityById = abilityService.getAbilityById(id);
        return (abilityById == null) ? null : mappingService.mapTo(abilityById, AbilityDTO.class);
    }

    @Override
    public AbilityDTO getAbilityByName(String name) {
        Ability abilityByName = abilityService.getAbilityByName(name);
        return (abilityByName == null) ? null : mappingService.mapTo(abilityByName, AbilityDTO.class);
    }

    @Override
    public List<AbilityDTO> getAllAbilities() {
        return mappingService.mapTo(abilityService.getAllAbilities(), AbilityDTO.class);
    }

    @Override
    public Long createAbility(AbilityCreateDTO ability) {
        Ability newAbility = mappingService.mapTo(ability, Ability.class);
        abilityService.createAbility(newAbility);
        return newAbility.getId();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAbility(Long id) {
        abilityService.deleteAbility(abilityService.getAbilityById(id));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateAbility(AbilityDTO ability) {
        Ability abilityToUpdate = mappingService.mapTo(ability, Ability.class);
        abilityService.updateAbility(abilityToUpdate);
    }
}
