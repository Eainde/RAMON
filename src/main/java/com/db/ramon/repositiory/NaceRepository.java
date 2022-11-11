package com.db.ramon.repositiory;

import com.db.ramon.Mapper;
import com.db.ramon.aggregate.NaceAggregate;
import com.db.ramon.entity.NaceEntity;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class NaceRepository {
  @PersistenceContext private final EntityManager entityManager;
  private final Mapper<NaceAggregate, NaceEntity> mapper;
  private static final Logger LOGGER = LoggerFactory.getLogger(NaceRepository.class);

  NaceRepository(final EntityManager entityManager, Mapper<NaceAggregate, NaceEntity> mapper) {
    this.entityManager = entityManager;
    this.mapper = mapper;
  }

  public List<NaceEntity> findAll() {
    final var criteria = entityManager.getCriteriaBuilder().createQuery(NaceEntity.class);
    criteria.select(criteria.from(NaceEntity.class));
    return entityManager.createQuery(criteria).getResultList();
  }

  public NaceEntity findById(long id) {
    try {
      return entityManager
          .createQuery("SELECT u FROM NaceEntity u WHERE u.orderId=:id", NaceEntity.class)
          .setParameter("id", id)
          .getSingleResult();
    } catch (Exception e) {
      LOGGER.error(e + "");
      throw new RuntimeException("Cannot find the Nace by this ID");
    }
  }

  @Transactional
  public boolean add(List<NaceAggregate> domains) {
    try {
      List<NaceEntity> entity =
          domains.stream().map(mapper::mapToEntity).collect(Collectors.toList());
      entity.forEach(entityManager::persist);
      return true;
    } catch (Exception e) {
      LOGGER.error("Error ", e);
      return false;
    }
  }
}
