package cat.nebula.blog.customer;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao{

    private static final List<Customer> customers;

    static {
        customers = new ArrayList<>();

        Customer alex = new Customer(
                1L,
            "Alex",
            "alex@nebula.cat",
            25
        );
        customers.add(alex);

        Customer maria = new Customer(
                2L,
            "Maria",
            "maria@nebula.cat",
            30
        );
        customers.add(maria);
    }

    @Override
    public List<Customer> selecAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {

        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();

    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsCustomerByEmail(String email) {
        return customers.stream()
                .anyMatch(customer -> customer.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerById(Long id) {
        return customers.stream()
                .anyMatch(customer -> customer.getId().equals(id));
    }

    @Override
    public void deleteCustomerById(Long id) {
        customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .ifPresent(customers::remove);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customers.add(customer);
    }

}
