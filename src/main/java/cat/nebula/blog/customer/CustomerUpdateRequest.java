package cat.nebula.blog.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
