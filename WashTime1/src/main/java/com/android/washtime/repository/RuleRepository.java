package com.android.washtime.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.android.washtime.entity.Rule;

@RepositoryRestResource
public interface RuleRepository extends JpaRepository<Rule, Integer> {

	Optional<Rule> findRuleByRuleHomeLocationNameAndRuleHomeNameAndFloor(String locationName, String name, int floor);
}
