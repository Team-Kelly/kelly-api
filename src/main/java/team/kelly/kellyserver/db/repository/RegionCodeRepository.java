package team.kelly.kellyserver.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.kelly.kellyserver.db.entity.RegionCode;

public interface RegionCodeRepository extends JpaRepository<RegionCode, Long> {
    RegionCode findByRegionAndCity(String region, String city);
}
