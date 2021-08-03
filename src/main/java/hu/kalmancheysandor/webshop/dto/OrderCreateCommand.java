package hu.kalmancheysandor.webshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


@Data
@NoArgsConstructor
@Validated
public class OrderCreateCommand {
    private Integer customerId;
    //private List<OrderItem> items;

//customer_id
//email
//
//price_total_net
//price_total_gross
//
//address_billing_country
//address_billing_city
//address_billing_street
//address_billing_postcode
//address_billing_name
//
//address_shipping_country
//address_shipping_city
//address_shipping_street
//address_shipping_postcode
//address_shipping_name
//
//
//is_payed
//status
//
//date_order
}
