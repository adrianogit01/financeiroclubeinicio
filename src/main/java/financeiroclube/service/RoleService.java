package financeiroclube.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import financeiroclube.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;

	public boolean roleExist(String role) {
		return roleRepository.existsById(role);
	}
}
