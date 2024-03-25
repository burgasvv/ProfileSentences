package com.burgas.questionsandauth.entity;

import com.burgas.questionsandauth.dao.UserRoleDao;

public class Main {

    public static void main(String[] args) {

        UserRoleDao userRoleDao = new UserRoleDao();
        userRoleDao.insert(new UserRole(
                new User(4), new Role(1)
        ));
    }
}
