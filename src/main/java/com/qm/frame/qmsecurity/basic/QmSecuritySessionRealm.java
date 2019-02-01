package com.qm.frame.qmsecurity.basic;


/**
 * Copyright © 2019浅梦工作室. All rights reserved.
 *
 * @author 浅梦
 * @date 2019/2/2 1:14
 * @Description session-Realm
 */
public interface QmSecuritySessionRealm {
    /**
     * 当用户登录时触发
     * @param user
     */
    void inUser(Object user);

    /**
     * 当用户失效或退出时触发
     * @param user
     */
    void outUser(Object user);


}
