package com.projmanager.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projmanager.manager.models.UserInput;

@Repository
public interface UserinputRepository extends JpaRepository<UserInput, Long>{

}
