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

    public Tag findById(long id) {
        return entityManager.find(Tag.class, id);
    }
}