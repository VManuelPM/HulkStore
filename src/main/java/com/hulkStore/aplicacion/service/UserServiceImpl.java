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
import com.hulkStore.aplicacion.entity.User;
import com.hulkStore.aplicacion.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	private boolean checkUsernameAvailable(User user) throws Exception {
		Optional<User> userFound = userRepository.findByUsername(user.getUsername());
		if (userFound.isPresent()) {
			throw new Exception("Username no disponible");
		}
		return true;
	}

	private boolean checkPasswordValid(User user) throws Exception {
		if (user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) {
			throw new Exception("Confirmacion Password es obligatorio");
		}
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("Password y Confirmacion Password no son iguales");
		}
		return true;
	}

	@Override
	public User createUser(User user) throws Exception {
		if (checkUsernameAvailable(user) && checkPasswordValid(user)) {
			
			String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodePassword);

			user = userRepository.save(user);
		}
		return user;
	}

	@Override
	public User getUserById(Long id) throws Exception {
		return userRepository.findById(id).orElseThrow(() -> new Exception("El usuario para editar no existe"));

	}

	@Override
	public User updateUser(User fromUser) throws Exception {
		User toUser = getUserById(fromUser.getId());
		mapUser(fromUser, toUser);
		return userRepository.save(toUser);
	}

	protected void mapUser(User from, User to) {
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteUser(Long id) throws Exception {
		User user = getUserById(id);
		userRepository.delete(user);
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

	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		User user = getUserById(form.getId());

		if (!isLoggedUserADMIN() && !user.getPassword().equals(form.getCurrentPassword())) {
			System.out.println("entre 1");
			throw new Exception("Password actual invalido");
		}

		if (user.getPassword().equals(form.getNewPassword())) {
			System.out.println("entre 2");
			throw new Exception("Password nuevo debe ser diferente al password actual");
		}

		if (!form.getNewPassword().equals(form.getConfirmPassword())) {
			System.out.println("entre 3");
			throw new Exception("Password nuevo y password actual no coinciden");
		}

		String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setPassword(encodePassword);

		return userRepository.save(user);
	}
	
	private User getLoggedUser() throws Exception {
		//Obtener el usuario logeado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;

		//Verificar que ese objeto traido de sesion es el usuario
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		}
		
		User myUser = userRepository
				.findByUsername(loggedUser.getUsername()).orElseThrow(() -> new Exception("Problemas obteniendo usuario de sesion."));
		
		return myUser;
	}


}
