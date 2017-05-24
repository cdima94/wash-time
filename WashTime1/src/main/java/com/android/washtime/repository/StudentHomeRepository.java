package com.android.washtime.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.android.washtime.entity.StudentHome;

@RepositoryRestResource
public interface StudentHomeRepository extends JpaRepository<StudentHome, Integer> {

	List<StudentHome> findByLocationName(String locationName);
	Optional<StudentHome> findByLocationNameAndName(String locationName, String name);
}
