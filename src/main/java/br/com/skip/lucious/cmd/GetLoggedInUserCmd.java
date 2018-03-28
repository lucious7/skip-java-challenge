package br.com.skip.lucious.cmd;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.skip.lucious.entity.Customer;
import br.com.skip.lucious.repository.CustomerRepository;

@Component
public class GetLoggedInUserCmd {

	@Autowired
	private CustomerRepository customerRepo;

	public Customer get() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Optional<Customer> customer = customerRepo.findByEmail((String) auth.getPrincipal());
		return customer.orElseThrow(() -> new RuntimeException("No user logged in."));
	}

}
