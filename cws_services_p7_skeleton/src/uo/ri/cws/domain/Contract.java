package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Contract {

	public enum ContractState {
		IN_FORCE, TERMINATED
	}

	private double annualWage;
	private LocalDate startDate;
	private LocalDate endDate;
	private double settlement;
	private ContractState state;

	private Mechanic mechanic;
	private ProfessionalGroup group;
	private ContractType type;
	private Set<Payroll> payrolls = new HashSet<>();
	private Mechanic firedmechanic;

	public Contract(Mechanic mechanic, ContractType type, ProfessionalGroup group, double wage) {
		ArgumentChecks.isNotNull(mechanic);
		ArgumentChecks.isNotNull(type);
		ArgumentChecks.isNotNull(group);
		ArgumentChecks.isTrue(wage >= 0);
		this.annualWage = wage;
		this.startDate = LocalDate.now();
		this.state = ContractState.IN_FORCE;
		Associations.Hire.link(mechanic, this, type, group);
	}

	public void terminate() {
		this.state = ContractState.TERMINATED;
		Associations.Fire.link(this);
	}

	public double getAnnualWage() {
		return annualWage;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public double getSettlement() {
		return settlement;
	}

	public ContractState getState() {
		return state;
	}

	public Optional<Mechanic> getMechanic() {
		return mechanic == null ? Optional.empty() : Optional.of(mechanic);
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public ProfessionalGroup getGroup() {
		return group;
	}

	void _setProfessionalGroup(ProfessionalGroup group) {
		this.group = group;
	}

	public ContractType getType() {
		return type;
	}

	void _setContractType(ContractType type) {
		this.type = type;
	}

	public Set<Payroll> getPayrolls() {
		return new HashSet<>(payrolls);
	}

	Set<Payroll> _getPayrolls() {
		return payrolls;
	}

	public Optional<Mechanic> getFiredMechanic() {
		return firedmechanic == null ? Optional.empty() : Optional.of(firedmechanic);
	}

	void _setFiredMechanic(Mechanic mechanic) {
		this.firedmechanic = mechanic;
	}

	@Override
	public int hashCode() {
		return Objects.hash(mechanic, startDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contract other = (Contract) obj;
		return Objects.equals(mechanic, other.mechanic) && Objects.equals(startDate, other.startDate);
	}

	@Override
	public String toString() {
		return "Contract [annualWage=" + annualWage + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", settlement=" + settlement + ", state=" + state + "]";
	}

}
