package lb.demo.model;

import lb.demo.model.Mieszkaniec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MieszkaniecRepository extends JpaRepository<Mieszkaniec,Long> {
}
