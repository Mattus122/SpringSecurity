package com.example.AuthenticationProject.controller;

import com.example.AuthenticationProject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/controller")
@RequiredArgsConstructor
public class AdminController {


    private final AdminService adminService;
    @GetMapping("/hello")
    public String hellowworld(){
        return "CAN BE ACCESSED ONLY BY ADMIN";

    }
    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<HttpStatus> deleteuser(@PathVariable Long id) throws Exception{
         adminService.delete(id);
         return new ResponseEntity<>(HttpStatus.OK);
    }
}
