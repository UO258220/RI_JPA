package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class ProfessionalGroup extends BaseEntity {

	private String name;
	private double productivityRate;
	private double trienniumSalary;

	private Set<Contract> contracts = new HashSet<>();

	public ProfessionalGroup(String name, double productivityRate, double trienniumSalary) {
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isTrue(productivityRate >= 0);
		ArgumentChecks.isTrue(trienniumSalary >= 0);
		this.name = name;
		this.productivityRate = productivityRate;
		this.trienniumSalary = trienniumSalary;
	}

	public String getName() {
		return name;
	}

	public double getProductivityRate() {
		return productivityRate;
	}

	public double getTrienniumSalary() {
		return trienniumSalary;
	}

	public Set<Contract> getContracts() {
		return new HashSet<>(contracts);
	}

	Set<Contract> _getContracts() {
		return contracts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfessionalGroup other = (ProfessionalGroup) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "ProfessionalGroup [name=" + name + ", productivityRate=" + productivityRate + ", trienniumSalary="
				+ trienniumSalary + "]";
	}

}
