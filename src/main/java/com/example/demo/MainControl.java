package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class MainControl {
    @Autowired
    DataUserDAO dataUserDAO;

    @RequestMapping("/")
    public ResponseEntity home(@RequestParam(value="labname")String labname){

        Map response=new HashMap();
         response.put("mex","ok recieved");
        System.out.println(labname);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/createuser")
    public ResponseEntity<Map<String,String>> createuser (@RequestBody DataUser dataUser){
        System.out.println(dataUser);
        Map<String,String> resp= new HashMap<>();
        if (dataUserDAO.countuser(dataUser.getUsername())>0){
            resp.put("msg","username ia already existed");
            return new ResponseEntity<>(resp,HttpStatus.CONFLICT);
        }
        resp.put("msg","username is created");

//        System.out.println(user);
        dataUserDAO.save(dataUser);


        return new ResponseEntity<>(resp,HttpStatus.OK);

    }
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginVerify(@RequestBody DataUser datauser)
    {
        Map<String,String> resp=new HashMap<>();
        DataUser realdata=dataUserDAO.findByusername(datauser.getUsername());
        if (realdata==null)
        {
            resp.put("msg","user not existed");
            return new ResponseEntity<>(resp,HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(realdata.getPassword(), datauser.getPassword()))
        {
            resp.put("msg","password is  not matched");
            return new ResponseEntity<>(resp,HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(resp,HttpStatus.OK);
    }
}
