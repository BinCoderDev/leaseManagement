package com.atguigu.lease.web.app.mapper;

import com.atguigu.lease.model.entity.ViewAppointment;
import com.atguigu.lease.web.app.vo.appointment.AppointmentItemVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Mapper
* @createDate     11:12:39
* @Entity com.atguigu.lease.model.entity.ViewAppointment
*/
public interface ViewAppointmentMapper extends BaseMapper<ViewAppointment> {

    List<AppointmentItemVo> listItemByUserId(Long userId);
}




