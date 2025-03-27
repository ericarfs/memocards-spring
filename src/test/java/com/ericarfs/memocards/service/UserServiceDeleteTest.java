package com.ericarfs.memocards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ericarfs.memocards.entity.User;
import com.ericarfs.memocards.entity.enums.Role;
import com.ericarfs.memocards.exceptions.ResourceNotFoundException;
import com.ericarfs.memocards.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceDeleteTest {
	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PasswordEncoder realPasswordEncoder;
	
	private List<User> users = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		realPasswordEncoder = new BCryptPasswordEncoder(); 
		User u1 = new User("67e04d0d365ac80f157d98b4", "Maria", "Asjpk!26", Role.BASIC);
		
		users.add(u1);
		
		lenient().when(repository.findById("67e04d0d365ac80f157d98b4"))
					.thenReturn(Optional.of(users.get(0)));
		
		lenient().when(repository.findById("67e04d0d365ac80f157d98b5"))
					.thenThrow(new ResourceNotFoundException("User with id 67e04d0d365ac80f157d98b5 not found."));
		
	}
	
	@Test
	void mustDeleteAnUser() {
		String nameEncoded = realPasswordEncoder.encode("Maria");
		String newUsername = "Maria" + nameEncoded.substring(0, 15);
		when(passwordEncoder.encode("Maria")).thenReturn(nameEncoded);
		
		service.delete("67e04d0d365ac80f157d98b4");
		
		verify(repository).findById("67e04d0d365ac80f157d98b4");
		verify(repository).deleteById("67e04d0d365ac80f157d98b4",newUsername);
		verifyNoMoreInteractions(repository);
	}
	
	
	@Test
	void mustThrowExceptionWhenUserNotFoundById() {
		final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
			service.delete("67e04d0d365ac80f157d98b5");
		});
		
		verify(repository).findById("67e04d0d365ac80f157d98b5");
		assertEquals(e.getMessage(), "User with id 67e04d0d365ac80f157d98b5 not found.");
		verifyNoMoreInteractions(repository);
	}
	

}
