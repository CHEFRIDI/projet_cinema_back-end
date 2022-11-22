package iteam.tn.cinema.service;

import iteam.tn.cinema.entities.*;
import iteam.tn.cinema.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitImpl implements ICinemaInitService{
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private TicketRepository ticketRepository;



    @Override
    public void initVilles() {
        Stream.of("Tunis", "Ariana", "mounestir", "marsa").forEach(nameville->{
            //villeRepository.save(new Ville(null, v));
            Ville ville = new Ville();
            ville.setName(nameville);
            //save villes
            villeRepository.save(ville);
        });

    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(v -> {
            Stream.of("ciné star ", "cinema le palace", "Agora" ,"opera").forEach(nameCinema ->{
                Cinema cinema = new Cinema();
                cinema.setName(nameCinema);
                cinema.setNombreSalles(3 + (int) (Math.random()*7));//nb sale [3 , 10]
                cinema.setVille(v);

                //save cinemas
                cinemaRepository.save(cinema);

            });
        });

    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(cinema -> {
            for (int i=0 ;i < cinema.getNombreSalles(); i++){
                Salle salle =new Salle();
                salle.setName("salle " + (i + 1));
                salle.setCinema(cinema);
                salle.setNombrePlaces(15 + (int) (Math.random() * 20));//[15, 35]

                //save
                salleRepository.save(salle);
            }

        });

    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle ->{
            for (int i = 0; i < salle.getNombrePlaces(); i++) {
                Place place = new Place();
                place.setNumeroPlace(i +1);
                place.setSalle(salle);

                //save
                placeRepository.save(place);
            }
        });
    }

    @Override
    public void initSeances() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00", "15:00", "17:00", "19:00", "21:00").forEach( s -> {
            Seance seance = new Seance();
            try {
                seance.setHeureDebut(dateFormat.parse(s));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //save
            seanceRepository.save(seance);
        });

    }

    @Override
    public void initCategories() {
        Stream.of("actions", "Histoire", "Fiction", "Drama").forEach(cat->{
            Categorie categorie = new Categorie();
            categorie.setName(cat);

            //save
            categorieRepository.save(categorie);
        });

    }

    @Override
    public void initFilms() {
        double[] durees = new double[] {1, 1.5, 2, 2.5, 3};
        List<Categorie> categories = categorieRepository.findAll();
        Stream.of("le seigneur des anneaux", "matrix", "taxi driver", "gladiator", "le parin").forEach(titreF -> {
            Film film = new Film();
            film.setTitre(titreF);
            film.setDuree(durees[new Random().nextInt(durees.length)]);
            film.setPhoto(titreF.replaceAll(" ","")+".jpg");
            film.setCategorie(categories.get(new Random().nextInt(categories.size())));

            //save
            filmRepository.save(film);
        });
    }

    @Override
    public void initProjections() {
        double[] prices = new double[] {30, 50, 70, 90, 120};
        villeRepository.findAll().forEach( ville ->{
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    filmRepository.findAll().forEach(film -> {
                        seanceRepository.findAll().forEach(seance -> {
                            Projection projection = new Projection();
                            projection.setDateProjection(new Date());
                            projection.setFilm(film);
                            projection.setPrix(prices[new Random().nextInt(prices.length)]);
                            projection.setSalle(salle);
                            projection.setSeance(seance);

                            //save
                            projectionRepository.save(projection);
                        });
                    });
                });
            });
        });
    }

    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(p -> {
            p.getSalle().getPlaces().forEach(place -> {
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(p.getPrix());
                ticket.setProjection(p);
                ticket.setReserve(false);

                //save
                ticketRepository.save(ticket);
            });
        });
    }
}
