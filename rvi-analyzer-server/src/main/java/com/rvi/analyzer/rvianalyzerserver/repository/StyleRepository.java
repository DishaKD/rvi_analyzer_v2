//package com.rvi.analyzer.rvianalyzerserver.repository;
//
//import com.rvi.analyzer.rvianalyzerserver.entiy.Style;
//import com.rvi.analyzer.rvianalyzerserver.entiy.User;
// // import org.springframework.data.mongodb.repository.Query;
// // import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//public interface StyleRepository extends ReactiveMongoRepository<Style, String> {
//
//    @Query(
//            value = """
//                    {
//                        "name" : {
//                            $eq: ?0
//                        }
//                    }
//                    """
//    )
//    Mono<Style> findByName(String name);
//
////    @Query(
////            value = """
////    {
////        "username" : {
////            $regex: .*?0.*
////        }
////    }
////    """
////    )
////    Flux<User> findByUserNamePattern(String pattern);
//
//    @Query(
//            value = """
//                    {
//                        "created-by" : {
//                            $eq: ?0
//                        }
//                    }
//                    """
//    )
//    Flux<Style> findByCreatedBy(String createdBy);
//
////    @Query(value = "{ 'created-by': ?0 }", count = true)
////    Mono<Long> countUsersByUsername(String username);
//}
