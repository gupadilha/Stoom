package com.stoom.stoomer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.stoom.stoomer.exception.IntegrationException;
import com.stoom.stoomer.exception.MissingInformationException;
import com.stoom.stoomer.exception.NoDataFoundException;
import com.stoom.stoomer.model.entity.Address;
import com.stoom.stoomer.model.repository.AddressRepository;

/**
 * Address Service class handling business roles and 'DB' interaction.
 * @author guspadilha
 */
@Component("addressService")
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepository;
	
	public Address create(Address address, String googleKey) throws MissingInformationException, NoDataFoundException, IntegrationException {
		if ((address.getId() == null) || address.getId().equals(0L)) {
			address.setId(null);
			return save(address, googleKey);
		} else {
			throw new MissingInformationException("Invalid address identifier");
		}
	}

	public Address update(Address address, String googleKey) throws MissingInformationException, NoDataFoundException, IntegrationException {
		if ((address.getId() == null) || address.getId().equals(0L)) {
			throw new MissingInformationException("Invalid address identifier");
		}
		queryById(address.getId());
		return save(address, googleKey);
	}

	private Address save(Address address, String googleKey) throws MissingInformationException, NoDataFoundException, IntegrationException {
		if (isAddressOk(address)) {
			if (isMissingGeoInfo(address)) {
				queryGeoInfo(address, googleKey);
			}
			return addressRepository.save(address);
		} else {
			throw new MissingInformationException("Address.isAddressOk: false");
		}
	}
	
	public Address queryById(Long id) throws NoDataFoundException {
		Optional<Address> result = addressRepository.findById(id);
		if (result.isEmpty()) {
			throw new NoDataFoundException();
		} else {
			return result.get();
		}
	}
	
	public Address[] queryAll() {
		List<Address> result = new ArrayList<Address>();
		for (Address address : addressRepository.findAll()) result.add(address);
		return result.toArray(new Address[] {});
	}
	
	public Address remove(Long id) throws MissingInformationException, NoDataFoundException {
		if ((id == null) || id.equals(0L)) {
			throw new MissingInformationException();
		} else {
			Address result = queryById(id);
			addressRepository.deleteById(id);
			return result;
		}
	}
	
	private boolean isAddressOk(Address address) {
		return !(
				((address.getStreetName() == null)||(address.getStreetName().trim().isEmpty()))
				|| ((address.getNumber() == null)||(address.getNumber().trim().isEmpty()))
				|| ((address.getNeighbourhood() == null)||(address.getNeighbourhood().trim().isEmpty()))
				|| ((address.getCity() == null)||(address.getCity().trim().isEmpty()))
				|| ((address.getState() == null)||(address.getState().trim().isEmpty()))
				|| ((address.getCountry() == null)||(address.getCountry().trim().isEmpty()))
				|| ((address.getZipcode() == null)||(address.getZipcode().trim().isEmpty()))
				);
	}
	
	private boolean isMissingGeoInfo(Address address) {
		return ((address.getLatitude() == null) || (address.getLongitude() == null));
	}
	
	private boolean queryGeoInfo(Address address, String googleKey) throws NoDataFoundException, IntegrationException {
		try {
			String query = address.toString().replaceAll(" ","+");
			GeoApiContext context = new GeoApiContext.Builder().apiKey(googleKey).build();
            GeocodingResult[] request = GeocodingApi.newRequest(context).address(query).await();
            if (request.length > 0) {
	            LatLng location = request[0].geometry.location;
	            address.setLatitude(location.lat);
	            address.setLongitude(location.lng);
	            return true;
            } else {
                throw new NoDataFoundException("Latitude/Longitude not found for reported address.");
            }
        } catch (Exception err) {
            throw new IntegrationException(err);
        }
	}
	
}
