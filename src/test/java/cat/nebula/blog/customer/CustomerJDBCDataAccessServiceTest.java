package cat.nebula.blog.customer;

import cat.nebula.blog.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private CustomerJDBCDataAccessService underTest;
    private CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {

        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper);

    }

    @Test
    void selecAllCustomers() {

        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().emailAddress() + "-" + UUID.randomUUID(),
                FAKER.number().numberBetween(18, 100)
        );

        underTest.insertCustomer(customer);

        List<Customer> customers = underTest.selecAllCustomers();

        System.out.println("customers: " + customers);

        assertThat(customers).isNotEmpty();

    }

    @Test
    void selectCustomerById() {

        String email = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();

        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(18, 100)
        );

        underTest.insertCustomer(customer);

        long id = underTest.selecAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Optional<Customer> actual = underTest.selectCustomerById(id);

        System.out.println("actual: " + actual);

        assertThat(actual)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(customer.getName());
                    assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                    assertThat(c.getAge()).isEqualTo(customer.getAge());
                });

    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {

        Long id = -1L;

        var actual = underTest.selectCustomerById(id);

        assertThat(actual).isEmpty();

    }

    @Test
    void insertCustomer() {

        String email = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();

        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(18, 100)
        );

        underTest.insertCustomer(customer);

        List<Customer> customers = underTest.selecAllCustomers();

        assertThat(customers)
                .isNotEmpty()
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(customer);

    }

    @Test
    void existsCustomerByEmail() {

        String email = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();

        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(18, 100)
        );

        underTest.insertCustomer(customer);

        boolean actual = underTest.existsCustomerByEmail(email);

        assertThat(actual).isTrue();

    }

    @Test
    void existsCustomerByEmailReturnsFalseWhenEmailDoesNotExist() {

        String email = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();

        boolean actual = underTest.existsCustomerByEmail(email);

        assertThat(actual).isFalse();

    }

    @Test
    void deleteCustomerById() {

        String email = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();

        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(18, 100)
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selecAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        underTest.deleteCustomerById(id);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isNotPresent();


    }

    @Test
    void existsCustomerById() {

        String email = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();

        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(18, 100)
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selecAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        boolean actual = underTest.existsCustomerById(id);

        assertThat(actual).isTrue();

    }
    @Test
    void existsCustomerByIdWillReturnFalseWhenIdDoesNotExist() {

        Long id = -1L;

        boolean actual = underTest.existsCustomerById(id);

        assertThat(actual).isFalse();

    }

    @Test
    void willUpdateAllPropertiesCustomer() {

        String email = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();

        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(18, 100)
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selecAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Customer update = new Customer();
        update.setId(id);
        update.setName("Bar");
        update.setEmail(UUID.randomUUID() + "-" + FAKER.internet().emailAddress());
        update.setAge(FAKER.number().numberBetween(18, 100));

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual)
                .isPresent()
                .hasValue(update);

    }

    @Test
    void willNotUpdateWhenNothingToUpdate() {

        String email = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();

        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(18, 100)
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selecAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Customer update = new Customer();
        update.setId(id);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(customer.getName());
                    assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                    assertThat(c.getAge()).isEqualTo(customer.getAge());
                });

    }

    @Test
    void updateCustomerName() {

        String email = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();

        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(18, 100)
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selecAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        String newName = "Foo";

        Customer update = new Customer();
        update.setId(id);
        update.setName(newName);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(newName);
                    assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                    assertThat(c.getAge()).isEqualTo(customer.getAge());
                });

    }

    @Test
    void updateCustomerEmail() {

        String email = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();

        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(18, 100)
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selecAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        String newEmail = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();

        Customer update = new Customer();
        update.setId(id);
        update.setEmail(newEmail);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(customer.getName());
                    assertThat(c.getEmail()).isEqualTo(newEmail);
                    assertThat(c.getAge()).isEqualTo(customer.getAge());
                });

    }

    @Test
    void updateCustomerAge() {

        String email = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();

        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(18, 100)
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selecAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Integer newAge = FAKER.number().numberBetween(18, 100);

        Customer update = new Customer();
        update.setId(id);
        update.setAge(newAge);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(customer.getName());
                    assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                    assertThat(c.getAge()).isEqualTo(newAge);
                });

    }

}