package com.paisiwater.user.impl;

import com.paisiwater.dao.UserDao;
import com.paisiwater.model.User;
import com.paisiwater.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


//@Transactional
@Service
//@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false,isolation= Isolation.DEFAULT)
//@Transactional
public class UserServiceImpl implements UserService {
 

    //@Qualifier("myUserDao")
    @Autowired
    private UserDao userDao;

    //@Transactional(rollbackFor = Exception.class)
    //@Transactional
    public int insertUser(User user){
        // TODO Auto-generated method stub
        int u = userDao.insertUser(user);
        System.out.println("u:"+u);
        //这个时候我故意把name弄成空，数据库是不准为空的，第二次肯定报错。看第一次是否回滚了
       /* user.setName(null);
        int u1 = userDAO.insertUser(user);
        System.out.println("u1:"+u1);*/
       /* try {
            Thread.sleep(1000*60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //int exception = Integer.parseInt(user.getName());

        return u;
    }

    public List<User> getUserList() throws Exception {
        return userDao.getUserList();
    }

    public List<Map<String,Object>> getUserListMapResult() throws Exception {
        return userDao.getUserListMapResult();
    }

}