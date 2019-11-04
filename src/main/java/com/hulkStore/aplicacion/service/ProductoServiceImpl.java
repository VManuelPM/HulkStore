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
import com.hulkStore.aplicacion.entity.Producto;
import com.hulkStore.aplicacion.entity.User;
import com.hulkStore.aplicacion.repository.ProductoRepository;
import com.hulkStore.aplicacion.repository.UserRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Iterable<Producto> getAllProductos(){
		return productoRepository.findAll();
	}

	private boolean checkProductNameAvailable(Producto producto) throws Exception {
		Optional<Producto> userFound = productoRepository.findByProducto(producto.getProducto());
		if (userFound.isPresent()) {
			throw new Exception("Nombre del producto no disponible");
		}
		return true;
	}

	@Override
	public Producto createProducto(Producto producto) throws Exception {
		if (checkProductNameAvailable(producto)) {
			producto = productoRepository.save(producto);
		}
		return producto;
	}

	@Override
	public Producto getProductoById(Long id) throws Exception {
		return productoRepository.findById(id).orElseThrow(() -> new Exception("El producto para editar no existe"));

	}

	@Override
	public Producto updateProducto(Producto fromProducto) throws Exception {
		Producto toProducto = getProductoById(fromProducto.getId());
		mapProducto(fromProducto, toProducto);
		return productoRepository.save(toProducto);
	}

	protected void mapProducto(Producto from, Producto to) {
		to.setProducto(from.getProducto());
		to.setDescripcion(from.getDescripcion());
		to.setCategorias(from.getCategorias());
		to.setCantidad(from.getCantidad());
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteProducto(Long id) throws Exception {
		Producto producto = getProductoById(id);
		productoRepository.delete(producto);
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
