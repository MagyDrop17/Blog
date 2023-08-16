package cat.nebula.blog;

import cat.nebula.blog.customer.Customer;
import cat.nebula.blog.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MainBlogApp {

    public static void main(String[] args) {
        SpringApplication.run(MainBlogApp.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {

            Customer alex = new Customer(
                    "Alex",
                    "alex@nebula.cat",
                    25
            );

            Customer maria = new Customer(
                    "Maria",
                    "maria@nebula.cat",
                    30
            );

            List<Customer> customers = List.of(alex, maria);
            customerRepository.saveAll(customers);
        };

    };

}
