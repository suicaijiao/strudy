package com.study.cae.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.cae.entity.UmsContractHistory;
import com.study.cae.mapper.UmsContractHistoryMapper;
import com.study.cae.service.IUmsContractHistoryService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户合约关联表 服务实现类
 * </p>
 *
 * @author soonphe
 * @since 2020-05-30
 */
@Service
public class UmsContractHistoryServiceImpl extends ServiceImpl<UmsContractHistoryMapper, UmsContractHistory> implements IUmsContractHistoryService {


    @Override
    public List<UmsContractHistory> getPageList(int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<UmsContractHistory> result = this.list();
        return result;
    }
}
