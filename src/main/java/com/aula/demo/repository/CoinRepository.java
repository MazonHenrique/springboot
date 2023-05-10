package com.aula.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.aula.demo.entity.Coin;

import dto.CoinDTO;

@Repository
@EnableAutoConfiguration
public class CoinRepository {
	
	private static String INSERT = "insert into coin(name, price, quantity, datetime) values(?,?,?,?)";
	
	private static String SELECT_ALL = "select name, sum(quantity) as quantity from coin group by name";
	
	private static String SELCT_BY_NAME = "select * from coin where name = ?";
	
	private static String DELETE = "delete from coin where id = ?";
	
	private JdbcTemplate jdbcTemplate;
	
	public CoinRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Coin insert(Coin coin) {
		Object[] attr = new Object[] {
				coin.getName(),
				coin.getPrice(),
				coin.getQuantity(),
				coin.getDateTime()
		};
		jdbcTemplate.update(INSERT, attr);
		return coin;
	}
	
	public List<CoinDTO> getALL(){
		return jdbcTemplate.query(SELECT_ALL, new RowMapper<CoinDTO>(){
			@Override
			public CoinDTO mapRow (ResultSet rs, int rowNum) throws SQLException{
				CoinDTO coin = new CoinDTO();
				coin.setName(rs.getString("name"));
				coin.setQuantity(rs.getBigDecimal("quantity"));
				return coin;
			}
		});
	}
	
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
	}
}
