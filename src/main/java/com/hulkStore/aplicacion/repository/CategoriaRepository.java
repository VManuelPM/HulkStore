package com.hulkStore.aplicacion.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hulkStore.aplicacion.entity.Categoria;
import com.hulkStore.aplicacion.entity.Producto;
import com.hulkStore.aplicacion.entity.Role;


@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Long>{
	
	public Optional<Categoria> findByName(String categoria);
	
}
