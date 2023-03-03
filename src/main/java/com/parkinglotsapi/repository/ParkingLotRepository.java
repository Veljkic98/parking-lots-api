package com.parkinglotsapi.repository;

import com.parkinglotsapi.domain.model.ParkingLot;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingLotRepository extends ElasticsearchRepository<ParkingLot, Integer> {

    List<ParkingLot> findAll();

    Optional<ParkingLot> searchTop1By(Sort sort);

    @Query("""
        {
            "bool": {
                "must": {
                    "match_all": {}
                },
                "filter": {
                    "geo_distance": {
                        "distance": "?2km",
                        "location": {
                            "lat": ?0,
                            "lon": ?1
                        }
                    }
                }
            }
        }
    """)
    List<ParkingLot> searchWithinRadius(double latitude, double longitude, int distance);

}
