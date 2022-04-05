package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.User;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.util.TokenUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.util.PendingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(originPatterns = {"*"}, allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService = new UserServiceImpl();

    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public List<User> selectAll() {
        List<User> list = userService.selectAll();
        return list;
    }

    // 如果是post请求body传参，对于参数较多的，可以使用对象或者map结合@RequestBody使用接收参数，如果是少量参数，可以使用@RequestParam单个映射接收。
    @CrossOrigin
    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestBody Map map){
        User checkEmail = userService.findEmail(map.get("email").toString());//查询数据库有没有相同的邮箱
        User checkUsername = userService.findUsername(map.get("username").toString());//查询数据库有没有相同用户名
        System.out.println(checkUsername);
        System.out.println(checkEmail);
        if(checkEmail != null){
            return "email has existed";
        }
        else if(checkUsername != null){
            return "name has existed";
        }
        else {
            //创建用户对象，并赋值保存
            User user = new User();
            String userid = UUID.randomUUID().toString();//创建一个唯一标识id
            user.setUserid(userid);
            user.setUsername(map.get("username").toString());
            user.setEmail(map.get("email").toString());
            user.setPassword(map.get("password").toString());
            userService.register(user);
            return "index";
        }
    }
    @CrossOrigin
    @PostMapping("/login")
    @ResponseBody
    // 注意login函数接收数据的方式为@RequestBody,而register函数接收数据的方式为@RequestParam，这与前端axios发送数据的方式不同有关。稍后详解
    public JSONObject login(@RequestBody Map map){
        User user = userService.login(map.get("username").toString(),map.get("password").toString());
        JSONObject result = new JSONObject();
        System.out.println(user);
        if(user != null){
            String token = TokenUtil.sign("username");
            System.out.println(token);
            result.put("state",200);
            result.put("token",token);
            result.put("message", "认证成功");
        }
        else {
            result.put("state",400);
            result.put("message", "认证失败");
        }
        return result;
    }


    @PostMapping("/Info")
    public Boolean info(HttpServletRequest request) throws PendingException, Exception {
        /**
          * 从请求头信息中获取token数据
          *   1.获取请求头信息：名称=Authorization(前后端约定)
          *   2.替换Bearer+空格
          *   3.解析token
          *   4.获取clamis
        */

        //1.获取请求头信息：名称=Authorization(前后端约定)
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            //throw new PendingException(ResCode.UNAUTHENTICATED);
            //系统未捕捉到请求头信息
//            throw new CommonException(ResultCode.UNAUTHENTICATED);
        }
        //2.替换Bearer+空格
        String token = authorization.replace("Bearer ", "");

        //3.解析token
        TokenUtil tokenUtil = new TokenUtil();
        Boolean claims = tokenUtil.verify(token);
        //4.获取clamis
//        String userId = claims.getId();

        //  String userId = "U01";
//        User user = userService.findId(userId);

        /**此处只是为了获取token中的用户数据，所有只简单返回用户对象，
          * 工作则按实际要求多表查询需要数据（根据用户ID查询权限）
        */

        return claims;
//        return new Result(ResultCode.SUCCESS);
//         return new Result(ResultCode.SUCCESS, user);
    }
}
