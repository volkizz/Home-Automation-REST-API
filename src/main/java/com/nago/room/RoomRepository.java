package com.nago.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {
  @RestResource(rel = "nameStartsWith", path = "nameStartsWith")
  Page<Room> findByNameStartsWith(@Param("name") String name, Pageable page);

  @RestResource(rel = "lessThanArea", path = "lessThanArea")
  Page<Room> findByAreaLessThan(@Param("area") int area, Pageable page);

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  void delete(Long aLong);

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  void delete(Room entity);


}
