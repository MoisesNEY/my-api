package my_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Date;

import my_api.models.User;

import my_api.repositories.userRepository;

@RestController
@RequestMapping("/users")
public class userController {

    @Autowired
    private userRepository userRepository;


    // Endpoint para obtener los usuarios
    @GetMapping
    public List<User> getUsers()
    {
        return userRepository.findAll();
    }

    // Endpoint para crear usuario
    @PostMapping
    public User postUser(@RequestBody User user)
    {
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    // Endpoint para buscar usuarios por id

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id)
    {
        return userRepository.findById(id);
    }

    // Endpoint para actualizar usuario
    @PutMapping("/{id}")
    public User putUser(@PathVariable Long id, @RequestBody User userBody)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userBody.getUsername());
        user.setEmail(userBody.getEmail());
        user.setPassword(userBody.getPassword());
        user.setFirstname(userBody.getFirstname());
        user.setLastname(userBody.getLastname());
        user.setRoles(userBody.getRoles());
        return userRepository.save(user);
    }

    // Endpoint para eliminar usuario por id
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id)
    {
        userRepository.deleteById(id);
    }


}
