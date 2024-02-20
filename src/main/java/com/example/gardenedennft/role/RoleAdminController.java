package com.example.gardenedennft.role;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/role")
@RequiredArgsConstructor
public class RoleAdminController {

    private final RoleService roleService;

    @PreAuthorize("hasAnyAuthority(@adminMaster)")
    @PostMapping("/add")
    public ResponseEntity<?> addRole(
            @Validated @RequestBody RoleRequest request
    ) {
        roleService.addRole(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority(@adminMaster)")
    @PutMapping("/update")
    public ResponseEntity<?> updateRole(
            @Validated @RequestBody RoleRequest request,
            @RequestParam("rid") UUID rid,
            Errors errors
    ) {
        roleService.updateRole(request, rid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority(@adminMaster)")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRole(@RequestParam("rid") UUID rid) {
        roleService.deleteRole(rid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority(@adminMaster)")
    @GetMapping("/get-all")
    public ResponseEntity<RoleResponse> selectAllRole() {
        return new ResponseEntity<>(roleService.findAllRole(), HttpStatus.OK);
    }
}
