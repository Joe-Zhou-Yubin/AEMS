package com.projmanager.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projmanager.manager.models.Savings;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long>{
	
}
