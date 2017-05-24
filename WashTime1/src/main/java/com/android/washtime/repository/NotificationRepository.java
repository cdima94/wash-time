package com.android.washtime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.android.washtime.entity.Notification;

@RepositoryRestResource
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	List<Notification> findByStudentUserName(String userName);
}
