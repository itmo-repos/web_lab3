package com.lab3.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.Collection;
import com.lab3.entity.ResultEntity;

public class ResultDAOImpl implements ResultDAO {

    private final EntityManager entityManager = JPAUtils.getFactory().createEntityManager();

    @Override
    @Transactional
    public void addNewResult(ResultEntity result) {
        entityManager.persist(result);
    }

    @Override
    @Transactional
    public void updateResult(Long result_id, ResultEntity result) {
        entityManager.merge(result);
    }

    @Override
    public ResultEntity getResultById(Long result_id) {
        return entityManager.getReference(ResultEntity.class, result_id);
    }

    @Override
    public Collection<ResultEntity> getAllResults() {
        var cm = entityManager.getCriteriaBuilder().createQuery(ResultEntity.class);
        Root<ResultEntity> root = cm.from(ResultEntity.class);
        return entityManager.createQuery(cm.select(root)).getResultList();
    }

    @Override
    @Transactional
    public void deleteResult(ResultEntity result) {
        entityManager.remove(result);
    }

    @Override
    @Transactional
    public void clearResults() {
        try {
            Query query = entityManager.createQuery("DELETE FROM ResultEntity r");
            query.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.clear();
        }
    }
}
