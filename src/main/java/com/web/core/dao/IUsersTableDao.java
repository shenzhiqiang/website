package com.web.core.dao;

import com.web.core.entity.UsersTable;
import org.springframework.stereotype.Repository;

/**
 * Created by shenzhiqiang on 16/2/2.
 */

@Repository
public interface IUsersTableDao {
    UsersTable selectByName(String name);
}
