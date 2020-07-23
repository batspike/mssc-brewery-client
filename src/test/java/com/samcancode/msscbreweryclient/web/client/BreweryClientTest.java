package com.samcancode.msscbreweryclient.web.client;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.samcancode.msscbreweryclient.web.model.BeerDto;
import com.samcancode.msscbreweryclient.web.model.BeerStyleEnum;

@SpringBootTest
class BreweryClientTest {
    public static final UUID BEER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID BEER_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID BEER_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");

    public static final String BEER_1_UPC = "0612334200036";
	public static final String BEER_2_UPC = "0612334300019";
	public static final String BEER_3_UPC = "0083783375213";
	public static final String BEER_4_UPC = "0083783375214";
	public static final String BEER_5_UPC = "0083783375215";
	public static final String BEER_6_UPC = "0083783375216";
	
	@Autowired
	BreweryClient client;

	@Test
	void testGetBeerById() {
		BeerDto beerDto = client.getBeerById(BEER_1_UUID);
		assertNotNull(beerDto);
	}
	
	@Test
	void testSaveNewBeer() {
		BeerDto beerDto = BeerDto.builder()
				.upc(BEER_4_UPC)
				.price(new BigDecimal("12.29"))
				.quantityOnHand(200)
				.beerName("Tiger Beer")
				.beerStyle(BeerStyleEnum.PALE_ALE)
				.build();
		
		URI uri = client.saveNewBeer(beerDto);
		assertNotNull(uri);
	}

	@Test
	void testUpdateBeer() {
		BeerDto beerDto = BeerDto.builder()
				.upc(BEER_5_UPC)
				.price(new BigDecimal("12.29"))
				.quantityOnHand(200)
				.beerName("Updated Beer")
				.beerStyle(BeerStyleEnum.PALE_ALE)
				.build();
		
		client.updateBeer(BEER_1_UUID, beerDto);
	}
	
	@Test
	void testDeleteBeerById() {
		client.deleteBeer(BEER_3_UUID);
	}
	
	
}
