package de.stromentlastung.unternehmen.register;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UnternehmenRepository extends JpaRepository<Unternehmen, Long> {

    Optional<Unternehmen> findByKennung(String kennung);

    List<Unternehmen> findByHauptzollamtKennungOrderByName(String hauptzollamtKennung);
}
