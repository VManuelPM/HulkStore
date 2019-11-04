package com.hulkStore.aplicacion.service;

import javax.validation.Valid;

import com.hulkStore.aplicacion.dto.ChangePasswordForm;
import com.hulkStore.aplicacion.entity.User;

public interface UserService {
	
	//Iterable --> Puede ser cualquier colecci�n
	public Iterable<User> getAllUsers();

	public User createUser(User user) throws Exception;
	
	public User getUserById(Long id) throws Exception;
	
	public User updateUser(User user) throws Exception;

	public void deleteUser(Long id) throws Exception;
	
	public User changePassword(ChangePasswordForm form) throws Exception;
}
