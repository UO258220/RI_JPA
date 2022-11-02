package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class SparePart {
	// natural attributes
	private String code;
	private String description;
	private double price;

	// accidental attributes
	private Set<Substitution> substitutions = new HashSet<>();

	public SparePart(String code, String description, double price) {
		ArgumentChecks.isNotEmpty(code);
		ArgumentChecks.isNotEmpty(description);
		ArgumentChecks.isTrue(price > 0);
		this.code = code;
		this.description = description;
		this.price = price;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>( substitutions );
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SparePart other = (SparePart) obj;
		return Objects.equals(code, other.code);
	}

	@Override
	public String toString() {
		return "SparePart [code=" + code + ", description=" + description + ", price=" + price + "]";
	}

}
