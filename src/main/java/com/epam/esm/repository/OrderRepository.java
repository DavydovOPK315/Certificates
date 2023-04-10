package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager entityManager;

    @Transactional
    public void save(Order order) {
        entityManager.persist(order);
    }

    public Order findById(long id){
        return entityManager.find(Order.class, id);
    }
}
