package cat.nebula.blog.customer;

public record CustomerRegistrationRequest (
    String name,
    String email,
    Integer age
)  {

}
