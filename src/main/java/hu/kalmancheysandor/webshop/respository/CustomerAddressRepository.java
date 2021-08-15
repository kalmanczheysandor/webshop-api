package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.customer.CustomerAddress;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomerAddressRepository {

    @PersistenceContext
    EntityManager entityManager;

    public CustomerAddress saveAddress(CustomerAddress customerAddress) {
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

//    public List<CustomerAddress> listAllAddressOfCustomer(Customer customer) {
//        return entityManager.createQuery("SELECT ca FROM CustomerAddress ca " +
//                "WHERE ca.customer=:paramCustomer", CustomerAddress.class)
//                .setParameter("paramCustomer", customer)
//                .getResultList();
//    }

    public CustomerAddress updateAddress(CustomerAddress customerAddress) {
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

//    public void deleteAllAddressOfCustomer(Customer customer) {
//        List<CustomerAddress> addressList = listAddressesOfCustomer(customer);
//        for (CustomerAddress address : addressList) {
//            deleteAddress(address);
//        }
//    }

}
