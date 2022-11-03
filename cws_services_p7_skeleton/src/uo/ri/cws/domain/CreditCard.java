package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

@Entity
public class CreditCard extends PaymentMean {
	@Basic(optional = false)
	private String number;
	@Basic(optional = false)
	private String type;
	@Basic(optional = false)
	private LocalDate validThru;
	
	CreditCard() {}

	public CreditCard(String number) {
		this(number, "UNKNOWN", LocalDate.now().plusDays(1));
	}
	
	public CreditCard(String number, String type, LocalDate validThru) {
		ArgumentChecks.isNotEmpty(number);
		ArgumentChecks.isNotEmpty(type);
		ArgumentChecks.isNotNull(validThru);
		this.number = number;
		this.type = type;
		this.validThru = validThru;
	}

	/**
	 * Augments the accumulated by the amount paid
	 * 
	 * @throws IllegalStateException if validity date expired
	 */
	@Override
	public void pay(double amount) {
		StateChecks.isTrue(isValidNow());
		super.pay(amount);
	}

	public boolean isValidNow() {
		return validThru.isAfter(LocalDate.now());
	}

	public String getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}

	public LocalDate getValidThru() {
		return validThru;
	}

	public void setValidThru(LocalDate validThru) {
		this.validThru = validThru;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(number);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCard other = (CreditCard) obj;
		return Objects.equals(number, other.number);
	}

	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", type=" + type + ", validThru=" + validThru + "]";
	}

}
