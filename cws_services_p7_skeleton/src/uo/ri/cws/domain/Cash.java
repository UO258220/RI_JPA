package uo.ri.cws.domain;

public class Cash extends PaymentMean {

	public Cash(Client c) {
		Associations.Pay.link(c, this);
	}

}
