package com.atguigu.lease.web.app.service;

import com.atguigu.lease.model.entity.PaymentType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author   
* @description 针对表【payment_type(支付方式表)】的数据库操作Service
* @createDate     11:12:39
*/
public interface PaymentTypeService extends IService<PaymentType> {
    List<PaymentType> listByRoomId(Long id);
}
