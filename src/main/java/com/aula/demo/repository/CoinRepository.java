package com.aula.demo.repository;

import java.util.List;
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

	private EntityManager entityManager;
	
	public CoinRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	@Transactional //Anotacao resposaval por pegar uma transação e apos persistir os dados fechar a transação
	public Coin insert(Coin coin) {
		//persistir os dados
		entityManager.persist(coin);
		return coin;
	}
	
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
	
	/*
	public List<Coin> getByName(String name){
		Object[] attr = new Object[] {name}; 
		return jdbcTemplate.query(SELCT_BY_NAME, new RowMapper<Coin>(){
			@Override
			public Coin mapRow (ResultSet rs, int rowNum) throws SQLException{
				Coin coin = new Coin();
				coin.setId(rs.getInt("id"));
				coin.setName(rs.getString("name"));
				coin.setPrice(rs.getBigDecimal("price"));
				coin.setQuantity(rs.getBigDecimal("quantity"));
				coin.setDateTime(rs.getTimestamp("datetime"));
				
				return coin;
			}
		}, attr);
	}
	
	public int remove(int id) {
		return jdbcTemplate.update(DELETE, id);
	}*/
}
