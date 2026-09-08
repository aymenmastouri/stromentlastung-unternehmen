package de.stromentlastung.unternehmen.register;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ZuordnungRepository extends JpaRepository<Zuordnung, Long> {

    Optional<Zuordnung> findByUnternehmenKennungAndEntnahmejahr(String unternehmenKennung, int entnahmejahr);

    List<Zuordnung> findByUnternehmenKennungOrderByEntnahmejahrDesc(String unternehmenKennung);

    @Query("""
            select z from Zuordnung z, Unternehmen u
            where u.kennung = z.unternehmenKennung and u.hauptzollamtKennung = :dienststelle and z.zustand = :zustand
            order by z.beantragtAm""")
    List<Zuordnung> findByDienststelleAndZustand(@Param("dienststelle") String dienststelle, @Param("zustand") ZuordnungZustand zustand);
}
