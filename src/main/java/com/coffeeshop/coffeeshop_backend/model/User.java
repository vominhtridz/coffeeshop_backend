package com.coffeeshop.coffeeshop_backend.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.Set;
import java.util.HashSet;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();
}