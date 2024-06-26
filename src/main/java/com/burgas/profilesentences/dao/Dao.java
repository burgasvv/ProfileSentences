package com.burgas.profilesentences.dao;

import com.burgas.profilesentences.entity.Entity;
import com.burgas.profilesentences.manager.PropertiesManager;

import java.sql.Connection;
import java.util.List;

public abstract class Dao<T extends Entity> {

    protected final Connection connection = PropertiesManager.createConnection();

    public abstract T getByName(String name);

    public abstract T getByEmail(String email);

    public abstract List<T> getAll();

    public abstract void insert(T t);

    public abstract void update(T t);

    public abstract void delete(int id);
}
