package com.stoom.stoomer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stoom.stoomer.exception.MissingInformationException;
import com.stoom.stoomer.exception.NoDataFoundException;
import com.stoom.stoomer.model.entity.Address;
import com.stoom.stoomer.service.AddressService;
import com.stoom.stoomer.service.ConfigService;

@RestController
@RequestMapping("/address")
public class StoomerController {
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private ConfigService configService;
	
	/**
	 * Address entity creation
	 * @param address
	 * @return Address request object and...
	 * <br> ...HttpStatus.OK for success creation.
	 * <br> ...HttpStatus.BAD_REQUEST for 'Address' incomplete.
	 * <br> ...HttpStatus.NOT_FOUND for Latitude and Longitude not found via Google Maps API.
	 * <br> ...HttpStatus.INTERNAL_SERVER_ERROR when something for unexpected issues.
	 */
	@PostMapping("/crud")
	public ResponseEntity<Address> create(@RequestBody Address address) {
		return commit(address);
	}
	
	@GetMapping("/crud")
	public ResponseEntity<Address[]> read(Long id) {
		try {
			if (id == null) {
				return new ResponseEntity<Address[]>(
						addressService.queryAll(), 
						HttpStatus.OK
						);
			} else {
				return new ResponseEntity<Address[]>(
						new Address[] {addressService.queryById(id)},
						HttpStatus.OK
						);
			}
		} catch (NoDataFoundException err) {
			return new ResponseEntity<Address[]>(
					new Address[] { },
					HttpStatus.NOT_FOUND
					);
		}
	}
	
	@PutMapping("/crud")
	public ResponseEntity<Address> update(@RequestBody Address address) {
		return commit(address);
	}
	
	private ResponseEntity<Address> commit(@RequestBody Address address) {
		try {
			return new ResponseEntity<Address>(
					addressService.save(address, configService.getGoogleKey()),
					HttpStatus.OK
					);
		} catch (MissingInformationException err) {
			return new ResponseEntity<Address>(address, HttpStatus.BAD_REQUEST);
		} catch (NoDataFoundException err) {
			return new ResponseEntity<Address>(address, HttpStatus.NOT_FOUND);
		} catch (Exception err) {
			return new ResponseEntity<Address>(address, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Address removal method
	 * @param address
	 * @return Address request object and...
	 * <br> ...HttpStatus.OK for success deleting.
	 * <br> ...HttpStatus.BAD_REQUEST when 'Address Id' is invalid (empty or zero)
	 * <br> ...HttpStatus.NOT_FOUND when 'Address' is not found by 'Id'.
	 */
	@DeleteMapping("/crud")
	public ResponseEntity<Address> delete(@RequestBody Long id) {
		try {
			return new ResponseEntity<Address>(addressService.remove(id), HttpStatus.OK);
		} catch (MissingInformationException err) {
			return new ResponseEntity<Address>(new Address(id), HttpStatus.BAD_REQUEST);
		} catch (NoDataFoundException err) {
			return new ResponseEntity<Address>(new Address(id), HttpStatus.NOT_FOUND);
		}
	}

}



