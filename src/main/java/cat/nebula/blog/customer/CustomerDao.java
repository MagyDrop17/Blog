package cat.nebula.blog.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    List<Customer> selecAllCustomers();
    Optional<Customer> selectCustomerById(Integer id);

}
