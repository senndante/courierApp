package com.arw.hometest.service;


import com.arw.hometest.dao.CourierDao;
import com.arw.hometest.dao.OperatorDao;
import com.arw.hometest.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("courierService")
@Transactional
public class CourierService {
    @Autowired
    private CourierDao courierDao;

    @Autowired
    private OperatorDao operatorDao;

    public List<String> getTasks(String userName) {
        List<String> tasks = courierDao.findCourierTasks(userName);
        return tasks;
    }

    public void setTaskDisabled(String orderCode) {
        Long courierTaskIdForDisabled = courierDao.getIdTaskForDisable(orderCode);
        courierDao.setTaskDisabled(courierTaskIdForDisabled);
        UserDto userDto = operatorDao.getOperatorForTask();
        operatorDao.createOperatorTask(userDto.getId(), courierTaskIdForDisabled);
    }
}


