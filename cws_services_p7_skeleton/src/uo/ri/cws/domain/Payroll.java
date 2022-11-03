package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Payroll {

	private LocalDate date;
	private double bonus;
	private double incomeTax;
	private double monthlyWage;
	private double nic;
	private double productivityBonus;
	private double trienniumPayment;

	private Contract contract;

	public LocalDate getDate() {
		return date;
	}

	public double getBonus() {
		return bonus;
	}

	public double getIncomeTax() {
		return incomeTax;
	}

	public double getMonthlyWage() {
		return monthlyWage;
	}

	public double getNic() {
		return nic;
	}

	public double getProductivityBonus() {
		return productivityBonus;
	}

	public double getTrienniumPayment() {
		return trienniumPayment;
	}

	public Contract getContract() {
		return contract;
	}

	void _setContract(Contract contract) {
		this.contract = contract;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contract, date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payroll other = (Payroll) obj;
		return Objects.equals(contract, other.contract) && Objects.equals(date, other.date);
	}

	@Override
	public String toString() {
		return "Payroll [date=" + date + ", bonus=" + bonus + ", incomeTax=" + incomeTax + ", monthlyWage="
				+ monthlyWage + ", nic=" + nic + ", productivityBonus=" + productivityBonus + ", trienniumPayment="
				+ trienniumPayment + "]";
	}

}
