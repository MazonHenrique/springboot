package com.aula.demo.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping()
	public ResponseEntity post(@RequestBody Coin coin) {
		try {
			coin.setDateTime(new Timestamp(System.currentTimeMillis()));
			return new ResponseEntity<>(coinRepository.insert(coin), HttpStatus.CREATED);
		}catch(Exception error ){
			return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
