package com.android.washtime.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.android.washtime.entity.Student;

@RepositoryRestResource
public interface StudentRepository extends JpaRepository<Student, Integer> {

	Optional<Student> findByFirstName(String firstName);
	Optional<Student> findByUserName(String userName);
}
