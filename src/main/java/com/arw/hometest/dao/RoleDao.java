package com.arw.hometest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class RoleDao extends JdbcDaoSupport {
    @Autowired
    public RoleDao(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public List<String> getRoleNames(Long userId){
        String sql = "Select r.name "
                + " from user_role ur, roles r "
                + " where ur.role_id = r.id and ur.user_id = ? ";

        Object[] params = new Object[] { userId };

        List<String> roles = this.getJdbcTemplate().queryForList(sql, params, String.class);

        return roles;
    }
}
