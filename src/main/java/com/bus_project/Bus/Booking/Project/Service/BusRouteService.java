package com.bus_project.Bus.Booking.Project.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bus_project.Bus.Booking.Project.Model.BusRoute;
import com.bus_project.Bus.Booking.Project.Repository.BusRouteRepository;

@Service
public class BusRouteService {

    private final BusRouteRepository busRouteRepository;

    public BusRouteService(BusRouteRepository busRouteRepository) {
        this.busRouteRepository = busRouteRepository;
    }

    public List<BusRoute> getAllRoutes() {
        return busRouteRepository.findAll();
    }

    public BusRoute getRouteById(Long id) {
        return busRouteRepository.findById(id).
                                    orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus route not found"));
    }

    public BusRoute createRoute(BusRoute busRoute) {
        return busRouteRepository.save(busRoute);
    }

    public BusRoute updateRoute(Long id, BusRoute updatedRoute) {
        return busRouteRepository.findById(id)
                .map(route -> {
                    route.setName(updatedRoute.getName());
                    return busRouteRepository.save(route);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus route not found"));
    }

    public void deleteRoute(Long id) {
        if(!busRouteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus route not found");
        }

        busRouteRepository.deleteById(id);
    }

}
