package com.procorp.auth.dao;

import com.procorp.auth.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.username = :username")
	public User getUserByUsername(@Param("username") String username);
	@Query("SELECT u.token FROM User u WHERE u.username = :username")
	public String getTokenByUsername(@Param("username") String username);
}
