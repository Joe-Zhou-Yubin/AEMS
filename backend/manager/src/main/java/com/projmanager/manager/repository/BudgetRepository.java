package com.projmanager.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projmanager.manager.models.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long>{
	List<Budget> findByMonthidAndCategory(Long monthid, String category);
}
