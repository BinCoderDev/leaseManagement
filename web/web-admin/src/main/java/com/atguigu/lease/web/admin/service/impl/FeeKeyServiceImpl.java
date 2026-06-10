package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.FeeKey;
import com.atguigu.lease.web.admin.mapper.FeeKeyMapper;
import com.atguigu.lease.web.admin.service.FeeKeyService;
import com.atguigu.lease.web.admin.vo.fee.FeeKeyVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FeeKeyServiceImpl extends ServiceImpl<FeeKeyMapper, FeeKey>
    implements FeeKeyService{

    @Autowired
    private FeeKeyMapper feeKeyMapper;
    @Override
    public List<FeeKeyVo> listFeeInfo() {
        return feeKeyMapper.listFeeInfo();
    }
}




