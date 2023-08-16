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

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selecAllCustomers();
    }

    public Customer getCustomer(Integer id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer " + id + " does not exist"));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {

        String email = customerRegistrationRequest.email();

        if (customerDao.existsCusotmerByEmail(email)) {
            throw new DuplicateResourceException("Email already exists");
        }

        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                email,
                customerRegistrationRequest.age()
        );

        customerDao.inserCustomer(customer);

    }

    public void deleteCustomer(Integer customerId) {

        if (!customerDao.existsCustomerById(customerId)) {
            throw new ResourceNotFoundException("Customer " + customerId + " not found");
        }

        customerDao.deleteCustomerById(customerId);

    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest customerUpdateRequest) {

        // check if customer exists

        if (!customerDao.existsCustomerById(customerId)) {
            throw new ResourceNotFoundException("Customer " + customerId + " not found");
        }

        // get customer
        Customer customer = getCustomer(customerId);

        boolean changes = false;

        if (customerUpdateRequest.name() != null && !customerUpdateRequest.name().equals(customer.getName())) {
            customer.setName(customerUpdateRequest.name());
            changes = true;
        }

        if (customerUpdateRequest.email() != null && !customerUpdateRequest.email().equals(customer.getEmail())) {

            if (customerDao.existsCusotmerByEmail(customerUpdateRequest.email())) {
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
