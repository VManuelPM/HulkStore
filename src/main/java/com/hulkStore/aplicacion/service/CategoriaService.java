package com.hulkStore.aplicacion.service;

import javax.validation.Valid;

import com.hulkStore.aplicacion.dto.ChangePasswordForm;
import com.hulkStore.aplicacion.entity.Categoria;
import com.hulkStore.aplicacion.entity.User;

public interface CategoriaService {
	
	//Iterable --> Puede ser cualquier colecci�n
	public Iterable<Categoria> getAllCategorias();

	public Categoria createCategoria(Categoria categoria) throws Exception;
	
	public Categoria getCategoriaById(Long id) throws Exception;
	
	public Categoria updateCategoria(Categoria categoria) throws Exception;

	public void deleteCategoria(Long id) throws Exception;
	
}
