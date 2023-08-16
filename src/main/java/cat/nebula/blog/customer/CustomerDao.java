package cat.nebula.blog.customer;

import java.util.List;
import java.util.Optional;


public interface CustomerDao {

    List<Customer> selecAllCustomers();
    Optional<Customer> selectCustomerById(Integer id);
    void inserCustomer(Customer customer);
    boolean existsCusotmerByEmail(String email);
    void deleteCustomerById(Integer id);
    boolean existsCustomerById(Integer id);
    void updateCustomer(Customer customer);

}
