package com.web.core.dao;

import com.web.core.entity.UsersTable;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by shenzhiqiang on 16/2/2.
 */

@Repository
public interface IUsersTableDao {
    UsersTable selectByName(String name);
    int updatePasswd(Map<String, Object> params);
}
