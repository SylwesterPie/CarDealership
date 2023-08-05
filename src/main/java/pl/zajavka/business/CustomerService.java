package pl.zajavka.business;

import lombok.AllArgsConstructor;
import pl.zajavka.business.dao.CustomerDAO;
import pl.zajavka.domain.Address;
import pl.zajavka.domain.Customer;

import java.util.Optional;

@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;

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

    public void saveServiceRequest(Customer customer) {
        customerDAO.saveServiceRequest(customer);
    }

    public Customer saveCustomer(Customer customer) {
        Customer entity = Customer.builder()
                .name(customer.getName())
                .surname(customer.getSurname())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .address(
                        Address.builder()
                                .country(customer.getAddress().getCountry())
                                .city(customer.getAddress().getCity())
                                .postalCode(customer.getAddress().getPostalCode())
                                .address(customer.getAddress().getAddress())
                                .build()
                )
                .build();
        return customerDAO.saveCustomer(entity);
    }
}
