package com.example.ShadowSocksShare.dao;


import com.example.ShadowSocksShare.entity.ShadowSocksDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 数据库操作
 */
@Repository
public interface ShadowSocksDetailsRepository extends JpaRepository<ShadowSocksDetailsEntity, Long> {

	/*@Query("select h.name as name, avg(r.rating) as averageRating  from Hotel h left outer join h.reviews r  group by h")
	Page<ShadowSocksDetailsEntity> findByCity(Pageable pageable);*/
}
