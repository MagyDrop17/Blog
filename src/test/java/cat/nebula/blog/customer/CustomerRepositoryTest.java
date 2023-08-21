package cat.nebula.blog.customer;

import cat.nebula.blog.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private CustomerRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        System.out.println("applicationContext = " + applicationContext.getBeanDefinitionCount());
    }

    @Test
    void existsCustomerByEmail() {

        String email = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();

        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(18, 100)
        );

        underTest.save(customer);

        boolean actual = underTest.existsCustomerByEmail(email);

        System.out.println("actual: " + actual);

        assertThat(actual)
                .isTrue();

    }

    @Test
    void existsCustomerByEmailFailsWhenEmailNotPresent() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        // When
        var actual = underTest.existsCustomerByEmail(email);

        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsCustomerById() {

        String email = FAKER.internet().emailAddress() + "-" + UUID.randomUUID();

        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(18, 100)
        );

        underTest.save(customer);

        long id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        boolean actual = underTest.existsCustomerById(id);

        System.out.println("actual: " + actual);

        assertThat(actual)
                .isTrue();

    }

    @Test
    void existsCustomerByIdFailsWhenIdNotPresent() {
        // Given
        Long id = -1L;

        // When
        var actual = underTest.existsCustomerById(id);

        // Then
        assertThat(actual).isFalse();
    }

}