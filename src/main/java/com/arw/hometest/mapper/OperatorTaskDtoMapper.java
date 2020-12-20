package com.arw.hometest.mapper;

import com.arw.hometest.dto.OperatorTaskDto;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OperatorTaskDtoMapper implements RowMapper<OperatorTaskDto> {
    @Override
    public OperatorTaskDto mapRow(ResultSet rs, int rowNum) throws SQLException{
        String orderCode = rs.getString("code");
        LocalDateTime dateTime = rs.getTimestamp("datetime").toLocalDateTime();
        return new OperatorTaskDto(orderCode, dateTime);
    }
}
