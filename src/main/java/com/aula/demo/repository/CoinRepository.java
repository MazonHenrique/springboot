package com.aula.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.aula.demo.dto.CoinTransactionDTO;
import com.aula.demo.entity.Coin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
@EnableAutoConfiguration
public class CoinRepository {

	@Autowired
	private EntityManager entityManager;

	@Transactional //Anotacao resposaval por pegar uma transação e apos persistir os dados fechar a transação
	public Coin insert(Coin coin) {
		//persistir os dados
		entityManager.persist(coin);
		return coin;
	}
	
	//Usar essa anotation quando tem alteracao
	@Transactional //Anotacao resposaval por pegar uma transação e apos persistir os dados fechar a transação
	public Coin update(Coin coin) {
		entityManager.merge(coin);
		return coin;
	}
	
	
	public List<CoinTransactionDTO> getALL(){
		String jpql = "select new com.aula.demo.dto.CoinTransactionDTO(c.name, sum(c.quantity)) from Coin c group by c.name";
		TypedQuery<CoinTransactionDTO> query =  entityManager.createQuery(jpql, CoinTransactionDTO.class);
		return query.getResultList();
	}
	
	
	public List<Coin> getByName(String name){
		String jpql = "select c from Coin c where c.name like :name";
		TypedQuery<Coin> query = entityManager.createQuery(jpql, Coin.class);
		query.setParameter("name", "%"+name+"%");
		return query.getResultList();
	}
	
	/*
	public int remove(int id) {
		return jdbcTemplate.update(DELETE, id);
	}*/
}
