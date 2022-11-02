package uo.ri.cws.domain;

import java.util.Objects;

public class Charge {
	// natural attributes
	private double amount = 0.0;

	// accidental attributes
	private Invoice invoice;
	private PaymentMean paymentMean;

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		// store the amount
		// increment the paymentMean accumulated -> paymentMean.pay( amount )
		// link invoice, this and paymentMean
		this.amount = amount;
		paymentMean.pay(amount);
		Associations.ToCharge.link(paymentMean, this, invoice);
	}

	/**
	 * Unlinks this charge and restores the accumulated to the payment mean
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		// asserts the invoice is not in PAID status
		// decrements the payment mean accumulated ( paymentMean.pay( -amount) )
		// unlinks invoice, this and paymentMean
	}

	public double getAmount() {
		return amount;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public PaymentMean getPaymentMean() {
		return paymentMean;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	void _setPaymentMean(PaymentMean paymentMean) {
		this.paymentMean = paymentMean;
	}

	@Override
	public int hashCode() {
		return Objects.hash(invoice, paymentMean);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Charge other = (Charge) obj;
		return Objects.equals(invoice, other.invoice) && Objects.equals(paymentMean, other.paymentMean);
	}

	@Override
	public String toString() {
		return "Charge [amount=" + amount + ", invoice=" + invoice + ", paymentMean=" + paymentMean + "]";
	}

}
