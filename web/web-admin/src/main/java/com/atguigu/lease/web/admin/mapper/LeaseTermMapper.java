package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.LeaseTerm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author
* @description 针对表【lease_term(租期)】的数据库操作Mapper
* @createDate
* @Entity com.atguigu.lease.model.LeaseTerm
*/
public interface LeaseTermMapper extends BaseMapper<LeaseTerm> {

    List<LeaseTerm> selectListByRoomId(Long id);
}




