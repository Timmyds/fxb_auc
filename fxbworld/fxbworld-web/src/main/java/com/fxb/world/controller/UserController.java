package com.fxb.world.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fxb.world.entity.TUser;
import com.fxb.world.service.impl.UserServiceImpl;
import com.fxb.world.util.Redis.RedisSlave;
import com.github.pagehelper.PageInfo;

/**
 * Created by yangqj on 2017/4/22.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private UserServiceImpl userServiceImpl;
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    
    @RequestMapping(value = "/findUser")
    @ResponseBody
    public Map<String,Object> getAll(TUser user, String draw,
                                     @RequestParam(required = false, defaultValue = "1") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length){
        Map<String,Object> map = new HashMap<>();
        PageInfo<TUser> pageInfo = userServiceImpl.selectByPage(user, 1, 10);
        System.out.println("pageInfo.getTotal():"+pageInfo.getTotal());
        map.put("draw",draw);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    @RequestMapping(value = "/findUsers")
    @ResponseBody
    public PageInfo<TUser> findUsers(TUser user,
                                     @RequestParam(required = false, defaultValue = "1") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length){
        Map<String,Object> map = new HashMap<>();
        PageInfo<TUser> pageInfo = userServiceImpl.queryPage("", 1, 10);
        System.out.println("pageInfo.getTotal():"+pageInfo.getTotal());
       
        return pageInfo;
    }
    @RequestMapping(value = "/getUser")
    @ResponseBody
    public TUser getUser(Long id){
      try{
          TUser selectByUsername = userServiceImpl.selectByUsername(id);
         /* ValueOperations<String, String> vo = redisTemplate.opsForValue();  
          vo.set("test_key", "4561632456-----------------");  
    
 
          System.out.println(redisTemplate.opsForValue().get("test_key"));*/
          return selectByUsername;
      }catch (Exception e){
          e.printStackTrace();
          return null;
      }
    }
    

    @RequestMapping(value = "/delete")
    public String delete(Integer id){
      try{
    	  userServiceImpl.delUser(id);
          return "success";
      }catch (Exception e){
          e.printStackTrace();
          return "fail";
      }
    }

}
