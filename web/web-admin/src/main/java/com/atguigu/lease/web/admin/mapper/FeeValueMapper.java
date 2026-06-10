package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.FeeValue;
import com.atguigu.lease.web.admin.vo.fee.FeeValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author   
* @description 针对表【fee_value(杂项费用值表)】的数据库操作Mapper
* @createDate 
* @Entity com.atguigu.lease.model.FeeValue
*/
public interface FeeValueMapper extends BaseMapper<FeeValue> {

    List<FeeValueVo> selectListByApartmentId(Long id);
}




