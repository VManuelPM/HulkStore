 package com.hulkStore.aplicacion.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hulkStore.aplicacion.dto.ChangePasswordForm;
import com.hulkStore.aplicacion.entity.Producto;
import com.hulkStore.aplicacion.entity.User;
import com.hulkStore.aplicacion.repository.CategoriaRepository;
import com.hulkStore.aplicacion.repository.ProductoRepository;
import com.hulkStore.aplicacion.repository.RolRepository;
import com.hulkStore.aplicacion.repository.UserRepository;
import com.hulkStore.aplicacion.service.ProductoService;
import com.hulkStore.aplicacion.service.UserService;

@Controller
public class ProductoController {

	@Autowired
	ProductoService productoService;
	
	@Autowired
	CategoriaRepository categoriasRepository;
	
	@Autowired
	UserRepository repository;
	
	
	
	@GetMapping("/productoForm")
	public String getProductoForm(Model model) throws Exception {
		model.addAttribute("productoForm", new Producto());
		model.addAttribute("productoList",productoService.getAllProductos());
		model.addAttribute("categorias", categoriasRepository.findAll());
		model.addAttribute("listTab","active");
		model.addAttribute("user", getLoggedUser());
		return "producto-form/producto-view";
	}	
	
	@PostMapping("/productoForm")
	public String createProducto(@Valid @ModelAttribute("productoForm")Producto producto, BindingResult result,
			ModelMap model) throws Exception {
		
		if(result.hasErrors()) {
			model.addAttribute("productoForm", producto);
			model.addAttribute("formTab","active");
		}else {
			try {
				productoService.createProducto(producto);
				model.addAttribute("productoForm", new Producto());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("productoForm", producto);
				model.addAttribute("formTab","active");
				model.addAttribute("productoList",productoService.getAllProductos());
				model.addAttribute("categorias",categoriasRepository.findAll());
			}
			
		}
		model.addAttribute("productoList",productoService.getAllProductos());
		model.addAttribute("categorias",categoriasRepository.findAll());
		model.addAttribute("user", getLoggedUser());
		return "producto-form/producto-view";
	}
	
	@GetMapping("/editProducto/{id}")
	public String getEditProductoForm(Model model, @PathVariable(name="id")Long id) throws Exception{
		Producto productoToEdit = productoService.getProductoById(id);
		model.addAttribute("productoForm", productoToEdit);
		model.addAttribute("productoList",productoService.getAllProductos());
		model.addAttribute("categorias",categoriasRepository.findAll());
		model.addAttribute("formTab","active");
		model.addAttribute("user", getLoggedUser());
		//Activa el tab del formulario.
		model.addAttribute("editMode",true);
		return "producto-form/producto-view";
	}
	
	
	@PostMapping("/editProducto")
	public String postEditProductoForm(@Valid @ModelAttribute("productoForm")Producto producto, BindingResult result,
			ModelMap model) throws Exception {
		
		if(result.hasErrors()) {
			model.addAttribute("productoForm", producto);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode",true);
			model.addAttribute("user", getLoggedUser());
			
		}else {
			try {
				productoService.updateProducto(producto);
				model.addAttribute("productoForm", new Producto());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("productoForm", producto);
				model.addAttribute("formTab","active");
				model.addAttribute("productoList",productoService.getAllProductos());
				model.addAttribute("categorias",categoriasRepository.findAll());
				model.addAttribute("editMode",true);
				model.addAttribute("user", getLoggedUser());
			}
			
		}
		model.addAttribute("productoList",productoService.getAllProductos());
		model.addAttribute("categorias",categoriasRepository.findAll());
		model.addAttribute("user", getLoggedUser());
		return "producto-form/producto-view";
	}
	
	@GetMapping("/productoForm/cancel")
	public String cancelEditProducto(ModelMap model) {
		return "redirect:/productoForm";
	}
	
	@GetMapping("/deleteProducto/{id}")
	public String deleteProducto(Model model, @PathVariable(name="id") Long id) throws Exception {
		try {
			productoService.deleteProducto(id);
		} catch (Exception e) {
			model.addAttribute("deleteError","El producto no puede ser eliminado");
		}
		return getProductoForm(model);
	}
	
	
	public User getLoggedUser() throws Exception {
		//Obtener el usuario logeado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;

		//Verificar que ese objeto traido de sesion es el usuario
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		}
		
		User myUser = repository.findByUsername(loggedUser.getUsername()).orElseThrow(() -> new Exception(""));
		
		return myUser;
	}

	

}
