package com.example.gardenedennft.userrole;

import com.example.gardenedennft.artist.Artist;
import com.example.gardenedennft.role.Role;
import com.example.gardenedennft.utils.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "_user_role")
public class UserRole extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "_id_Role")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "_id_Artist")
    private Artist artist;
}
