//package com.epam.esm.jparepository;
//
//import com.epam.esm.entity.Certificate;
//import com.epam.esm.entity.Tag;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface TagJpaRepository extends JpaRepository<Tag, Long> {
//
//    @Query("select t from Tag t")
//    List<Tag> findAll();
//
//    @Query("select t from Tag t where t.id = ?1")
//    Tag findTagById(long id);
//
////    @Query("select t from Tag t where :certificate in t.certificates")
////    List<Tag> findAllByCertificate(@Param("certificate") Certificate certificate);
//}
