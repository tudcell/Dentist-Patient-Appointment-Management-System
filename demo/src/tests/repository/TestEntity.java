package tests.repository;

import main.domain.Identifiable;

import java.io.Serializable;

public class TestEntity implements Identifiable<Integer>, Serializable {
    private Integer id;
    private final String name;

    public TestEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public TestEntity(String name) {
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}

