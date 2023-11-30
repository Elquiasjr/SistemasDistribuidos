package server.controller;

import jwt.JwtHelper;
import protocol.request.user.LoginRequest;
import server.dtobject.CreateUser;
import server.dtobject.UserDTO;
import server.dtobject.DeleteUser;
import server.dtobject.UpdateUser;
import server.entity.User;
import server.exceptions.LastAdminException;
import server.exceptions.ResourceNotFoundException;
import server.exceptions.ServerResponseException;
import server.exceptions.UnauthorizedAccessException;
import server.repository.UserRepository;

import java.util.List;


public class UserController {
    private static UserController instance = null;

    private final UserRepository repository = new UserRepository();
    private UserController(){
    }

    public static UserController getInstance(){
        if(instance == null){
            instance = new UserController();
        }
        return instance;
    }

    public String login(LoginRequest.Payload login) throws UnauthorizedAccessException {
        var user = repository.login(login.email()).orElseThrow(UnauthorizedAccessException::new);

        if(!user.getSenha().equals(login.senha())){
            throw new UnauthorizedAccessException();
        }

        return JwtHelper.createJWT(user.getTipo(), user.getRegistro());
    }

    public Boolean verifyIdExists(Long registro){
        return repository.userRegistred(registro);}

    public void verifyUniqueAdmin() throws LastAdminException {
        if(repository.uniqueAdmin()){
            throw new LastAdminException();
        }
    }
    public List<UserDTO> findUsers(){
        return repository.findAll()
                .stream()
                .map(UserDTO::of)
                .toList();
    }

    public UserDTO findUser(long id) throws ResourceNotFoundException{
        var entity = repository.find(id)
                .orElseThrow(() -> new ResourceNotFoundException("User"));

        return UserDTO.of(entity);
    }


    public UserDTO createUser(CreateUser user) throws ServerResponseException {
        var entity = User.of(user);
        repository.create(entity);
        return UserDTO.of(entity);
    }

    public UserDTO updateUser(UpdateUser user) throws ServerResponseException {
        var entity = repository.update(user.registro(), User.of(user));
        return UserDTO.of(entity);
    }

    public void deleteUserPreparation(DeleteUser deleteUser, String email, String senha) throws UnauthorizedAccessException, ResourceNotFoundException, LastAdminException {
        var userFromBD = findUser(deleteUser.registroToDelete());
        var user = repository.login(email).orElseThrow(UnauthorizedAccessException::new);
        if(userFromBD.email().equals(email) && user.getSenha().equals(senha)){
            deleteUser(deleteUser);
        }
    }

    public void deleteUser(DeleteUser userToDelete) throws ResourceNotFoundException, LastAdminException {
        if (userToDelete.isAdmin() && userToDelete.registroToVerify().equals(userToDelete.registroToDelete())){
            if(!repository.tryDelete(userToDelete.registroToDelete())){
                throw new LastAdminException();
            }
        }else{
            repository.deleteById(userToDelete.registroToDelete());
        }
    }
}
