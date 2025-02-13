package com.bus_project.Bus.Booking.Project.Controller;

import java.util.List;

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

import com.bus_project.Bus.Booking.Project.Model.BusRoute;
import com.bus_project.Bus.Booking.Project.Service.BusRouteService;

@RestController
@RequestMapping("/api/bus")
public class BusRouteController {

    private final BusRouteService busRouteService;

    public BusRouteController(BusRouteService busRouteService) {
        this.busRouteService = busRouteService;
    }

    @GetMapping("/routes")
    public ResponseEntity<List<BusRoute>> getAllRoutes() {
        return ResponseEntity.ok(busRouteService.getAllRoutes());
    }

    @GetMapping("/routes/{id}")
    public ResponseEntity<BusRoute> getRouteById(@PathVariable Long id) {
        BusRoute busRoute = busRouteService.getRouteById(id);
        return ResponseEntity.ok(busRoute);
    }

    @PostMapping("/routes")
    public ResponseEntity<BusRoute> createRoute(@RequestBody BusRoute busRoute) {
        BusRoute createdRoute = busRouteService.createRoute(busRoute);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoute);
    }

    @PutMapping("/routes/{id}")
    public ResponseEntity<BusRoute> updateRoute(@PathVariable Long id, @RequestBody BusRoute updatedRoute) {
        BusRoute route = busRouteService.updateRoute(id, updatedRoute);
        return ResponseEntity.ok(route);
    }

    @DeleteMapping("/routes/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        busRouteService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }

}
