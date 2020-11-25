package com.stoom.stoomer.model.entity;

/**
 * This object represents an address used by rest-crud exercise.
 * 
 * REQUIRED: id, streetName, number, neighbourhood, city, state, country, zipcode, 
 * OPTIONAL: complement, latitude, longitude
 * 
 * @author Gustavo Padilha gupadilha[at]gmail.com
 */

@javax.persistence.Entity
public class Address {
	
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private Long id;
	private String streetName;
	private String number;
	private String complement;
	private String neighbourhood;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private Double latitude;
	private Double longitude;
	
	public Address() {}

	public Address(Long id) {
		this.setId(id);
	}
	
	public Address(Long id, String streetName, String number, String neighbourhood, 
			String city, String state, String country, String zipcode) {
		setId(id);
		setStreetName(streetName);
		setNumber(number);
		setNeighbourhood(neighbourhood);
		setCity(city);
		setState(state);
		setCountry(country);
		setZipcode(zipcode);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append((this.getStreetName() == null)||(this.getStreetName().trim().isEmpty()) ? "" : (this.getStreetName().trim()+" "));
		result.append((this.getNumber() == null)||(this.getNumber().trim().isEmpty()) ? "" : (this.getNumber().trim()+" "));
		result.append((this.getNeighbourhood() == null)||(this.getNeighbourhood().trim().isEmpty()) ? "" : (this.getNeighbourhood().trim()+" "));
		result.append((this.getCity() == null)||(this.getCity().trim().isEmpty()) ? "" : (this.getCity().trim()+" "));
		result.append((this.getState() == null)||(this.getState().trim().isEmpty()) ? "" : (this.getState().trim()+" "));
		result.append((this.getCountry() == null)||(this.getCountry().trim().isEmpty()) ? "" : (this.getCountry().trim()+" "));
		result.append((this.getZipcode() == null)||(this.getZipcode().trim().isEmpty()) ? "" : (this.getZipcode().trim()+" "));
		return result.toString().trim();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getComplement() {
		return complement;
	}
	
	public void setComplement(String complement) {
		this.complement = complement;
	}
	
	public String getNeighbourhood() {
		return neighbourhood;
	}
	
	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
