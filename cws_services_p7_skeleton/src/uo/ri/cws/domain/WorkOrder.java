package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {
				"VEHICLE_ID", "DATE"
		})
})
public class WorkOrder extends BaseEntity {
	public enum WorkOrderState {
		OPEN,
		ASSIGNED,
		FINISHED,
		INVOICED
	}

	// natural attributes
	@Basic(optional = false)
	private LocalDateTime date;
	@Basic(optional = false)
	private String description;
	private double amount = 0.0;
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private WorkOrderState state = WorkOrderState.OPEN;

	// accidental attributes
	@ManyToOne
	private Vehicle vehicle;
	@ManyToOne
	private Mechanic mechanic;
	@ManyToOne
	private Invoice invoice;
	@OneToMany(mappedBy = "workOrder")
	private Set<Intervention> interventions = new HashSet<>();
	
	WorkOrder() {}

	public WorkOrder(Vehicle vehicle, String description) {
		ArgumentChecks.isNotEmpty(description);
		this.description = description;
		Associations.Fix.link(vehicle, this);
	}
	
	public WorkOrder(LocalDateTime date, String description, double amount, WorkOrderState state) {
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotEmpty(description);
		ArgumentChecks.isTrue(amount > 0);
		ArgumentChecks.isNotNull(state);
		this.date = date;
		this.description = description;
		this.amount = amount;
		this.state = state;
	}

	private void computeAmount() {
		double amount = 0.0;
		for (Intervention i : interventions) {
			amount += i.getAmount();
		}
		this.amount = amount;
	}

	/**
	 * Changes it to INVOICED state given the right conditions
	 * This method is called from Invoice.addWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not FINISHED, or
	 *  - The work order is not linked with the invoice
	 */
	public void markAsInvoiced() {
		StateChecks.isTrue(isFinished());
		StateChecks.isNotNull(invoice);
		this.state = WorkOrderState.INVOICED;
	}

	/**
	 * Changes it to FINISHED state given the right conditions and
	 * computes the amount
	 *
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state, or
	 *  - The work order is not linked with a mechanic
	 */
	public void markAsFinished() {
		this.state = WorkOrderState.FINISHED;
		computeAmount();
	}

	/**
	 * Changes it back to FINISHED state given the right conditions
	 * This method is called from Invoice.removeWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not INVOICED, or
	 *  - The work order is still linked with the invoice
	 */
	public void markBackToFinished() {
		this.state = WorkOrderState.FINISHED;
	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its state
	 * to ASSIGNED
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in OPEN state, or
	 *  - The work order is already linked with another mechanic
	 */
	public void assignTo(Mechanic mechanic) {
		Associations.Assign.link(mechanic, this);
		this.state = WorkOrderState.ASSIGNED;
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes
	 * its state back to OPEN
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state
	 */
	public void desassign() {
		this.state = WorkOrderState.OPEN;
	}

	/**
	 * In order to assign a work order to another mechanic is first have to
	 * be moved back to OPEN state and unlinked from the previous mechanic.
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in FINISHED state
	 */
	public void reopen() {
		this.state = WorkOrderState.OPEN;
	}

	public boolean isInvoiced() {
		return getState().equals(WorkOrderState.INVOICED);
	}

	public boolean isFinished() {
		return getState().equals(WorkOrderState.FINISHED);
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public WorkOrderState getState() {
		return state;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, vehicle, description);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkOrder other = (WorkOrder) obj;
		return Objects.equals(date, other.date) && Objects.equals(vehicle, other.vehicle);
	}

	@Override
	public String toString() {
		return "WorkOrder [date=" + date + ", description=" + description + ", amount=" + amount + ", state=" + state
				+ "]";
	}

}
