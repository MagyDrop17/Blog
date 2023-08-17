package cat.nebula.blog;

import cat.nebula.blog.customer.Customer;
import cat.nebula.blog.customer.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class MainBlogApp {

    public static void main(String[] args) {
        SpringApplication.run(MainBlogApp.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {

            var faker = new Faker();
            Random random = new Random();
            Name name = faker.name();
            String firstName = name.firstName();
            String lastName = name.lastName();
            Customer customer = new Customer(
                    firstName +  " " + lastName,
                    firstName.toLowerCase() + "." + lastName.toLowerCase() + "@nebula.cat",
                    random.nextInt(16, 99)
            );
            customerRepository.save(customer);

        };

    };

}
