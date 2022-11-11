package com.db.ramon;

public interface Mapper<T, E> {
  T mapToDomain(E entity);

  E mapToEntity(T domain);
}
