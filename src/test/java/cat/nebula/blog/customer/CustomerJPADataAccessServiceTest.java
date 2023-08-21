package cat.nebula.blog.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selecAllCustomers() {

        underTest.selecAllCustomers();

        verify(customerRepository)
                .findAll();

    }

    @Test
    void selectCustomerById() {

        Long id = 1L;

        underTest.selectCustomerById(id);

        verify(customerRepository)
                .findById(id);

    }

    @Test
    void insertCustomer() {

        Customer customer = new Customer(
                1L, "Gabi", "gabi@nebula.cat", 18
        );

        // When
        underTest.insertCustomer(customer);

        // Then
        verify(customerRepository).save(customer);

    }

    @Test
    void existsCustomerByEmail() {

        String email = "foo@gmail.com";

        // When
        underTest.existsCustomerByEmail(email);

        // Then
        verify(customerRepository).existsCustomerByEmail(email);

    }

    @Test
    void deleteCustomerById() {

        Long id = 1L;

        // When
        underTest.deleteCustomerById(id);

        // Then
        verify(customerRepository).deleteById(id);

    }

    @Test
    void existsCustomerById() {

        Long id = 1L;

        // When
        underTest.existsCustomerById(id);

        // Then
        verify(customerRepository).existsCustomerById(id);

    }

    @Test
    void updateCustomer() {

        Customer customer = new Customer(
                1L, "Gabi", "gabi@nebula.cat", 18
        );

        // When
        underTest.updateCustomer(customer);

        // Then
        verify(customerRepository).save(customer);

    }

}