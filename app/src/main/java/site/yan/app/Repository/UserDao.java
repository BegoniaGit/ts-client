package site.yan.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.yan.app.model.UserDO;

@Repository
public interface UserDao extends JpaRepository<UserDO, Long> {

}