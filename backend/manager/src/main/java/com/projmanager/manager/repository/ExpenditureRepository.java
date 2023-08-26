package com.projmanager.manager.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projmanager.manager.models.Expenditure;

@Repository
public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {
	List<Expenditure> findByMonthid(Long monthId);
	List<Expenditure> findByMonthidAndCategory(Long monthid, String category);
}
