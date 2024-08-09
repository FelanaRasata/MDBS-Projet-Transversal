package itu.mbds.transversal.controller;

import itu.mbds.transversal.entity.User;
import itu.mbds.transversal.security.JwtService;
import itu.mbds.transversal.service.user.UserService;
import itu.mbds.transversal.utils.dto.request.AuthRequestDTO;
import itu.mbds.transversal.utils.dto.response.JwtResponseDTO;
import itu.mbds.transversal.utils.dto.response.ResponseMessage;
import itu.mbds.transversal.utils.enumeration.Message;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserService userService;


    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;

        this.userService = userService;
    }


    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));

            if (authentication.isAuthenticated()) {

                JwtResponseDTO jwtResponseDTO = new JwtResponseDTO();
                jwtResponseDTO.setToken(jwtService.GenerateToken(authRequestDTO.getUsername()));
                jwtResponseDTO.setUsername(authRequestDTO.getUsername());

                return ResponseEntity.status(HttpStatus.OK).body(jwtResponseDTO);

            } else {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            }
        } catch (BadCredentialsException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(Message.BAD_CREDENTIAL.toString()));

        } catch (DisabledException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseMessage(Message.ACCOUNT_DISABLED.toString()));

        }


    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> saveUser(@RequestBody User user) {

        try {

            User savedUser = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

        } catch (DataIntegrityViolationException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(Message.USERNAME_USED.toString()));

        }

    }

    @PostMapping("/admin")
    public ResponseEntity<?> saveAdmin(@RequestBody User user) {

        try {

            User savedUser = userService.saveAdmin(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

        } catch (DataIntegrityViolationException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(Message.USERNAME_USED.toString()));

        }

    }

    /*@PostMapping(value = "/sign-in")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDTO authRequestDTO) {

        try {

            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
            if (authentication.isAuthenticated()) {
                return JwtResponseDTO.builder()
                        .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()).build();
            } else {
                throw new UsernameNotFoundException("invalid user request..!!");
            }

        } catch (BadCredentialsException e) {

            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {

            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }

    }*/
}
