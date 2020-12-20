package com.arw.hometest.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class OperatorTaskDto {
    private String orderCode;
    private LocalDateTime dateTime;

    public OperatorTaskDto(){}

    public OperatorTaskDto(String orderCode, LocalDateTime dateTime) {
        this.orderCode = orderCode;
        this.dateTime = dateTime;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "OperatorTaskDto{" +
                "orderCode='" + orderCode + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperatorTaskDto that = (OperatorTaskDto) o;
        return orderCode.equals(that.orderCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderCode);
    }
}
