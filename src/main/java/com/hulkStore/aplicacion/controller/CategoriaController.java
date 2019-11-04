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
import com.hulkStore.aplicacion.entity.Categoria;
import com.hulkStore.aplicacion.entity.User;
import com.hulkStore.aplicacion.repository.CategoriaRepository;
import com.hulkStore.aplicacion.repository.RolRepository;
import com.hulkStore.aplicacion.repository.UserRepository;
import com.hulkStore.aplicacion.service.CategoriaService;
import com.hulkStore.aplicacion.service.UserService;

@Controller
public class CategoriaController {

	@Autowired
	CategoriaService categoriaService;
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	UserRepository repository;
	
	@GetMapping("/categoriaForm")
	public String getCategoriaForm(Model model) throws Exception {
		model.addAttribute("categoriaForm", new Categoria());
		model.addAttribute("categoriaList",categoriaService.getAllCategorias());
		model.addAttribute("categorias", categoriaRepository.findAll());
		model.addAttribute("listTab","active");
		model.addAttribute("user", getLoggedUser());
		return "categoria-form/categoria-view";
	}	
	
	@PostMapping("/categoriaForm")
	public String createCategoria(@Valid @ModelAttribute("categoriaForm")Categoria categoria, BindingResult result,
			ModelMap model) throws Exception {
		
		if(result.hasErrors()) {
			model.addAttribute("categoriaForm", categoria);
			model.addAttribute("formTab","active");
		}else {
			try {
				categoriaService.createCategoria(categoria);
				model.addAttribute("categoriaForm", new Categoria());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("categoriaForm", categoria);
				model.addAttribute("formTab","active");
				model.addAttribute("categoriaList",categoriaService.getAllCategorias());
				model.addAttribute("categoria",categoriaRepository.findAll());
			}
			
		}
		model.addAttribute("categoriaList",categoriaService.getAllCategorias());
		model.addAttribute("roles",categoriaRepository.findAll());
		model.addAttribute("user", getLoggedUser());
		return "categoria-form/categoria-view";
	}
	
	@GetMapping("/editCategoria/{id}")
	public String getEditCategoriaForm(Model model, @PathVariable(name="id")Long id) throws Exception{
		Categoria categoriaToEdit = categoriaService.getCategoriaById(id);
		model.addAttribute("categoriaForm", categoriaToEdit);
		model.addAttribute("categoriaList",categoriaService.getAllCategorias());
		model.addAttribute("categoria",categoriaRepository.findAll());
		model.addAttribute("formTab","active");
		model.addAttribute("user", getLoggedUser());
		//Activa el tab del formulario.
		model.addAttribute("editMode",true);
		return "categoria-form/categoria-view";
	}
	
	
	@PostMapping("/editCategoria")
	public String postEditCategoriaForm(@Valid @ModelAttribute("categoriaForm")Categoria categoria, BindingResult result,
			ModelMap model) throws Exception {
		
		if(result.hasErrors()) {
			model.addAttribute("categoriaForm", categoria);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode",true);
			model.addAttribute("user", getLoggedUser());
			
		}else {
			try {
				categoriaService.updateCategoria(categoria);
				model.addAttribute("categoriaForm", new Categoria());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("categoriaForm", categoria);
				model.addAttribute("formTab","active");
				model.addAttribute("categoriaList",categoriaService.getAllCategorias());
				model.addAttribute("roles",categoriaRepository.findAll());
				model.addAttribute("editMode",true);
				model.addAttribute("user", getLoggedUser());
			}
			
		}
		model.addAttribute("categoriaList",categoriaService.getAllCategorias());
		model.addAttribute("roles",categoriaRepository.findAll());
		model.addAttribute("user", getLoggedUser());
		return "categoria-form/categoria-view";
	}
	
	@GetMapping("/categoriaForm/cancel")
	public String cancelEditCategoria(ModelMap model) {
		return "redirect:/categoriaForm";
	}
	
	@GetMapping("/deleteCategoria/{id}")
	public String deleteCategoria(Model model, @PathVariable(name="id") Long id) throws Exception {
		try {
			categoriaService.deleteCategoria(id);
		} catch (Exception e) {
			model.addAttribute("deleteError","La categoria no puede ser eliminado");
		}
		return getCategoriaForm(model);
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
