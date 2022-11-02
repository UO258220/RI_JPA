package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Client {
	private String dni;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private Address address;

	private Set<PaymentMean> paymentMeans = new HashSet<>();
	private Set<Vehicle> vehicles = new HashSet<>();

	public Client(String dni) {
		this(dni, "no-name", "no-surname", "no@email", "no-phone", null);
	}

	public Client(String dni, String name, String surname) {
		this(dni, name, surname, "no@email", "no-phone", null);
	}

	public Client(String dni, String name, String surname, String email, String phone, Address address) {
		ArgumentChecks.isNotBlank(dni);
		ArgumentChecks.isNotBlank(name);
		ArgumentChecks.isNotBlank(surname);
		ArgumentChecks.isNotBlank(email);
		ArgumentChecks.isNotBlank(phone);
		this.dni = dni;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	public String getDni() {
		return dni;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Address getAddress() {
		return address;
	}

	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<>(paymentMeans);
	}

	Set<PaymentMean> _getPaymentMeans() {
		return paymentMeans;
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(dni, other.dni);
	}

	@Override
	public String toString() {
		return "Client [dni=" + dni + ", name=" + name + ", surname=" + surname + ", email=" + email + ", phone="
				+ phone + ", address=" + address + "]";
	}

}
