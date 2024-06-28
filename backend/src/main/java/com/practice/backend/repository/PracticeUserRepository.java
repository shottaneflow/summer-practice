package com.practice.backend.repository;


import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.practice.backend.entity.PracticeUser;
import jakarta.transaction.Transactional;

public interface PracticeUserRepository extends CrudRepository<PracticeUser,Integer> {
		
	Optional<PracticeUser> findByUsername(String username);
	@Query("SELECT EXISTS(SELECT id FROM t_deactivated_token WHERE id = :tokenId)")
    boolean isTokenDeactivated(@Param("tokenId") UUID tokenId);
	
	@Modifying
    @Transactional
    @Query(value = "INSERT INTO t_deactivated_token (id, c_keep_util) VALUES (:tokenId, :expiresAt)", nativeQuery = true)
    void deactivateToken(@Param("tokenId") UUID tokenId, @Param("expiresAt") Date expiresAt);

}
