
package com.glassvisionai.glassvisionai.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users") // Equivalent to @Table in JPA
public class User {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "username")
    private String username;

    @Field(name = "password")
    private String password;

    @Field(name = "imageId")   // Stores the ID of the uploaded image
    private String imageId;

    @Field(name = "imageName") // Stores the image filename
    private String imageName;

    // Constructors
    public User() {}

    public User(String id, String name, String username, String password, String imageId, String imageName) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.imageId = imageId;
        this.imageName = imageName;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", imageId='" + imageId + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}