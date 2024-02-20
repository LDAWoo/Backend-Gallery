package com.example.gardenedennft.configuration;


import com.example.gardenedennft.artist.ArtistResponse;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import com.example.gardenedennft.owner.OwnerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final ArtistResponse artistResponse;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> artistResponse.findOwnerByEmailAndStatus(email, true)
                .orElseThrow(() -> new ResourceNotFoundException("find owner by email " + email + " not found!."));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
