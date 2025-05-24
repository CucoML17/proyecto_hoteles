package edu.listasproductost2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.listasproductost2.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
