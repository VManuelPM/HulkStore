package com.hulkStore.aplicacion.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hulkStore.aplicacion.entity.Producto;
import com.hulkStore.aplicacion.entity.User;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {
	
	//Convenci�n sobre configuraci�n
	public Optional<Producto> findByProducto(String producto);
	
}
