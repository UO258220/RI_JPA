package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TVEHICLES")
public class Vehicle extends BaseEntity {
	
	@Column(unique = true)
	private String plateNumber;
	@Basic(optional = false)
	@Column(name = "BRAND")
	private String make;
	@Basic(optional = false)
	private String model;
	
	@ManyToOne
	private Client client;
	@ManyToOne
	private VehicleType vehicleType;
	@OneToMany(mappedBy = "vehicle")
	private Set<WorkOrder> workOrders = new HashSet<>();
	
	Vehicle() {}
	
	public Vehicle(String platenumber, String make, String model) {
		ArgumentChecks.isNotEmpty(platenumber);
		ArgumentChecks.isNotEmpty(make);
		ArgumentChecks.isNotEmpty(model);
		this.plateNumber = platenumber;
		this.make = make;
		this.model = model;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public Client getClient() {
		return client;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	void _setClient(Client client) {
		this.client = client;
	}

	void _setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}
	
	public Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	@Override
	public int hashCode() {
		return Objects.hash(plateNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(plateNumber, other.plateNumber);
	}

	@Override
	public String toString() {
		return "Vehicle [plateNumber=" + plateNumber + ", make=" + make + ", model=" + model + "]";
	}

}
