package com.study.cae.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.cae.commons.enums.CommonsEnum;
import com.study.cae.commons.exception.entity.BusinessException;
import com.study.cae.commons.global.result.ResultCode;
import com.study.cae.dto.OperationTypeDto;
import com.study.cae.entity.Type;
import com.study.cae.mapper.TypeMapper;
import com.study.cae.service.TypeService;
import com.study.cae.vo.QueryTypeCountVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.*;

/**
 * @description
 * @author: suicaijiao
 * @create: 2020-05-23 08:50
 **/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    // 线程池
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 30,
            0L, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>(20));

    @Resource
    private TypeMapper typeMapper;

    /**
     * 查询实体
     *
     * @param id
     * @return
     */
    @PostConstruct
    @Override
    public Type selectByPrimaryKey(Integer id) {
        return typeMapper.selectById(id);
    }

    /**
     * 修改
     *
     * @param typeDto
     * @return
     */
    @Override
    @CacheEvict(value = "TypeService", key = "#typeDto.rootId")
    public boolean updateById(OperationTypeDto typeDto) {
        Type type = this.selectByPrimaryKey(typeDto.getId());
        if (type.getRootId() != null) {
            typeDto.setRootId(type.getRootId() + "");
        }

        if (type == null) {
            throw new BusinessException(ResultCode.SYS_COM_GETDATA_EMPTY_DATA, "操作数据不存在");
        }
        //判断是否是定级父类
        if (type.getParentId().equals(0)) {
            type.setDescription(typeDto.getDescription());
        } else {
            BeanUtils.copyProperties(typeDto, type);
        }
        type.setTypeName(typeDto.getTypeName());
        type.setSysModified(new Date());
        return this.updateById(type);
    }

    /**
     * 返回所有类别层级区分
     *
     * @return
     */
    @Override
    @Cacheable(value = "TypeService", key = "#rootId")
    public List<Map<String, Object>> listToTreeByParentId(Integer rootId) {
        List<Type> typeList = typeMapper.selectList(new QueryWrapper<Type>().lambda()
                .eq(Type::getRootId, rootId)
                .eq(Type::getIsDelete, CommonsEnum.IS_DELETE_FALSE.getStatus())
        );
        if (CollectionUtils.isEmpty(typeList)) {
            return new ArrayList<>();
        }
        Callable<List<Map<String, Object>>> resultCallable = new Callable() {
            @Override
            public List<Map<String, Object>> call() throws Exception {
                List<Map<String, Object>> result = getTypeTreeList(2, rootId, typeList);
                return result;
            }
        };

        try {
            Future<List<Map<String, Object>>> typeResult = executor.submit(resultCallable);
//            SuiFutureTask<List<Map<String, Object>>> futureTask = new SuiFutureTask<>(resultCallable);

            return typeResult.get();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }


    /**
     * 返回所有类别层级区分
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> listToTree() {
        List<Type> typeList = this.list();
        if (CollectionUtils.isEmpty(typeList)) {
            return new ArrayList<>();
        }

        Callable<List<Map<String, Object>>> resultCallable = new Callable() {
            @Override
            public List<Map<String, Object>> call() throws Exception {
                List<Map<String, Object>> result = getTypeTreeList(1, 0, typeList);
                return result;
            }
        };
        try {
            Future<List<Map<String, Object>>> typeResult = executor.submit(resultCallable);
            return typeResult.get();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

//        List<Map<String, Object>> result = getTypeTreeList(1, 0, typeList);
//        return result;
    }

    /**
     * 单条删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteByPrimaryKey(Integer id) {
        Type type = this.selectByPrimaryKey(id);
        if (type == null) {
            throw new BusinessException(ResultCode.SYS_COM_GETDATA_EMPTY_DATA, "操作数据不存在");
        }
        //判断是否是定级父类
        if (type.getParentId().equals(0)) {
            throw new BusinessException(ResultCode.SYS_COM_GETDATA_EMPTY_DATA, "定级父类不允许修改");
        }

        type.setIsDelete(CommonsEnum.IS_DELETE_TRUE.getStatus());
        return this.updateById(type);
    }

    /**
     * 修改状态
     *
     * @param id
     * @return
     */
    @Override
    public boolean updateStatusById(Integer id) {
        Type type = typeMapper.selectById(id);
        if (type == null) {
            throw new BusinessException(ResultCode.SYS_COM_GETDATA_EMPTY_DATA, "修改失败");
        }
        //判断是否是定级父类
        if (type.getParentId().equals(0)) {
            throw new BusinessException(ResultCode.SYS_COM_GETDATA_EMPTY_DATA, "定级父类不允许修改");
        }
        if (type.getStatus().equals(CommonsEnum.IS_ENABLED_YES.getStatus())) {
            type.setStatus(CommonsEnum.IS_ENABLED_NO.getStatus());
        } else {
            type.setStatus(CommonsEnum.IS_ENABLED_YES.getStatus());
        }
        type.setSysModified(new Date());
        return this.updateById(type);
    }

    /**
     * 根据父节点查询
     *
     * @param parentId
     * @return
     */
    @Override
    public List<Type> selectByParentId(Integer parentId) {
        List<Type> typeList = typeMapper.selectList(new QueryWrapper<Type>().lambda().eq(Type::getParentId, parentId).eq(Type::getStatus, CommonsEnum.IS_ENABLED_YES.getStatus()));
        return typeList;
    }

    /**
     * 根据名称查询
     *
     * @param typeName
     * @return
     */
    @Override
    public Type getByTypeName(String typeName) {
        List<Type> typeList = typeMapper.selectList(new QueryWrapper<Type>().lambda().eq(Type::getTypeName, typeName).eq(Type::getStatus, CommonsEnum.IS_ENABLED_YES.getStatus()));
        if (CollectionUtils.isEmpty(typeList)) {
            return null;
        }
        return typeList.get(0);
    }

    /**
     * 查询数据返回树节点
     *
     * @param level
     * @param parentId
     * @param typeList
     * @return
     */
    private List<Map<String, Object>> getTypeTreeList(int level, Integer parentId, List<Type> typeList) {
        List<Map<String, Object>> typeChildrenList = new ArrayList<>();
        for (Type type : typeList) {
            if (type.getParentId().equals(parentId)) {
                Map<String, Object> catalogMap = new LinkedHashMap<>();
                catalogMap.putAll(JSONObject.parseObject(JSON.toJSON(type).toString()));

                List<Map<String, Object>> typeTreeList = new ArrayList<>();
                //查询子目录
                QueryTypeCountVo query = new QueryTypeCountVo();
                query.setLevel(level + 1);
                // 相当于父节点
                query.setParentId(type.getId());
                if (typeMapper.countByLevel(query) > 0) {
                    typeTreeList = getTypeTreeList(level + 1, type.getId(), typeList);
                }
                catalogMap.put("children", typeTreeList);
                typeChildrenList.add(catalogMap);
            }
        }
        return typeChildrenList;
    }

    /**
     * 读取缓存导航条
     *
     * @return
     */
    @Override
    public List getNavFileList() {
        InputStream is = this.getClass().getResourceAsStream("/nav-file.json");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder jsonStr = new StringBuilder();
            String nav = "";
            while ((nav = br.readLine()) != null) {
                jsonStr.append(nav.trim());
            }
            is.close();
            br.close();
            return JSON.parseArray(jsonStr.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
