package cat.nebula.blog.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selecAllCustomers() {

        var sql = """
                SELECT id, name, email, age
                FROM customer
                """;

        return jdbcTemplate.query(sql, customerRowMapper);

    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {

        var sql = """
                SELECT id, name, email, age
                FROM customer
                WHERE id = ?
                """;

        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream()
                .findFirst();

    }

    @Override
    public void insertCustomer(Customer customer) {

        var sql = """
                INSERT INTO customer (name, email, age)
                VALUES (?, ?, ?)
                """;

        int result = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());

        System.out.println("JdbcTemplate: " + jdbcTemplate);

        System.out.println("insertCustomer: " + result);

    }

    @Override
    public boolean existsCustomerByEmail(String email) {

        var sql = """
                SELECT count(id)
                FROM customer
                WHERE email = ?
                """;

        Long count = jdbcTemplate.queryForObject(sql, Long.class, email);
        return count != null && count > 0;

    }

    @Override
    public void deleteCustomerById(Long customerId) {

        var sql = """
                DELETE FROM customer
                WHERE id = ?
                """;

        int result = jdbcTemplate.update(sql, customerId);
        System.out.println("deleteCustomerById: " + result);

    }

    @Override
    public boolean existsCustomerById(Long customerId) {

        var sql = """
                SELECT count(id)
                FROM customer
                WHERE id = ?
                """;

        Long count = jdbcTemplate.queryForObject(sql, Long.class, customerId);

        return count != null && count > 0;

    }

    @Override
    public void updateCustomer(Customer customer) {

        if (customer.getName() != null) {
            String sql = """
                    UPDATE customer
                    SET name = ?
                    WHERE id = ?
                    """;

            int result = jdbcTemplate.update(sql, customer.getName(), customer.getId());
            System.out.println("updateCustomer: " + result);
        }

        if (customer.getAge() != null) {
            String sql = """
                    UPDATE customer
                    SET age = ?
                    WHERE id = ?
                    """;

            int result = jdbcTemplate.update(sql, customer.getAge(), customer.getId());
            System.out.println("updateCustomer: " + result);
        }

        // check if email exists
        if (customer.getEmail() != null) {
            String sql = """
                    UPDATE customer
                    SET email = ?
                    WHERE id = ?
                    """;

            int result = jdbcTemplate.update(sql, customer.getEmail(), customer.getId());
            System.out.println("updateCustomer: " + result);
        }

    }
}
