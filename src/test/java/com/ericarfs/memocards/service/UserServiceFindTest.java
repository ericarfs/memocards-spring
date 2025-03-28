package com.ericarfs.memocards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.ericarfs.memocards.entity.User;
import com.ericarfs.memocards.entity.enums.Role;
import com.ericarfs.memocards.exceptions.ResourceNotFoundException;
import com.ericarfs.memocards.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceFindTest {
	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository repository;
	
	private List<User> users = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		User u1 = new User("67e04d0d365ac80f157d98b4", "Maria", "Asjpk!26", Role.BASIC);
		
		users.add(u1);
	}
	
	@Test
	void mustReturnAListWithOneUser() {
		when(repository.findAll()).thenReturn(users);
		List<User> users = service.findAll();
		
		assertEquals(1, users.size());
		
		verify(repository).findAll();
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustFindAnUserById() {
		when(repository.findById("67e04d0d365ac80f157d98b4")).thenReturn(Optional.of(users.get(0)));
		
		User u = service.findById("67e04d0d365ac80f157d98b4");
		
		assertEquals(u, users.get(0));
		
		verify(repository).findById("67e04d0d365ac80f157d98b4");
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustThrowExceptionWhenUserNotFound() {
		when(repository.findById("67e04d0d365ac80f157d98b5"))
						.thenThrow(new ResourceNotFoundException("User with id 67e04d0d365ac80f157d98b5 not found."));
		
		final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
			service.findById("67e04d0d365ac80f157d98b5");
		});
		
		verify(repository).findById("67e04d0d365ac80f157d98b5");
		assertEquals(e.getMessage(), "User with id 67e04d0d365ac80f157d98b5 not found.");
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustFindAnUserByUsername() {
		when(repository.findByUsername("Maria")).thenReturn(Optional.of(users.get(0)));
		
		Optional<User> u = service.findByUsername("Maria");
		
		assertEquals(u.get(), users.get(0));
		
		verify(repository).findByUsername("Maria");
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustReturnNullWhenUserNotFound() {
		when(repository.findByUsername("Joao")).thenReturn(null);
		
		Optional<User> u = service.findByUsername("Joao");
		
		assertNull(u);
		
		verify(repository).findByUsername("Joao");
		verifyNoMoreInteractions(repository);
	}

}
