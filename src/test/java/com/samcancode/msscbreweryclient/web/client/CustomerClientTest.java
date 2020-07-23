package com.samcancode.msscbreweryclient.web.client;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.samcancode.msscbreweryclient.web.model.CustomerDto;

@SpringBootTest
class CustomerClientTest {
	@Autowired
	CustomerClient client;

	@Test
	void testGetCustById() {
		CustomerDto custDto = client.getCustById(UUID.randomUUID());
		assertNotNull(custDto);
	}
	
	@Test
	void testSaveNewCust() {
		CustomerDto custDto = CustomerDto.builder()
										 .id(UUID.randomUUID())
										 .name("New Customer")
										 .build();
		
		URI uri = client.saveNewCust(custDto);
		assertNotNull(uri);
	}

	@Test
	void testUpdateCust() {
		CustomerDto custDto = CustomerDto.builder()
				.id(UUID.randomUUID())
				.name("Update Customer")
				.build();
		
		client.updateCust(UUID.randomUUID(), custDto);
	}
	
	@Test
	void testDeleteCustById() {
		client.deleteCust(UUID.randomUUID());
	}
	
	
}
