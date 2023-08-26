package com.projmanager.manager.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projmanager.manager.models.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
	List<Income> findByMonthid(Long monthId);
	List<Income> findByMonthidAndCategory(Long monthid, String category);

}
