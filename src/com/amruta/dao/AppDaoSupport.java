package com.amruta.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AppDaoSupport extends HibernateDaoSupport {
    
    public void save(Object o) throws Exception {
        this.getHibernateTemplate().save(o);
    }

    public void saveOrUpdate(Object o) throws Exception {
        this.getHibernateTemplate().saveOrUpdate(o);
    }
    
    public void refresh(Object o) throws Exception {
        this.getHibernateTemplate().refresh(o);
    }

    public void update(Object o) throws Exception {
        this.getHibernateTemplate().update(o);
    }
    
    public void delete(Object o) throws Exception {
        this.getHibernateTemplate().delete(o);
    }
}
