package com.hulkStore.aplicacion.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hulkStore.aplicacion.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	//Convención sobre configuración
	public Optional<User> findByUsername(String username);
	
}
