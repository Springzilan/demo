package com.example.demo.mapper;

import com.example.demo.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
//mapper层UserMapper接口,也是sql具体实现方法的地方，用相应解释器加上sql语句配置相应查询
public interface UserMapper {
    @Select({
            "select",
            "userid, password, email, username",
            "from user"
    })
    List<User> selectAll();

    @Insert("insert into User(userid,username,password,email) values(#{userid},#{username},#{password},#{email})")
    int insertUser(User user);

    @Select("select userid,username from User where username=#{username} and password = #{password}")
    User login(String username,String password);

    @Select("select email from User where email = #{email}")
    User findEmail(String email);

    @Select("select username from User where username = #{username}")
    User findUsername(String username);

    @Select("select id from User where userid = #{id}")
    User findId(String id);
}
