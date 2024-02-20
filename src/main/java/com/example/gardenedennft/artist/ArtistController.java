package com.example.gardenedennft.artist;

import com.example.gardenedennft.owner.Owner;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gardeneden")
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping("/findArtistByEmail/{email}")
    public ResponseEntity<?> findArtistByEmail(@PathVariable String email){
        return new ResponseEntity<>(artistService.findArtistByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/findArtistBySymbol/{symbol}")
    public ResponseEntity<?> findArtistBySymbol(@PathVariable String symbol){
        return new ResponseEntity<>(artistService.findArtistBySymbol(symbol), HttpStatus.OK);
    }

    @PostMapping("/findArtistByToken")
    public ResponseEntity<?> findArtistByToken(@RequestParam("token") String token){
        return new ResponseEntity<>(artistService.findArtistByToken(token), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllArtist(){
        return new ResponseEntity<>(artistService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/authenticated")
    public ResponseEntity<?> authenticated(@RequestBody ArtistAuthenticationRequest request){
        artistService.authentication(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/auth/confirm")
    public ResponseEntity<?> confirmCreator(@RequestParam("token") String token, HttpServletResponse servletResponse) throws IOException {
        ArtistDTO artistDTO = artistService.confirmationToken(token);
        String currentToken = artistDTO.getToken();
        String redirectUrl = "http://localhost:5173/dashboard?token=" + currentToken;
        servletResponse.sendRedirect(redirectUrl);

        return new ResponseEntity<>(artistDTO,HttpStatus.OK);
    }

    @GetMapping("/auth/logout")
    public ResponseEntity<?> handleLogout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
