package com.aula.demo.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
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
		boolean response = false;
		try {
			response = coinRepository.remove(id);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
		}
	}
}
