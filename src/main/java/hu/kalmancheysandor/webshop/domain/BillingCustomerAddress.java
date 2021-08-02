package hu.kalmancheysandor.webshop.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_customer_address_billing")
public class BillingCustomerAddress extends CustomerAddress {
}
