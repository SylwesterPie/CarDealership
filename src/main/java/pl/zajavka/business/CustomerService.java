package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.business.dao.CustomerDAO;
import pl.zajavka.domain.Address;
import pl.zajavka.domain.Customer;

import java.util.Optional;

@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;

    @Transactional
    public void issueInvoice(Customer customer) {
        customerDAO.issueInvoice(customer);

    }

    public Customer findCustomer(String email) {
        Optional<Customer> customer = customerDAO.findCustomerByEmail(email);
        if (customer.isEmpty()) {
            throw new RuntimeException("Could not find Customer by email: [%s]".formatted(email));
        }
        return customer.get();
    }

    @Transactional
    public void saveServiceRequest(Customer customer) {
        customerDAO.saveServiceRequest(customer);
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerDAO.saveCustomer(customer);
    }
}
