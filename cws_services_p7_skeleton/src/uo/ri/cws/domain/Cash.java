package uo.ri.cws.domain;

import javax.persistence.Entity;

@Entity
public class Cash extends PaymentMean {
	
	Cash() {}

	public Cash(Client c) {
		Associations.Pay.link(c, this);
	}

}
