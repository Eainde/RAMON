package com.db.ramon.repositiory;

import com.db.ramon.Mapper;
import com.db.ramon.aggregate.OrderAggregate;
import com.db.ramon.entity.OrderEntity;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    private final Mapper<OrderAggregate, OrderEntity> mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRepository.class);

    OrderRepository(final EntityManager entityManager, Mapper<OrderAggregate, OrderEntity> mapper) {
        this.entityManager = entityManager;
        this.mapper = mapper;
    }

    public List<OrderAggregate> findAll() {
        final var criteria = entityManager.getCriteriaBuilder().createQuery(OrderEntity.class);
        criteria.select(criteria.from(OrderEntity.class));
        return entityManager.createQuery(criteria).getResultList().stream()
                .map(mapper::mapToDomain)
                .collect(Collectors.toList());
    }

    public OrderAggregate findById(long id) {
        try {
            OrderEntity entity = entityManager
                    .createQuery("SELECT u FROM OrderEntity u WHERE u.orderId=:id", OrderEntity.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return mapper.mapToDomain(entity);
        } catch (Exception e) {
            LOGGER.error(e + "");
            throw new RuntimeException("Cannot find the order by this ID");
        }
    }

    @Transactional
    public boolean add(List<OrderAggregate> domains) {
        try {
            List<OrderEntity> entity = domains.stream().map(mapper::mapToEntity).collect(Collectors.toList());
            entity.forEach(entityManager::persist);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error ", e);
            return false;
        }
    }
}
