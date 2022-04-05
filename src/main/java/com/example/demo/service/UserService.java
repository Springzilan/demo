package com.example.demo.service;
import com.example.demo.bean.User;
import java.util.List;

public interface UserService {
    public List<User> selectAll();

    int register(User user);
    User login(String a,String b);
    User findEmail(String email);
    User findId(String id);
    User findUsername(String username);
}

