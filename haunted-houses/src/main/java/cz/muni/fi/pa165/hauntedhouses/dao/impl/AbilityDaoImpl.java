package cz.muni.fi.pa165.hauntedhouses.dao.impl;

import cz.muni.fi.pa165.hauntedhouses.dao.AbilityDao;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author David Hofman
 */
@Repository
@Transactional
public class AbilityDaoImpl implements AbilityDao {

    @PersistenceContext
    private static EntityManager em;

    @Override
    public void createAbility(Ability A) {
        em.persist(A);
    }

    @Override
    public Ability getAbilityById(Long id) {
        return em.find(Ability.class, id);
    }

    @Override
    public Ability getAbilityByName(String name) {
        try {
            return em.createQuery("SELECT a FROM Ability a WHERE a.name = :name", Ability.class)
                    .setParameter("name",name).getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Ability> getAllAbilities() {
        return em.createQuery("SELECT a FROM Ability a", Ability.class).getResultList();
    }

    @Override
    public Ability updateAbility(Ability A) {
        if(em.find(Ability.class, A.getId()) != null) return em.merge(A);
        return null;
    }

    @Override
    public void deleteAbility(Ability A) {
        em.remove(em.contains(A) ? A : em.merge(A));
    }
}
