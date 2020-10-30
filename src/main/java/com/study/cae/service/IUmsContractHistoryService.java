package com.study.cae.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.cae.entity.UmsContractHistory;

import java.util.List;

/**
 * <p>
 * 用户合约关联表 服务类
 * </p>
 *
 * @author soonphe
 * @since 2020-05-30
 */
public interface IUmsContractHistoryService extends IService<UmsContractHistory> {


    List<UmsContractHistory> getPageList(int pageIndex, int pageSize);
}
