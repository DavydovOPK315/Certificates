package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagRepository {
    private final EntityManager entityManager;

    @Transactional
    public void save(Tag tag) {
        entityManager.persist(tag);
    }

    @Transactional
    public void deleteById(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.remove(tag);
    }

    public List<Tag> findAll() {
        String jpql = "select t from Tag t";
        TypedQuery<Tag> query = entityManager.createQuery(jpql, Tag.class);
        return query.getResultList();
    }

    public List<Tag> findAll(int pageNumber, int pageSize) {
        String jpql = "select t from Tag t order by t.id";
        TypedQuery<Tag> query = entityManager.createQuery(jpql, Tag.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);
        return query.getResultList();
    }

    public Tag findById(long id) {
        return entityManager.find(Tag.class, id);
    }

    public Tag findMostWidelyUsedTagOfUserWithHighestCostOfAllOrders() {
        String jpql1 = "SELECT o2.user.id FROM Order o2 WHERE o2.price = " +
                "(SELECT max(o3.price) FROM Order o3)";
        String jpql2 = "SELECT t FROM Tag t JOIN t.certificates c " +
                "JOIN Order o ON o.certificate.id = c.id AND o.user.id = ?1 group by t.id";
        Long userId = entityManager.createQuery(jpql1, Long.class)
                .setMaxResults(1)
                .getSingleResult();
        return entityManager.createQuery(jpql2, Tag.class)
                .setParameter(1, userId)
                .setMaxResults(1)
                .getSingleResult();
    }
}