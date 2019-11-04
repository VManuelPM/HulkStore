package com.hulkStore.aplicacion.service;

import javax.validation.Valid;

import com.hulkStore.aplicacion.dto.ChangePasswordForm;
import com.hulkStore.aplicacion.entity.Producto;
import com.hulkStore.aplicacion.entity.User;

public interface ProductoService {
	
	//Iterable --> Puede ser cualquier colecci�n
	public Iterable<Producto> getAllProductos();

	public Producto createProducto(Producto producto) throws Exception;
	
	public Producto getProductoById(Long id) throws Exception;
	
	public Producto updateProducto(Producto producto) throws Exception;

	public void deleteProducto(Long id) throws Exception;
	
}
