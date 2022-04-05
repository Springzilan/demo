package com.example.demo.service;

import com.example.demo.bean.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public int register(User user) {
        return userMapper.insertUser(user);
    }
    @Override
    public User login(String a,String b) {
        return userMapper.login(a,b);
    }

    @Override
    public User findEmail(String email) {
        return userMapper.findEmail(email);
    }
    @Override
    public User findUsername(String username){
        return userMapper.findUsername(username);
    }
    @Override
    public User findId(String id){
        return userMapper.findId(id);
    }
}
