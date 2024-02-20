package com.example.gardenedennft.role;


import com.example.gardenedennft.userrole.UserRole;
import com.example.gardenedennft.utils.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_role")
public class Role extends BaseEntity {
    private String name;
    private String code;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;
}
