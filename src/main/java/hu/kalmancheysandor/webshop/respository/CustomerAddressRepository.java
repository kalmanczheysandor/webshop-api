package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.customer.CustomerAddress;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.springframework.util.StringUtils.capitalize;

@Repository
public class CustomerAddressRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${customerRegistration.useUppercaseCorrection}")
    private boolean useUppercaseCorrection;

    public CustomerAddress saveAddress(CustomerAddress customerAddress) {
        if (useUppercaseCorrection) {
            customerAddress.setCountry(capitalize(customerAddress.getCountry()));
            customerAddress.setCity(capitalize(customerAddress.getCity()));
        }
        entityManager.persist(customerAddress);
        return customerAddress;
    }

    public CustomerAddress findAddressById(int customerAddressId) {
        CustomerAddress customerAddress = entityManager.find(CustomerAddress.class, customerAddressId);
        if (customerAddress == null) {
            throw new RecordNotFoundByIdException(customerAddressId);
        }
        return customerAddress;
    }

    public List<CustomerAddress> listAllAddress() {
        return entityManager.createQuery("SELECT a FROM CustomerAddress a", CustomerAddress.class).getResultList();
    }

    public CustomerAddress updateAddress(CustomerAddress customerAddress) {
        if (useUppercaseCorrection) {
            customerAddress.setCountry(capitalize(customerAddress.getCountry()));
            customerAddress.setCity(capitalize(customerAddress.getCity()));
        }
        CustomerAddress updated = entityManager.merge(customerAddress);
        return updated;
    }

    public void deleteAddressById(int customerAddressId) {
        CustomerAddress addressToDelete = findAddressById(customerAddressId);
        deleteAddress(addressToDelete);
    }

    public void deleteAllAddressByCustomerId(int customerId) {
        List<CustomerAddress> addressList = entityManager.createQuery("SELECT a FROM CustomerAddress a " +
                "JOIN a.customer c " +
                "WHERE c.id=:paramCustomerId", CustomerAddress.class)
                .setParameter("paramCustomerId", customerId)
                .getResultList();

        for (CustomerAddress address : addressList) {
            deleteAddress(address);
        }
    }

    private void deleteAddress(CustomerAddress customerAddress) {
        entityManager.remove(customerAddress);
    }
}
