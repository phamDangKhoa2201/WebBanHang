package com.project.shopaap.controllers;

import com.project.shopaap.models.Role;
import com.project.shopaap.services.ICategoryService;
import com.project.shopaap.services.IRoleService;
import com.project.shopaap.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RolesController {
    private final RoleService roleService;
    @GetMapping("/all")
    public ResponseEntity<?> getAllRole(){
        List<Role> roles= roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
}
