package cat.nebula.blog.customer;

import cat.nebula.blog.exception.DuplicateResourceException;
import cat.nebula.blog.exception.RequestValidationException;
import cat.nebula.blog.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selecAllCustomers();
    }

    public Customer getCustomer(Long id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer " + id + " does not exist"));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {

        String email = customerRegistrationRequest.email();

        if (customerDao.existsCustomerByEmail(email)) {
            throw new DuplicateResourceException("Email already exists");
        }

        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                email,
                customerRegistrationRequest.age()
        );

        customerDao.insertCustomer(customer);

    }

    public void deleteCustomer(Long customerId) {

        if (!customerDao.existsCustomerById(customerId)) {
            throw new ResourceNotFoundException("Customer " + customerId + " not found");
        }

        customerDao.deleteCustomerById(customerId);

    }

    public void updateCustomer(Long customerId, CustomerUpdateRequest customerUpdateRequest) {

        // check if customer exists

        Customer customer = customerDao.selectCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer " + customerId + " not found"));

        boolean changes = false;

        if (customerUpdateRequest.name() != null && !customerUpdateRequest.name().equals(customer.getName())) {
            customer.setName(customerUpdateRequest.name());
            changes = true;
        }

        if (customerUpdateRequest.email() != null && !customerUpdateRequest.email().equals(customer.getEmail())) {

            if (customerDao.existsCustomerByEmail(customerUpdateRequest.email())) {
                throw new DuplicateResourceException("Email already exists");
            }

            customer.setEmail(customerUpdateRequest.email());
            changes = true;

        }

        if (customerUpdateRequest.age() != null && !customerUpdateRequest.age().equals(customer.getAge())) {
            customer.setAge(customerUpdateRequest.age());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No changes were provided");
        }

        customerDao.updateCustomer(customer);

    }


}
