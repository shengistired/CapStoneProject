/*
 * 
 * 
 * 
 * Done By: Odelia
 * 
 * 
 * 
 */
package com.cognixia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.model.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, String>{

	UserInfo findByUsernameAndPassword(String username, String password);
	
}
