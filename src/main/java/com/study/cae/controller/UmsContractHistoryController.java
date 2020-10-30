package com.study.cae.controller;

import com.study.cae.commons.global.result.CommonPage;
import com.study.cae.commons.global.result.ResponseResult;
import com.study.cae.entity.UmsContractHistory;
import com.study.cae.service.IUmsContractHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract")
public class UmsContractHistoryController {

    @Autowired
    private IUmsContractHistoryService historyService;

    @GetMapping("/{id}")
    public ResponseResult findContractHistory(@PathVariable Integer id) {
        UmsContractHistory contractHistory = historyService.getById(id);
        return ResponseResult.success(contractHistory);
    }

    @GetMapping("/page")
    public ResponseResult findPageList(@RequestParam(required = false, defaultValue = "1") int pageNum,
                                       @RequestParam(required = false, defaultValue = "10") int pageSize) {
        List<UmsContractHistory> result = historyService.getPageList(pageNum, pageSize);
        return ResponseResult.success(CommonPage.restPage(result));
    }
}


