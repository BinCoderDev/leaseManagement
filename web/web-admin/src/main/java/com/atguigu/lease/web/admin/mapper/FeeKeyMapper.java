package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.FeeKey;
import com.atguigu.lease.web.admin.vo.fee.FeeKeyVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author   
* @description 针对表【fee_key(杂项费用名称表)】的数据库操作Mapper
* @createDate 
* @Entity com.atguigu.lease.model.FeeKey
*/
public interface FeeKeyMapper extends BaseMapper<FeeKey> {

    List<FeeKeyVo> listFeeInfo();
}




