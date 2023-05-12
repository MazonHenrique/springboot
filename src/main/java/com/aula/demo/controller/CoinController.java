package com.aula.demo.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aula.demo.entity.Coin;
import com.aula.demo.repository.CoinRepository;

@RestController
@RequestMapping("/coin")
public class CoinController {

	@Autowired
	private CoinRepository coinRepository;
	
	@Bean
	public Coin init() {
		Coin c1 = new Coin();
		c1.setName("BITCOIN");
		c1.setPrice(new BigDecimal(100));
		c1.setQuantity(new BigDecimal(0.004));
		c1.setDateTime(new Timestamp(System.currentTimeMillis()));
		
		Coin c2 = new Coin();
		c2.setName("BITCOIN");
		c2.setPrice(new BigDecimal(150));
		c2.setQuantity(new BigDecimal(0.008));
		c2.setDateTime(new Timestamp(System.currentTimeMillis()));
		
		Coin c3 = new Coin();
		c3.setName("ETHEREUM");
		c3.setPrice(new BigDecimal(90));
		c3.setQuantity(new BigDecimal(0.009));
		c3.setDateTime(new Timestamp(System.currentTimeMillis()));
		
		Coin c4 = new Coin();
		c4.setName("ETHEREUM");
		c4.setPrice(new BigDecimal(100));
		c4.setQuantity(new BigDecimal(0.012));
		c4.setDateTime(new Timestamp(System.currentTimeMillis()));
		
		coinRepository.insert(c1);
		coinRepository.insert(c2);
		coinRepository.insert(c3);
		coinRepository.insert(c4);
		return c1; 
	}
	
	@GetMapping
	public ResponseEntity get() {
		return new ResponseEntity<>(coinRepository.getALL(), HttpStatus.OK);
	}
	
	@GetMapping("/{name}")
	public ResponseEntity get(@PathVariable String name) {
		try {
			return new ResponseEntity<>(coinRepository.getByName(name), HttpStatus.OK);
		}catch (Exception error) {
			return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping()
	public ResponseEntity post(@RequestBody Coin coin) {
		try {
			coin.setDateTime(new Timestamp(System.currentTimeMillis()));
			return new ResponseEntity<>(coinRepository.insert(coin), HttpStatus.CREATED);
		}catch(Exception error ){
			return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@PutMapping()
//	public ResponseEntity put(@RequestBody Coin coin) {
//		try {
//			coin.setDateTime(new Timestamp(System.currentTimeMillis()));
//			return new ResponseEntity<>(coinRepository.update(coin), HttpStatus.OK);
//		}catch(Exception error ){
//			return new ResponseEntity<>(error.getMessage(), HttpStatus.NO_CONTENT);
//		}
//	}
	
    @PutMapping()
    public ResponseEntity put(@RequestBody Coin coin){
        try{
            coin.setDateTime(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(coinRepository.update(coin), HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.NO_CONTENT);
        }
    }
    
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable int id) {
		try {
			return new ResponseEntity<>(coinRepository.remove(id), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
}
