package com.example.gardenedennft.userrole;

import com.example.gardenedennft.artist.Artist;
import com.example.gardenedennft.role.Role;
import com.example.gardenedennft.utils.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_user_role")
@Entity
public class UserRole extends BaseEntity {

    @ManyToOne
    @JoinColumn
    private Role role;
    @ManyToOne
    @JoinColumn
    private Artist artist;
}
