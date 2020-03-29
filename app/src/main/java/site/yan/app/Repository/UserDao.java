package site.yan.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.yan.app.model.UserDO;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<UserDO, Long> {

    @Query(value = "select h from user limit 1",nativeQuery = true)
    String triException();

}
