
package com.glassvisionai.glassvisionai.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users") // Equivalent to @Table in JPA
public class User {

    @Id
    private String id;  // MongoDB uses String ObjectId as the primary key

    @Field(name = "name")
    private String name;

    @Field(name = "username")
    private String username;

    @Field(name = "password")
    private String password;

    // Default Constructor
    public User() {}

    // Parameterized Constructor
    public User(String id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString() method for debugging/logging
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}
