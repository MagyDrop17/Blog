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
                1,
            "Alex",
            "alex@nebula.cat",
            25
        );
        customers.add(alex);

        Customer maria = new Customer(
                2,
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
    public Optional<Customer> selectCustomerById(Integer id) {

        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();

    }

}
