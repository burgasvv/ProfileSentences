package com.burgas.questionsandauth.entity;

public class Role extends Entity{

    private String name;

    public Role(int id) {
        super(id);
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        //noinspection StringTemplateMigration
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }
}
