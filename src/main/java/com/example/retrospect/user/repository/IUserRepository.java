package com.example.retrospect.user.repository;

import com.example.retrospect.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository <UserEntity, Integer> {
  @Query("SELECT u FROM UserEntity u WHERE u.userEmail = :userEmail")
    UserEntity findByEmailId(@Param("userEmail") String userEmail);



}
