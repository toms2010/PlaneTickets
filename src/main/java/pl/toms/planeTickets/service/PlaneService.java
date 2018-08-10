package pl.toms.planeTickets.service;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import pl.toms.planeTickets.controller.FlightController;
import pl.toms.planeTickets.entity.Flight;
import pl.toms.planeTickets.entity.Plane;
import pl.toms.planeTickets.exception.ApplicationException;
import pl.toms.planeTickets.exception.NotFoundException;
import pl.toms.planeTickets.repository.PlaneRepository;

@Service
public class PlaneService {
    protected static final Logger LOGGER = LoggerFactory.getLogger(FlightController.class);

    /**
     * Wiadomość przy błędzie {@link NotFoundException}
     */
    MessageFormat form = new MessageFormat("There is no plane with id: {0}.");

    @Autowired
    private PlaneRepository planeRepository;

    public List<Plane> getPlanes() {
        return (List<Plane>) planeRepository.findAll();
    }

    public Plane getPlane(int planeTypeId) {
        Plane plane = planeRepository.findOneById(planeTypeId);
        if (plane == null) {
            Object[] testArgs = { new Integer(planeTypeId) };
            String info = form.format(testArgs);
            LOGGER.error(info);
            throw new NotFoundException(info);
        }
        return plane;
    }

    public Plane addPlaneType(Plane plane) {
        Plane newPlane = planeRepository.save(plane);
        LOGGER.debug("Created new plane with id: " + newPlane.getId());
        return newPlane;
    }

    public void deletePlane(int planeTypeId) {
        if (planeRepository.findOneById(planeTypeId) == null) {
            Object[] testArgs = { new Integer(planeTypeId) };
            String info = form.format(testArgs);
            LOGGER.error(info);
            throw new NotFoundException(info);
        }
        try {
            planeRepository.deleteById(planeTypeId);
            LOGGER.debug("Deleted plane with id: " + planeTypeId);
        }
        catch (Exception e) {
            List<Flight> flightsList = planeRepository.findOneById(planeTypeId).getFlights();
            if (!flightsList.isEmpty() && flightsList.size()>0) {
                MessageFormat form = new MessageFormat("Can not delete plane type with id: {0}. {1} flights is/are related to this plane type");
                Object[] testArgs = {planeTypeId, flightsList.size()};
                String info = form.format(testArgs);
                LOGGER.error(info);
                throw new ApplicationException(info, HttpStatus.CONFLICT);
            }
            else throw e;
        }
    }
}
