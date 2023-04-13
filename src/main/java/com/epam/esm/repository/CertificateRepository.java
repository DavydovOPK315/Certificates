package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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

    public List<Certificate> findAll(int pageNumber, int pageSize) {
        return entityManager.createQuery("select c from Certificate c", Certificate.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public Certificate findById(long id) {
        return entityManager.find(Certificate.class, id);
    }

    public Certificate findByName(String name) {
        return entityManager.createQuery("select c from Certificate c where c.name = ?1", Certificate.class)
                .setParameter(1, name)
                .getSingleResult();
    }

    public List<Certificate> findAllByNameLike(String name, int pageNumber, int pageSize) {
        String value = "%" + name + "%";
        return entityManager.createQuery("select c from Certificate c where c.name like ?1", Certificate.class)
                .setParameter(1, value)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Certificate> findAllByDescriptionLike(String description, int pageNumber, int pageSize) {
        String value = "%" + description + "%";
        return entityManager.createQuery("select c from Certificate c where c.description like ?1", Certificate.class)
                .setParameter(1, value)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Certificate> findAllByOrderByCreateDateDescNameDesc(int pageNumber, int pageSize) {
        String jpql = "select c from Certificate c order by c.createDate DESC, c.name DESC";
        return entityManager.createQuery(jpql, Certificate.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Certificate> findAllByOrderByCreateDateAscNameAsc(int pageNumber, int pageSize) {
        String jpql = "select c from Certificate c order by c.createDate ASC, c.name ASC";
        return entityManager.createQuery(jpql, Certificate.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Certificate> findAllByOrderByCreateDateDescNameAsc(int pageNumber, int pageSize) {
        String jpql = "select c from Certificate c order by c.createDate DESC, c.name ASC";
        return entityManager.createQuery(jpql, Certificate.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Certificate> findAllByOrderByCreateDateAscNameDesc(int pageNumber, int pageSize) {
        String jpql = "select c from Certificate c order by c.createDate ASC, c.name DESC";
        return entityManager.createQuery(jpql, Certificate.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Certificate> findAllByTagName(String tagName, int pageNumber, int pageSize) {
        return entityManager.createQuery("select c from Certificate c JOIN c.tags t where t.name = ?1", Certificate.class)
                .setParameter(1, tagName)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Certificate> findAllByTags(List<Tag> tags, int pageNumber, int pageSize) {
        return entityManager.createQuery("select c from Certificate c where :tag member of c.tags and :tag1 member of c.tags", Certificate.class)
                .setParameter("tag", tags.get(0))
                .setParameter("tag1", tags.get(1))
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
