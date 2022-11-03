package uo.ri.cws.domain;

import java.util.Objects;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class Voucher extends PaymentMean {
	
	private String code;
	private double available = 0.0;
	private String description;

	public Voucher(String code, double available) {
		this(code, "no-description", available);
	}
	
	public Voucher(String code, String description, double available) {
		ArgumentChecks.isNotEmpty(code);
		ArgumentChecks.isNotEmpty(description);
		this.code = code;
		this.description = description;
		this.available = available;
	}

	/**
	 * Augments the accumulated (super.pay(amount) ) and decrements the available
	 * 
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
		StateChecks.isTrue(this.available >= amount);
		super.pay(amount);
		this.available -= amount;
	}

	public String getCode() {
		return code;
	}

	public double getAvailable() {
		return available;
	}

	public String getDescription() {
		return description;
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
		Voucher other = (Voucher) obj;
		return Objects.equals(code, other.code);
	}

	@Override
	public String toString() {
		return "Voucher [code=" + code + ", available=" + available + ", description=" + description + "]";
	}

}
