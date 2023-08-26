package com.projmanager.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projmanager.manager.models.Month;


@Repository
public interface MonthRepository extends JpaRepository<Month, Long>{

}
