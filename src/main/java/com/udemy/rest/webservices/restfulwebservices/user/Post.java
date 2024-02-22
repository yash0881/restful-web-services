package com.udemy.rest.webservices.restfulwebservices.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;


@Entity
public class Post {

    @Id
    @GeneratedValue
    private int id;
    @Size(min = 10)
    private  String desc;

    @ManyToOne (fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "id='" + id + '\'' +
                ", desc=" + desc +
                '}';
    }
}
