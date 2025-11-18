package com.lab3.db;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Collection;
import com.lab3.entity.ResultEntity;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ResultDAOImpl implements ResultDAO {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @Override
    public void addNewResult(ResultEntity result) {
        entityManager.persist(result);
        entityManager.flush();
    }


    @Override
    public void updateResult(Long result_id, ResultEntity result) {
        entityManager.merge(result);
    }

    @Override
    public ResultEntity getResultById(Long result_id) {
        return entityManager.find(ResultEntity.class, result_id);
    }

    @Override
    public Collection<ResultEntity> getAllResults() {
        var cm = entityManager.getCriteriaBuilder().createQuery(ResultEntity.class);
        var root = cm.from(ResultEntity.class);
        return entityManager.createQuery(cm.select(root)).getResultList();
    }

    @Override
    public void deleteResult(ResultEntity result) {
        entityManager.remove(entityManager.contains(result) ? result : entityManager.merge(result));
    }

    @Override
    public void clearResults() {
        try {
            var query = entityManager.createQuery("DELETE FROM ResultEntity r");
            query.executeUpdate();
            entityManager.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.clear();
        }
    }
}
