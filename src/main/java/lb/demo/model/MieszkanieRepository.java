package lb.demo.model;

import lb.demo.model.Mieszkanie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MieszkanieRepository extends JpaRepository<Mieszkanie,Long> {

}
