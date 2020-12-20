package com.arw.hometest.dao;


import com.arw.hometest.dto.OperatorTaskDto;
import com.arw.hometest.dto.UserDto;
import com.arw.hometest.mapper.UserDtoMapper;
import com.arw.hometest.mapper.OperatorTaskDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository("operatorDao")
public class OperatorDao extends JdbcDaoSupport {

    @Autowired
    public OperatorDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public UserDto getOperatorForTask(){
        String sql = "select q.id, q.NAME, coalesce(count(ot.id), 0) count_tasks from " +
                "(select u.id, u.NAME from sdekbase.users u, user_role ur, roles r " +
                "   where u.id = ur.USER_ID and r.ID = ur.ROLE_ID and r.NAME = 'ROLE_OPERATOR') q " +
                "left join operator_tasks ot on q.ID = ot.user_id " +
                "group by q.id " +
                "order by count_tasks " +
                "limit 1";

        UserDtoMapper mapper = new UserDtoMapper();
        UserDto userDto = this.getJdbcTemplate().queryForObject(sql, mapper);
        return userDto;
    }

    public List<OperatorTaskDto> findOperatorTasks(String userName) {
        String sql = "select o.code code, ot.datetime datetime" +
                " from sdekbase.users u, operator_tasks ot, courier_tasks ct, orders o " +
                " where u.NAME = ? and u.id = ot.user_id " +
                "   and ct.id = ot.courier_task_id and ct.order_id = o.id " +
                " order by code";

        Object[] params = new Object[] { userName };
        OperatorTaskDtoMapper mapper = new OperatorTaskDtoMapper();
        List<OperatorTaskDto> tasks = this.getJdbcTemplate().query(sql, params, mapper);
        return tasks;
    }

    

    public int createOperatorTask(Long userId, Long courierDisabledTaskId){
        String sql = "insert into operator_tasks (user_id, courier_task_id, datetime) " +
                "values (?, ?, ?)";

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);
        Object[] params = new Object[] { userId, courierDisabledTaskId, formatDateTime};
        return this.getJdbcTemplate().update(sql, params);
    }
}
