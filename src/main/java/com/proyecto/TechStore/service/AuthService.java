package com.proyecto.TechStore.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.TechStore.dto.LoginRequestDTO;
import com.proyecto.TechStore.dto.LoginResponseDTO;
import com.proyecto.TechStore.dto.MessageResponseDTO;
import com.proyecto.TechStore.dto.RefreshTokenResponseDTO;
import com.proyecto.TechStore.dto.RegisterRequestDTO;
import com.proyecto.TechStore.dto.UserResponseDTO;
import com.proyecto.TechStore.entity.Users;
import com.proyecto.TechStore.repository.UsersRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final PasswordEncoder passwordEncoder;

    private final UsersRepository usersRepository;

    private final JwtService jwtService;

    public MessageResponseDTO register(RegisterRequestDTO request) {
        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Registro exitoso");

        if (usersRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("Este nombre de usuario ya está en uso");
        }

        Users user = new Users();
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        usersRepository.save(user);

        return response;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        LoginResponseDTO response = new LoginResponseDTO();
        Optional<Users> user = usersRepository.findByName(request.getName());

        if (user.isEmpty() && request.getName() != null) {
            response.setMessage("Este usuario no se encuentra registrado");
            return response;
        }

        Users userFound = user.get();

        if (!passwordEncoder.matches(request.getPassword(), userFound.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String jwt = jwtService.generateToken(userFound.getId(), userFound.getName(), userFound.getRole());

        response.setMessage("Inicio de sesión exitoso");
        response.setJwt(jwt);
        return response;
    }

    public List<UserResponseDTO> getAllUsers() {
        return usersRepository.findAll()
                .stream() 
                .map(user -> {
                    UserResponseDTO dto = new UserResponseDTO();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    dto.setRole(user.getRole());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * este metodo es para refrescar el token, se le pasa el token actual y se devuelve un nuevo token con una nueva fecha de expiracion
     * @param token el token actual que se quiere refrescar
     * @return un nuevo token con una nueva fecha de expiracion
     */
    public RefreshTokenResponseDTO refreshToken(String token) {
        String jwt = jwtService.refreshToken(token);
        RefreshTokenResponseDTO response = new RefreshTokenResponseDTO();
        response.setJwt(jwt);
        return response;
    } 
}
