package com.hulkStore.aplicacion.service;

import java.util.Optional;

import javax.jws.soap.SOAPBinding.Use;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.hulkStore.aplicacion.dto.ChangePasswordForm;
import com.hulkStore.aplicacion.entity.Categoria;
import com.hulkStore.aplicacion.entity.User;
import com.hulkStore.aplicacion.repository.CategoriaRepository;
import com.hulkStore.aplicacion.repository.UserRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Iterable<Categoria> getAllCategorias(){
		return categoriaRepository.findAll();
	}

	private boolean checkCategoriaNameAvailable(Categoria categoria) throws Exception {
		Optional<Categoria> categoriaFound = categoriaRepository.findByName(categoria.getName());
		if (categoriaFound.isPresent()) {
			throw new Exception("Nombre del categoria no disponible");
		}
		return true;
	}

	@Override
	public Categoria createCategoria(Categoria categoria) throws Exception {
		if (checkCategoriaNameAvailable(categoria)) {
			categoria = categoriaRepository.save(categoria);
		}
		return categoria;
	}

	@Override
	public Categoria getCategoriaById(Long id) throws Exception {
		return categoriaRepository.findById(id).orElseThrow(() -> new Exception("El categoria para editar no existe"));

	}

	@Override
	public Categoria updateCategoria(Categoria fromCategoria) throws Exception {
		Categoria toCategoria = getCategoriaById(fromCategoria.getId());
		mapCategoria(fromCategoria, toCategoria);
		return categoriaRepository.save(toCategoria);
	}

	protected void mapCategoria(Categoria from, Categoria to) {
		to.setName(from.getName());
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteCategoria(Long id) throws Exception {
		Categoria categoria = getCategoriaById(id);
		categoriaRepository.delete(categoria);
	}
	

	public boolean isLoggedUserADMIN() {
		return loggedUserHasRole("ROLE_ADMIN");
	}

	public boolean loggedUserHasRole(String role) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = null;
		Object roles = null;
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
			roles = loggedUser.getAuthorities().stream()
					.filter(x -> role.equals(x.getAuthority()))
					.findFirst().orElse(null); // loggedUser = null;
		}
		return roles != null ? true : false;

	}

	



}
