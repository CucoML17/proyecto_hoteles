package edu.listasproductost2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.listasproductost2.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}

