package lb.demo.model;

import lb.demo.model.Wspolnota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WspolnotaRepository extends JpaRepository<Wspolnota, Long> {

    @Query("SELECT sum(m.area) from Mieszkanie m " +
            "where m.wspolnota=:wspolnota")
    double getSumArea(@Param("wspolnota") Wspolnota wspolnota);


}
