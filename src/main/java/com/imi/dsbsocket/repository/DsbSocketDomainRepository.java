package com.imi.dsbsocket.repository;

import com.imi.dsbsocket.entity.DsbSocketDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DsbSocketDomainRepository extends JpaRepository<DsbSocketDomain, String> {

}
