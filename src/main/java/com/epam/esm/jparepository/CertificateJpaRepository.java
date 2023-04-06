//package com.epam.esm.jparepository;
//
//import com.epam.esm.entity.Certificate;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface CertificateJpaRepository extends JpaRepository<Certificate, Long> {
//
//    @Query("select c from Certificate c where c.name = ?1")
//    Certificate findByName(String name);
//
//    @Query("select c from Certificate c where c.id = ?1")
//    Certificate findById(long id);
//
//    @Query("select c from Certificate c where c.name like ?1")
//    List<Certificate> findAllByNameLike(String name);
//
//    @Query("select c from Certificate c where c.description like ?1")
//    List<Certificate> findAllByDescriptionLike(String description);
//
//    @Query("select c from Certificate c order by c.createDate DESC, c.name DESC")
//    List<Certificate> findAllByOrderByCreateDateDescNameDesc();
//
//    @Query("select c from Certificate c order by c.createDate, c.name")
//    List<Certificate> findAllByOrderByCreateDateAscNameAsc();
//
//    @Query("select c from Certificate c order by c.createDate DESC, c.name")
//    List<Certificate> findAllByOrderByCreateDateDescNameAsc();
//
//    @Query("select c from Certificate c order by c.createDate, c.name DESC")
//    List<Certificate> findAllByOrderByCreateDateAscNameDesc();
//}
