package com.vluee.cloud.orgs.infrastructure.repo.jpa;

import com.vluee.cloud.commons.ddd.annotations.domain.DomainRepositoryImpl;
import com.vluee.cloud.commons.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
import com.vluee.cloud.orgs.core.hotel.domain.Hotel;
import com.vluee.cloud.orgs.core.hotel.domain.HotelRepository;

@DomainRepositoryImpl
public class JpaHotelRepository extends GenericJpaRepository<Hotel> implements HotelRepository {

}