package my_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;
import java.util.Optional;
import my_api.models.User;

import my_api.repositories.userRepository;

@RestController
@RequestMapping("/users")
public class userController {

    @Autowired
    private userRepository userRepository;


    // Endpoint para obtener los usuarios
    @GetMapping
    public ResponseEntity<?> getUsers()
    {
        if (userRepository.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay usuarios encontrado");
        }
        return ResponseEntity.ok(userRepository.findAll());
    }

    // Endpoint para crear usuario
    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody User user)
    {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El correo ya está registrado");
        }

        user.setCreatedAt(new Date());
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(userRepository.save(user));
    }

    // Endpoint para buscar usuarios por id

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id)
    {
        if (userRepository.findById(id).isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("No se encontro el usuario con el id: " + id);
        }
        return ResponseEntity.ok(userRepository.findById(id));
    }

    // Endpoint para actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> putUser(@PathVariable Long id, @RequestBody User userBody)
    {
        Optional<User> OptionalUser = userRepository.findById(id);

        if (OptionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("No se encontro el usuario con el id: " + id);
        }
        User user = OptionalUser.get();
        user.setUsername(userBody.getUsername());
        user.setEmail(userBody.getEmail());
        user.setPassword(userBody.getPassword());
        user.setFirstname(userBody.getFirstname());
        user.setLastname(userBody.getLastname());
        user.setRoles(userBody.getRoles());
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para eliminar usuario por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id)
    {
        if (userRepository.findById(id).isEmpty()) 
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no existe");    
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
