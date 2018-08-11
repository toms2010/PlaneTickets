package pl.toms.planeTickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import pl.toms.planeTickets.entity.BaseEntity;

@NoRepositoryBean
public interface BaseRepository <T extends BaseEntity, ID>  extends JpaRepository<T, ID> {
    T findOneById(Integer id);
}
