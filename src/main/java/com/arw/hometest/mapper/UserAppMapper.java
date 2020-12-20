package com.arw.hometest.mapper;

import com.arw.hometest.model.UserApp;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAppMapper implements RowMapper<UserApp> {
    public static final String BASE_SQL = "select u.id, u.name, u.encrypted_password from sdekbase.users u ";

    @Override
    public UserApp mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long userId = rs.getLong("id");
        String userName = rs.getString("name");
        String encryptedPassword = rs.getString("encrypted_password");
        return new UserApp(userId, userName, encryptedPassword);
    }
}