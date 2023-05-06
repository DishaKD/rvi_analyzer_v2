package com.rvi.analyzer.rvianalyzerserver.repository;

import com.rvi.analyzer.rvianalyzerserver.entiy.Device;
import com.rvi.analyzer.rvianalyzerserver.entiy.User;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeviceRepository extends ReactiveMongoRepository<Device, String> {

    @Query(
            value = """
    {
        "mac-address" : {
            "$eq" : ?0
        }
    }
    """
    )
    Mono<Device> findByMacAddress(String mac);


    @Aggregation("{'status': ?1, 'name': {$regex: .*?0.*}, 'created-by': ?3}, {limit: 20, skip: ?2}")
    Flux<Device> findDevicesByNameStatusPageUserName(String name, String status, String page, String username);
}
