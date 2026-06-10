package com.atguigu.lease.web.app.mapper;

import com.atguigu.lease.model.entity.AttrValue;
import com.atguigu.lease.web.app.vo.attr.AttrValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author   
* @description 针对表【attr_value(房间基本属性值表)】的数据库操作Mapper
* @createDate     11:12:39
* @Entity com.atguigu.lease.model.entity.AttrValue
*/
public interface AttrValueMapper extends BaseMapper<AttrValue> {

    List<AttrValueVo> selectListByRoomId(Long id);
}




