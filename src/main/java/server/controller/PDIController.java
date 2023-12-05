package server.controller;

import server.dtobject.pdi.CreatePDI;
import server.dtobject.pdi.DeletePDI;
import server.dtobject.pdi.PDIDTO;
import server.dtobject.pdi.UpdatePDI;
import server.dtobject.user.CreateUser;
import server.dtobject.user.DeleteUser;
import server.dtobject.user.UpdateUser;
import server.dtobject.user.UserDTO;
import server.entity.PDI;
import server.entity.User;
import server.exceptions.LastAdminException;
import server.exceptions.ResourceNotFoundException;
import server.exceptions.ServerResponseException;
import server.exceptions.UnauthorizedAccessException;
import server.repository.PDIRepository;

import java.util.List;

public class PDIController  {
    private static PDIController instance = null;

    private final PDIRepository repository = new PDIRepository();

    private PDIController(){

    }

    public static PDIController getInstance(){
        if(instance == null){
            instance = new PDIController();
        }
        return instance;
    }

    public List<PDIDTO> findPDIs(){
        return repository.findAll()
                .stream()
                .map(PDIDTO::of)
                .toList();
    }

    public PDIDTO findPDI(long id) throws ResourceNotFoundException {
        var entity = repository.find(id)
                .orElseThrow(() -> new ResourceNotFoundException("User"));

        return PDIDTO.of(entity);
    }

    public PDIDTO createPDI(CreatePDI pdi) throws ServerResponseException {
        var entity = PDI.of(pdi);
        repository.create(entity);
        return PDIDTO.of(entity);
    }

    public PDIDTO updatePDI(UpdatePDI pdi) throws ServerResponseException {
        var entity = repository.update(pdi.id(), PDI.of(pdi));
        return PDIDTO.of(entity);
    }

    public void deletePDI(DeletePDI userToDelete) throws ResourceNotFoundException, LastAdminException {
            repository.deleteById(userToDelete.pdiToDelete());
    }
}
