package com.mbiger.common.model.merchant.dao;

import com.mbiger.common.db.AbstractBaseDao;
import com.mbiger.common.model.merchant.bean.Merchant;
import org.springframework.stereotype.Repository;

@Repository
public class MerchantDao extends AbstractBaseDao {
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    public int insert(Merchant record) {
        return 0;
    }

    public int insertSelective(Merchant record) {
        return 0;
    }

    public Merchant selectByPrimaryKey(Integer id) {
        return null;
    }

    public int updateByPrimaryKeySelective(Merchant record) {
        return 0;
    }

    public int updateByPrimaryKey(Merchant record) {
        return 0;
    }
}