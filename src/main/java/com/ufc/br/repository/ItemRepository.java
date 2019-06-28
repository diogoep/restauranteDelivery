package com.ufc.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ufc.br.model.Item;

public  interface ItemRepository extends JpaRepository<Item, Long>{
}
