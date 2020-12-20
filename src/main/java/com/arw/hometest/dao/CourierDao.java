package com.arw.hometest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository("courierDao")
public class CourierDao extends JdbcDaoSupport {
    @Autowired
    public CourierDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<String> findCourierTasks(String userName) {
        String sql = "select o.code code" +
                " from users u, courier_tasks ct, orders o " +
                " where u.name = ? and u.ID = ct.user_id and o.id = ct.order_id  and ct.enabled = 1 " +
                " order by code";

        Object[] params = new Object[] { userName };
        List<String> orders = this.getJdbcTemplate().queryForList(sql, params, String.class);
        return orders;
    }

    public Long getIdTaskForDisable(String orderCode){
        String sql = "select ct.id id from courier_tasks ct, orders o " +
                "where o.code = ? and o.id = ct.order_id and ct.enabled = 1";

        Object[] params = new Object[] { orderCode };
        Long id = this.getJdbcTemplate().queryForObject(sql, params, Long.class);
        return id;
    }

    public int setTaskDisabled(Long taskId){
        String sql = "UPDATE courier_tasks SET enabled = 0 WHERE id = ?";
        Object[] params = new Object[] { taskId };
        return this.getJdbcTemplate().update(sql, params);
    }

}
