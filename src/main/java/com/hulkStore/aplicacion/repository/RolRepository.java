package com.hulkStore.aplicacion.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hulkStore.aplicacion.entity.Role;


@Repository
public interface RolRepository extends CrudRepository<Role, Long>{

}
