package com.practice.backend.service;


import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.practice.backend.entity.Authority;
import com.practice.backend.entity.BankAccount;
import com.practice.backend.entity.PracticeUser;
import com.practice.backend.repository.PracticeUserRepository;







@Service
public class DefaultPracticeUserService implements PracticeUserService {
	
	

	private final PracticeUserRepository practiceUserRepository;
	
	public DefaultPracticeUserService(PracticeUserRepository practiceUserRepository) {
		this.practiceUserRepository=practiceUserRepository;
	}
	
	@Override
	public void save(PracticeUser user) {
		this.practiceUserRepository.save(user);
		
	}
	public PracticeUser findUserByUsername(String username) {
		return this.practiceUserRepository.findByUsername(username)
				.orElseThrow(()->new UsernameNotFoundException("Пользовтаель не найден"));
	}


	@Override
	public Optional<PracticeUser> findBankAccountById(Integer userId) {
		return this.practiceUserRepository.findById(userId);
	}

	@Override
	public Iterable<BankAccount> getBankAccounts(String username) {
		PracticeUser practiceUser=this.practiceUserRepository.findByUsername(username)
				.orElseThrow(()-> new NoSuchElementException("Пользователь не найден"));
		return practiceUser.getBankAccounts();
	}



	@Override
	public UserDetails loadUserByUsername(String username) {
		PracticeUser user=this.practiceUserRepository.findByUsername(username)
				.orElseThrow(()->new UsernameNotFoundException("Пользовтаель не найден"));
		return new User(
				user.getUsername(),
				user.getPincode(),
				user.getAuthorities().stream()
						.map(Authority::getAuthority)
						.map(SimpleGrantedAuthority::new)
						.toList());
	}
	}

	
	


