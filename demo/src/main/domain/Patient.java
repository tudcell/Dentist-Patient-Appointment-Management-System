package main.domain;

import java.io.Serializable;

public class Patient<ID> implements Identifiable<ID>, Serializable {
    private ID id;
    private String name;
    private int age;

    public Patient(ID id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public ID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return  id + "," + name + "," + age;
    }

    public void setId(ID id) {
        this.id = id;
    }
}