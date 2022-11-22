package iteam.tn.cinema.repositories;

import iteam.tn.cinema.entities.Cinema;
import iteam.tn.cinema.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
