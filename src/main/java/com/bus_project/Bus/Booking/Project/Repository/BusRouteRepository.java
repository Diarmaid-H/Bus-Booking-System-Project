package com.bus_project.Bus.Booking.Project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bus_project.Bus.Booking.Project.Model.BusRoute;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute, Long> {

}
