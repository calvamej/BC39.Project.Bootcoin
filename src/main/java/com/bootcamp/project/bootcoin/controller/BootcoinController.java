package com.bootcamp.project.bootcoin.controller;

import com.bootcamp.project.bootcoin.entity.BootcoinEntity;
import com.bootcamp.project.bootcoin.entity.BootcoinOperationDTO;
import com.bootcamp.project.bootcoin.service.BootcoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/Bootcoin")
public class BootcoinController {
    @Autowired
    BootcoinService bootcoinService;

    @GetMapping(value = "/FindOne/{documentNumber}")
    public Mono<BootcoinEntity> Get_One(@PathVariable("documentNumber") String documentNumber){
        return bootcoinService.getOne(documentNumber);
    }
    @GetMapping(value = "/FindAll")
    public Flux<BootcoinEntity> Get_All(){

        return bootcoinService.getAll();
    }
    @PostMapping(value = "/Save")
    public Mono<BootcoinEntity> Save(@RequestBody BootcoinEntity col){

        return bootcoinService.save(col);
    }
    @PutMapping(value = "/Update/{documentNumber}/{email}")
    public Mono<BootcoinEntity> Update(@PathVariable("documentNumber") String documentNumber,@PathVariable("email") String email){
        return bootcoinService.update(documentNumber, email);
    }
    @DeleteMapping  (value = "/Delete/{documentNumber}")
    public Mono<Void> Delete(@PathVariable("documentNumber") String documentNumber){
        return bootcoinService.delete(documentNumber);
    }
    @PostMapping(value = "/Register")
    public Mono<BootcoinEntity> register(@RequestBody BootcoinEntity col){

        return bootcoinService.register(col);
    }
    @GetMapping(value = "/RequestPurchase/{documentNumber}/{paymentMethod}/{amount}")
    public Mono<BootcoinOperationDTO> requestPurchase(@PathVariable("documentNumber") String documentNumber,@PathVariable("paymentMethod") String paymentMethod,@PathVariable("amount") double amount){
        return bootcoinService.requestPurchase(documentNumber,paymentMethod,amount);
    }
}
