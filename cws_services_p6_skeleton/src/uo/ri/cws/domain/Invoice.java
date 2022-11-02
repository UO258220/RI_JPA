package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;
import uo.ri.util.math.Round;

public class Invoice {

	private static final LocalDate VAT_CHANGE_DATE = LocalDate.of(2012, 7, 1);
	private static final double VAT_BEFORE = 0.18;
	private static final double VAT_AFTER = 0.21;

	public enum InvoiceState {
		NOT_YET_PAID, PAID
	}

	// natural attributes
	private Long number;
	private LocalDate date;
	private double amount;
	private double vat;
	private InvoiceState state = InvoiceState.NOT_YET_PAID;

	// accidental attributes
	private Set<WorkOrder> workOrders = new HashSet<>();
	private Set<Charge> charges = new HashSet<>();

	public Invoice(Long number) {
		this(number, LocalDate.now(), new ArrayList<>());
	}

	public Invoice(Long number, LocalDate date) {
		this(number, date, new ArrayList<>());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, LocalDate.now(), workOrders);
	}

	// full constructor
	public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
		// check arguments (always), through IllegalArgumentException
		// store the number
		// store a copy of the date
		// add every work order calling addWorkOrder( w )
		ArgumentChecks.isNotNull(number);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotNull(workOrders);
		this.number = number;
		this.date = date;
		for (WorkOrder w : workOrders) {
			addWorkOrder(w);
		}
		computeAmount();
	}

	/**
	 * Computes amount and vat (vat depends on the date) If the date of the invoice
	 * is before 1/7/2012 the VAT is 18% If the date of the invoice is 1/7/2012 or
	 * later the VAT is 21%
	 */
	private void computeAmount() {
		double acc = 0.0;
		for (WorkOrder wo : workOrders) {
			acc += wo.getAmount();
		}
		this.vat = this.date.isBefore(VAT_CHANGE_DATE) ? VAT_BEFORE : VAT_AFTER;
		this.amount = Round.twoCents(acc * (1 + vat));
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount and
	 * vat
	 * 
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		StateChecks.isTrue(isNotSettled());
		Associations.ToInvoice.link(this, workOrder);
		workOrder.markAsInvoiced();
		computeAmount();
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * 
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		StateChecks.isTrue(isNotSettled());
		Associations.ToInvoice.unlink(this, workOrder);
		workOrder.markAsFinished();
		computeAmount();
	}

	/**
	 * Marks the invoice as PAID, but
	 * 
	 * @throws IllegalStateException if - Is already settled - Or the amounts paid
	 *                               with charges to payment means do not cover the
	 *                               total of the invoice
	 */
	public void settle() {
		StateChecks.isTrue(isNotSettled());
	}

	public boolean isNotSettled() {
		return getState().equals(InvoiceState.NOT_YET_PAID);
	}

	public Long getNumber() {
		return number;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getAmount() {
		return amount;
	}

	public double getVat() {
		return vat;
	}

	public InvoiceState getState() {
		return state;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>(charges);
	}

	Set<Charge> _getCharges() {
		return charges;
	}

}
