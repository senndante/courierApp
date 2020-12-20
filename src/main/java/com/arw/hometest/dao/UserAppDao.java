package com.arw.hometest.dao;


import com.arw.hometest.mapper.UserAppMapper;
import com.arw.hometest.model.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional
public class UserAppDao extends JdbcDaoSupport {
    @Autowired
    public UserAppDao(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public UserApp findUserAccount(String userName) {
        String sql = UserAppMapper.BASE_SQL + " where u.name = ? ";

        Object[] params = new Object[] { userName };
        UserAppMapper mapper = new UserAppMapper();

        try {
            UserApp userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
