package com.example.CRUDApplication.repository;

import com.example.CRUDApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("select u from User u where u.id in(:idList)")
    List<User> findUsersByIdList(@Param("idList") List<Long> idList);
}
