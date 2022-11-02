package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Intervention {
	// natural attributes
	private LocalDateTime date;
	private int minutes;

	// accidental attributes
	private WorkOrder workOrder;
	private Mechanic mechanic;
	private Set<Substitution> substitutions = new HashSet<>();
	
	public Intervention(LocalDateTime date, int minutes) {
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isTrue(minutes > 0);
		this.date = date;
		this.minutes = minutes;
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
		ArgumentChecks.isTrue(minutes > 0);
		this.date = LocalDateTime.now();
		Associations.Intervene.link(workOrder, this, mechanic);
	}

	public LocalDateTime getDate() {
		return date;
	}

	public int getMinutes() {
		return minutes;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>( substitutions );
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

}
