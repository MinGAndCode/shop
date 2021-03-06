package com.proaim.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.proaim.entity.Commodity;
import com.proaim.entity.PageBean;
import com.proaim.mapper.CommodityMapper;
import com.proaim.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @date 2019/1/30
 */
@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    private CommodityMapper commodityMapper;

    /**
     * 分页查询
     *
     * @param commodity 查询条件
     * @param pageCode  当前页码
     * @param pageSize  每页显示记录数
     * @return PageBean 将当前页数及每页记录数返回
     */
    @Override
    public PageBean getByPageBean(Commodity commodity, Integer pageCode, Integer pageSize) {
        if (commodity != null && pageCode != null && pageSize != null) {
            // 使用MyBatis插件
            PageHelper.startPage(pageCode, pageSize);
            // 调用分页查询方法，其实就是查询所有数据，MyBatis自动帮我们进行分页计算
            Page<Commodity> page = commodityMapper.getByPage(commodity);
            return new PageBean(page.getTotal(), page.getResult());
        } else {
            return null;
        }
    }

    @Override
    public List<Commodity> listObjects() {
        return commodityMapper.listCommodity();
    }

    @Override
    public Commodity getObjectById(Long id) {
        if (id != null) {
            return commodityMapper.getCommodityById(id);
        } else {
            return null;
        }
    }

    @Override
    public Commodity getObjectByName(String name) {
        if (!StringUtils.isEmpty(name)) {
            return commodityMapper.getCommodityByName(name);
        } else {
            return null;
        }

    }

    @Override
    /**
     * TODO!
     * @Transactional 加于private方法, 无效
     * @Transactional 加于未加入接口的public方法, 再通过普通接口方法调用, 无效
     * @Transactional 加于接口方法, 无论下面调用的是private或public方法, 都有效
     * @Transactional 加于接口方法后, 被本类普通接口方法直接调用, 无效
     * @Transactional 加于接口方法后, 被本类普通接口方法通过接口调用, 有效
     * @Transactional 加于接口方法后, 被它类的接口方法调用, 有效
     * @Transactional 加于接口方法后, 被它类的私有方法调用后, 有效
     * Transactional是否生效, 仅取决于是否加载于接口方法, 并且是否通过接口方法调用(而不是本类调用)。
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveObject(Commodity commodity) {
        try {
            if (commodity != null) {
                commodityMapper.saveCommodity(commodity);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeObjects(Long... ids) {
        try {
            if (ids != null) {
                for (Long id : ids) {
                    commodityMapper.removeCommodity(id);
                }
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateObject(Commodity commodity) {
        try {
            if (commodity != null) {
                commodityMapper.updateCommodity(commodity);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }

    }
}
