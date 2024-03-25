package com.burgas.profilesentences.entity;

import com.burgas.profilesentences.dao.UserRoleDao;

public class Main {

    public static void main(String[] args) {

        UserRoleDao userRoleDao = new UserRoleDao();
        userRoleDao.insert(new UserRole(
                new User(4), new Role(1)
        ));
    }
}
