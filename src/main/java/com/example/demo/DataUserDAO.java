package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DataUserDAO extends JpaRepository<DataUser,Long> {
    @Query(value="SELECT count(*) FROM DATA_USER WHERE USERNAME=?1",nativeQuery = true)
    int countuser(String s);
    DataUser findByusername(String username);

}
