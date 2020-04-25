package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.dao.AbilityDao;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.*;
import java.util.List;

/**
 * @author Jan Horvath
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class AbilityDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private AbilityDao abilityDao;

    private Ability defensiveAbility;
    private Ability defensiveAbilityCopy;
    private Ability offensiveAbility;
    private Ability offensiveAbilityCopy;

    @BeforeMethod
    public void setup() {
        defensiveAbility = new Ability();
        defensiveAbility.setName("Defensive ability");
        defensiveAbility.setDescription("Defensive ability description");

        defensiveAbilityCopy = new Ability();
        defensiveAbilityCopy.setName("Defensive ability");
        defensiveAbilityCopy.setDescription("Defensive ability copy description");

        offensiveAbility = new Ability();
        offensiveAbility.setName("Offensive ability");
        offensiveAbility.setDescription("Offensive ability description");

        offensiveAbilityCopy = new Ability();
        offensiveAbilityCopy.setName("Offensive ability");
        offensiveAbilityCopy.setDescription("Offensive ability copy description");
    }

    @Test
    public void createAndGetTest() {
        List<Ability> allAbilities = abilityDao.getAllAbilities();
        Assert.assertEquals(allAbilities.size(), 0);

        abilityDao.createAbility(defensiveAbility);

        allAbilities = abilityDao.getAllAbilities();
        Assert.assertEquals(allAbilities.size(), 1);

        Ability foundDefensiveAbility = abilityDao.getAbilityById(defensiveAbility.getId()).get();
        Assert.assertEquals(foundDefensiveAbility, defensiveAbility);
        foundDefensiveAbility = abilityDao.getAbilityByName("Defensive ability").get();
        Assert.assertEquals(foundDefensiveAbility, defensiveAbility);

        abilityDao.createAbility(offensiveAbility);

        allAbilities = abilityDao.getAllAbilities();
        Assert.assertEquals(allAbilities.size(), 2);

        Ability foundOffensiveAbility = abilityDao.getAbilityById(offensiveAbility.getId()).get();
        Assert.assertEquals(foundOffensiveAbility, offensiveAbility);
        foundOffensiveAbility = abilityDao.getAbilityByName("Offensive ability").get();
        Assert.assertEquals(foundOffensiveAbility, offensiveAbility);
    }

    @Test (expectedExceptions = PersistenceException.class)
    public void createAbilityWithTheSameNameTwiceTest() {
        abilityDao.createAbility(defensiveAbility);
        abilityDao.createAbility(defensiveAbilityCopy);
        abilityDao.getAllAbilities();
    }

    @Test
    public void getNonExistingAbilityByIdTest() {
        abilityDao.createAbility(defensiveAbility);
        abilityDao.createAbility(offensiveAbility);

        Long nonExistingAbilityId = defensiveAbility.getId() + offensiveAbility.getId();
        Assert.assertTrue(abilityDao.getAbilityById(nonExistingAbilityId).isEmpty());
    }

    @Test
    public void getNonExistingAbilityByNameTest() {
        abilityDao.createAbility(defensiveAbility);
        abilityDao.createAbility(offensiveAbility);
        Assert.assertTrue(abilityDao.getAbilityByName("non-existing ability").isEmpty());
    }

    @Test
    public void getAllAbilitiesTest() {
        List<Ability> allAbilities = abilityDao.getAllAbilities();
        Assert.assertEquals(allAbilities.size(), 0);

        abilityDao.createAbility(defensiveAbility);

        allAbilities = abilityDao.getAllAbilities();
        Assert.assertEquals(allAbilities.size(), 1);
        Assert.assertEquals(allAbilities.get(0), defensiveAbilityCopy);

        abilityDao.createAbility(offensiveAbility);
        allAbilities = abilityDao.getAllAbilities();
        Assert.assertEquals(allAbilities.size(), 2);
        Assert.assertTrue(allAbilities.contains(defensiveAbilityCopy));
        Assert.assertTrue(allAbilities.contains(offensiveAbilityCopy));
    }

    @Test
    public void updateAbilityTest() {
        abilityDao.createAbility(defensiveAbility);
        defensiveAbility.setDescription("New description");
        abilityDao.updateAbility(defensiveAbility);

        Assert.assertEquals(abilityDao.getAbilityByName("Defensive ability").get().getDescription(), "New description");

        defensiveAbility.setName("New defensive ability");
        abilityDao.updateAbility(defensiveAbility);

        Assert.assertTrue(abilityDao.getAbilityByName("Defensive ability").isEmpty());
        Assert.assertEquals(abilityDao.getAbilityByName("New defensive ability").get().getDescription(), "New description");
    }

    @Test
    public void updateNonExistingAbilityTest() {
        abilityDao.createAbility(defensiveAbility);
        abilityDao.createAbility(offensiveAbility);

        Ability nonExistingAbility = new Ability();
        nonExistingAbility.setName("Non-existing ability");
        nonExistingAbility.setDescription("Description");

        Assert.assertTrue(abilityDao.updateAbility(nonExistingAbility).isEmpty());
    }

    @Test
    public void updateDeletedAbilityTest() {
        abilityDao.createAbility(defensiveAbility);
        abilityDao.createAbility(offensiveAbility);
        defensiveAbility = abilityDao.getAbilityByName("Defensive ability").get();

        abilityDao.deleteAbility(defensiveAbility);

        defensiveAbility.setDescription("New description");

        Assert.assertTrue(abilityDao.updateAbility(defensiveAbility).isEmpty());
    }

    @Test
    public void deleteAbilityTest() {
        abilityDao.createAbility(defensiveAbility);
        abilityDao.createAbility(offensiveAbility);

        Assert.assertEquals(abilityDao.getAllAbilities().size(), 2);

        abilityDao.deleteAbility(defensiveAbility);

        List<Ability> allAbilities = abilityDao.getAllAbilities();
        Assert.assertEquals(allAbilities.size(), 1);
        Assert.assertEquals(allAbilities.get(0), offensiveAbilityCopy);

        abilityDao.deleteAbility(defensiveAbility);

        allAbilities = abilityDao.getAllAbilities();
        Assert.assertEquals(allAbilities.size(), 1);
        Assert.assertEquals(allAbilities.get(0), offensiveAbilityCopy);

        abilityDao.deleteAbility(offensiveAbility);

        Assert.assertEquals(abilityDao.getAllAbilities().size(), 0);
    }

}
