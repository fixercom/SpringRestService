package com.example.repository;

import com.example.entity.PublishingHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("publishingHouseRepository")
public interface PublishingHouseRepository extends JpaRepository<PublishingHouse, Long> {
}
