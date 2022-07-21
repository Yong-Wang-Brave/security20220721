package com.security.security20220721.mapper;

import com.security.security20220721.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserInfoMapper {
    @Select("select * from user where username = #{username}")
    UserInfo getUserInfoByUsername(String username);

    // 插入用户
    @Insert("insert into user(username, password, role) value(#{username}, #{password}, #{role})")
    int insertUserInfo(UserInfo userInfo);

    //原文链接：https://blog.csdn.net/bookssea/article/details/109262109
    @Update("update user set password = #{newPwd} where username = #{username}")
    int updatePwd(String username, String newPwd);
}