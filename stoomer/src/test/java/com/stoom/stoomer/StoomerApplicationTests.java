package com.stoom.stoomer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stoom.stoomer.controller.StoomerController;
import com.stoom.stoomer.model.entity.Address;

@SpringBootTest
//@RestClientTest(StoomerController.class)
class StoomerApplicationTests {
	
	@Autowired
	private StoomerController stoomerController;
	
	/**
	 * Evaluates an address creation with proper entity usage.
	 */
	@Test
	public void addressCreationSuccess() {
		Logger.getLogger(this.getClass()).debug("addressCreationSuccess");
		ResponseEntity<Address> result = stoomerController.create(getSuccessAddress());
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody().getLatitude()).isNotNull();
		assertThat(result.getBody().getLongitude()).isNotNull();
		assertThatNoException();
	}

	/**
	 * Evaluates an address creation with missing information entity.
	 */
	@Test
	public void addressCreationErrIncomplete() {
		Logger.getLogger(this.getClass()).debug("addressCreationErrInscomplete");
		ResponseEntity<Address> result = stoomerController.create(getIncompleteAddress());
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThatNoException();
	}

	/**
	 * Evaluates an address item (per Id) success load.
	 */
	@Test
	public void addressReadingItemSuccess() {
		Logger.getLogger(this.getClass()).debug("addressReadingItemSuccess");
		ResponseEntity<Address> preparation = stoomerController.create(getSuccessAddress());
		assertThat(preparation.getStatusCode()).isEqualTo(HttpStatus.OK);
		ResponseEntity<Address> result = stoomerController.readById(preparation.getBody().getId());
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(preparation.getBody().getId()).isEqualTo(result.getBody().getId());
		assertThat(preparation.getBody().getStreetName()).isEqualTo(result.getBody().getStreetName());
		assertThat(preparation.getBody().getNumber()).isEqualTo(result.getBody().getNumber());
		assertThat(preparation.getBody().getCity()).isEqualTo(result.getBody().getCity());
		assertThat(preparation.getBody().getState()).isEqualTo(result.getBody().getState());
		assertThat(preparation.getBody().getCountry()).isEqualTo(result.getBody().getCountry());
		assertThat(preparation.getBody().getZipcode()).isEqualTo(result.getBody().getZipcode());
	}

	/**
	 * Evaluates an address list success load.
	 */
	@Test
	public void addressReadingListSuccess() {
		Logger.getLogger(this.getClass()).debug("addressReadingListSuccess");
		ResponseEntity<Address> preparation1 = stoomerController.create(getSuccessAddress());
		assertThat(preparation1.getStatusCode()).isEqualTo(HttpStatus.OK);
		ResponseEntity<Address> preparation2 = stoomerController.create(getSuccessAddress());
		assertThat(preparation2.getStatusCode()).isEqualTo(HttpStatus.OK);
		ResponseEntity<Address[]> result = stoomerController.readAll();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody().length).isGreaterThanOrEqualTo(2);
	}

	/**
	 * Evaluates a non existing address load.
	 */
	@Test
	public void addressReadingNotFound() {
		Logger.getLogger(this.getClass()).debug("addressReadingNotFound");
		ResponseEntity<Address> result = stoomerController.readById(Long.MAX_VALUE);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	/**
	 * Evaluates a success change to the entity.
	 */
	@Test
	public void addressUpdateSuccess() {
		Logger.getLogger(this.getClass()).debug("addressUpdateSuccess");
		// create
		Address preparation1 = getSuccessAddress();
		preparation1.setStreetName(preparation1.getStreetName().toLowerCase());
		ResponseEntity<Address> preparation2 = stoomerController.create(preparation1);
		assertThat(preparation2.getStatusCode()).isEqualTo(HttpStatus.OK);
		// apply some changes
		preparation2.getBody().setStreetName(preparation2.getBody().getStreetName().toUpperCase());
		ResponseEntity<Address> preparation3 = stoomerController.update(preparation2.getBody());
		assertThat(preparation3.getStatusCode()).isEqualTo(HttpStatus.OK);
		// load entity changed
		ResponseEntity<Address> result = stoomerController.readById(preparation3.getBody().getId());
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody().getStreetName()).isEqualTo(preparation1.getStreetName().toUpperCase());
		assertThat(result.getBody().getStreetName()).isEqualTo(preparation2.getBody().getStreetName());
	}

	/**
	 * Evaluates missing identifier changes.
	 */
	@Test
	public void addressUpdateBadRequest() {
		Logger.getLogger(this.getClass()).debug("addressUpdateBadRequest");
		ResponseEntity<Address> result = stoomerController.update(getSuccessAddress());
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	
	/**
	 * Evaluates non existing address changes.
	 */
	@Test
	public void addressUpdateNotFound() {
		Logger.getLogger(this.getClass()).debug("addressUpdateNotFound");
		Address preparation1 = getSuccessAddress();
		preparation1.setId(Long.MAX_VALUE);
		ResponseEntity<Address> result = stoomerController.update(preparation1);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	/**
	 * Evaluates an expected removal creating the entity, reading, removing and 
	 * double checking the removal trying to read it again (full life cycle).
	 */
	@Test
	public void addressDeleteSuccess() {
		Logger.getLogger(this.getClass()).debug("addressDeleteSuccess");
		// create
		ResponseEntity<Address> preparation1 = stoomerController.create(getSuccessAddress());
		assertThat(preparation1.getStatusCode()).isEqualTo(HttpStatus.OK);
		// remove
		ResponseEntity<Address> preparation2 = stoomerController.delete(preparation1.getBody().getId());
		assertThat(preparation2.getStatusCode()).isEqualTo(HttpStatus.OK);
		// double check removal
		ResponseEntity<Address> preparation3 = stoomerController.readById(preparation1.getBody().getId());
		assertThat(preparation3.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	/**
	 * Evaluates missing identifier removal
	 */
	@Test
	public void addressDeleteBadRequest() {
		Logger.getLogger(this.getClass()).debug("addressDeleteBadRequest");
		ResponseEntity<Address> result = stoomerController.delete(null);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	/**
	 * Evaluates non existing address removal.
	 */
	@Test
	public void addressDeleteNotFound() {
		Logger.getLogger(this.getClass()).debug("addressDeleteNotFound");
		ResponseEntity<Address> result = stoomerController.delete(Long.MAX_VALUE);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Provides an Address instance properly populated.
	 * @return
	 */
	private Address getSuccessAddress() {
		return new Address(
				null, "R. Zuneide Aparecida Marin", "43", "Barao Geraldo",
				"Jardim Santa Genebra II", "Campinas", "Sao Paulo", "Brasil",
				"13084-780", null, null
				);
	}
	
	/**
	 * Provides an Address instance missing required attributes 
	 * @return
	 */
	private Address getIncompleteAddress() {
		return new Address(
				null, "R. Zuneide Aparecida Marin", "43", null,
				"", "Campinas", "Sao Paulo", "Brasil",
				"13084-780", null, null
				);
	}

}
