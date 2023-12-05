package server.controller;

import server.dtobject.segment.*;
import server.dtobject.user.CreateUser;
import server.dtobject.user.DeleteUser;
import server.dtobject.user.UpdateUser;
import server.dtobject.segment.SegmentDTO;
import server.entity.Segment;
import server.entity.User;
import server.exceptions.LastAdminException;
import server.exceptions.ResourceNotFoundException;
import server.exceptions.ServerResponseException;
import server.exceptions.UnauthorizedAccessException;
import server.repository.SegmentRepository;

import java.util.List;

public class SegmentController {
    private static SegmentController instance = null;

    private final SegmentRepository repository = new SegmentRepository();

    private SegmentController(){
    }

    public static SegmentController getInstance(){
        if(instance == null){
            instance = new SegmentController();
        }
        return instance;
    }


    public List<SegmentDTO> findSegments(){
        return repository.findAll()
                .stream()
                .map(SegmentDTO::of)
                .toList();
    }

    public SegmentDTO findSegment(Long pdi_inicial, Long pdi_final) throws ResourceNotFoundException {
        var entity = repository.find(pdi_inicial, pdi_final)
                .orElseThrow(() -> new ResourceNotFoundException("Segment"));

        return SegmentDTO.of(entity);
    }

    public SegmentDTO createSegment(CreateSegment segment) throws ServerResponseException {
        var entity = Segment.of(segment);
        repository.create(entity);
        return SegmentDTO.of(entity);
    }

    public SegmentDTO updateSegment(UpdateSegment segment) throws ServerResponseException {
        var entity = repository.update(segment.id(), Segment.of(segment));
        return SegmentDTO.of(entity);
    }

    public void deleteSegment(DeleteSegment segmentToDelete) throws ResourceNotFoundException, LastAdminException {
        repository.deleteById(segmentToDelete.idToDelete());
    }

    public void deleteLinkedSegments(Long pdi){
        repository.deleteByPDI(pdi);
    }
}
