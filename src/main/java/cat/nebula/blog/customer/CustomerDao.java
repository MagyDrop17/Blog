package cat.nebula.blog.customer;

import java.util.List;
import java.util.Optional;


public interface CustomerDao {

    List<Customer> selecAllCustomers();
    Optional<Customer> selectCustomerById(Long id);
    void inserCustomer(Customer customer);
    boolean existsCustomerByEmail(String email);
    void deleteCustomerById(Long id);
    boolean existsCustomerById(Long id);
    void updateCustomer(Customer customer);

}
