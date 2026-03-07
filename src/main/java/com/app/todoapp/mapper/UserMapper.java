package com.app.todoapp.mapper;

import com.app.todoapp.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Insert("insert into `user` (username, password, email) " +
            "values (#{username}, #{password}, #{email})")
    public int register(User user);

    @Select("select * from `user` where username = #{username}")
    User getUserByUsername(String username);

    @Update("update `user` set username = #{username}, password = #{password}, email = #{email} " +
            "where id = #{id}")
    void updateUser(User user);
}
