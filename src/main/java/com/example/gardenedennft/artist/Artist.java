package com.example.gardenedennft.artist;


import com.example.gardenedennft.owner.Owner;
import com.example.gardenedennft.token.Token;
import com.example.gardenedennft.userrole.UserRole;
import com.example.gardenedennft.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_artist")
public class Artist extends BaseEntity implements UserDetails {
    private String email;
    private String name;
    private String symbol;
    @Column(nullable = true)
    private String image_url;
    @Column(nullable = true)
    private String discord_url;
    @Column(nullable = true)
    private String twitter_url;
    @Column(nullable = true)
    private String telegram_url;
    @Column(nullable = true)
    private String website_url;
    @Column(nullable = true)
    private String bio;
    private Boolean status=false;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    private List<Owner> owners;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    private List<Token> tokens;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
