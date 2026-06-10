package com.atguigu.lease.web.app.service;

import com.atguigu.lease.model.entity.LeaseTerm;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author   
* @description 针对表【lease_term(租期)】的数据库操作Service
* @createDate     11:12:39
*/
public interface LeaseTermService extends IService<LeaseTerm> {
    List<LeaseTerm> listByRoomId(Long id);
}
