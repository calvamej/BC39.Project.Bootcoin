package com.bootcamp.project.bootcoin.service;

import com.bootcamp.project.bootcoin.entity.BootcoinEntity;
import com.bootcamp.project.bootcoin.entity.BootcoinOperationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BootcoinService {
    public Flux<BootcoinEntity> getAll();
    public Mono<BootcoinEntity> getOne(String documentNumber);
    public Mono<BootcoinEntity> save(BootcoinEntity colEnt);
    public Mono<BootcoinEntity> update(String documentNumber, String email);
    public Mono<Void> delete(String documentNumber);
    public Mono<BootcoinEntity> register(BootcoinEntity colEnt);
    public Mono<BootcoinOperationDTO> requestPurchase(String documentNumber, String paymentMethod, double amount);
    public void publishToTopic(BootcoinOperationDTO entity);
}
