package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CertificateRepository {
    private final EntityManager entityManager;

    @Transactional
    public void save(Certificate certificate) {
        entityManager.persist(certificate);
    }

    @Transactional
    public void deleteById(long id) {
        Certificate certificate = entityManager.find(Certificate.class, id);
        entityManager.remove(certificate);
    }

    @Transactional
    public void update(Certificate certificate) {
        entityManager.merge(certificate);
    }

    public List<Certificate> findAll() {
        String jpql = "select c from Certificate c";
        TypedQuery<Certificate> query = entityManager.createQuery(jpql, Certificate.class);
        return query.getResultList();
    }

    public Certificate findById(long id) {
        return entityManager.find(Certificate.class, id);
    }

    public Certificate findByName(String name) {
        Certificate certificate = (Certificate) entityManager.createQuery("select c from Certificate c where c.name = ?1")
                .setParameter(1, name)
                .getSingleResult();
        return certificate;
    }

    public List<Certificate> findAllByNameLike(String name) {
        String value = "%" + name + "%";
        List<Certificate> certificate = entityManager.createQuery("select c from Certificate c where c.name like ?1")
                .setParameter(1, value)
                .getResultList();
        return certificate;
    }

    public List<Certificate> findAllByDescriptionLike(String description) {
        String value = "%" + description + "%";
        List<Certificate> certificate = entityManager.createQuery("select c from Certificate c where c.description like ?1")
                .setParameter(1, value)
                .getResultList();
        return certificate;
    }

    public List<Certificate> findAllByOrderByCreateDateDescNameDesc() {
        String jpql = "select c from Certificate c order by c.createDate DESC, c.name DESC";
        TypedQuery<Certificate> query = entityManager.createQuery(jpql, Certificate.class);
        return query.getResultList();
    }

    public List<Certificate> findAllByOrderByCreateDateAscNameAsc() {
        String jpql = "select c from Certificate c order by c.createDate ASC, c.name ASC";
        TypedQuery<Certificate> query = entityManager.createQuery(jpql, Certificate.class);
        return query.getResultList();
    }

    public List<Certificate> findAllByOrderByCreateDateDescNameAsc() {
        String jpql = "select c from Certificate c order by c.createDate DESC, c.name ASC";
        TypedQuery<Certificate> query = entityManager.createQuery(jpql, Certificate.class);
        return query.getResultList();
    }

    public List<Certificate> findAllByOrderByCreateDateAscNameDesc() {
        String jpql = "select c from Certificate c order by c.createDate ASC, c.name DESC";
        TypedQuery<Certificate> query = entityManager.createQuery(jpql, Certificate.class);
        return query.getResultList();
    }

    public List<Certificate> findAllByTagName(String tagName) {
        List<Certificate> certificate = entityManager.createQuery("select c from Certificate c JOIN c.tags t where t.name = ?1")
                .setParameter(1, tagName)
                .getResultList();
        return certificate;
    }
}
