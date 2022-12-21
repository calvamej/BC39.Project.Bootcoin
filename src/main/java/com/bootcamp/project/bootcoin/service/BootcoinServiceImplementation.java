package com.bootcamp.project.bootcoin.service;

import com.bootcamp.project.bootcoin.entity.BootcoinEntity;
import com.bootcamp.project.bootcoin.entity.BootcoinOperationDTO;
import com.bootcamp.project.bootcoin.exception.CustomNotFoundException;
import com.bootcamp.project.bootcoin.repository.BootcoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class BootcoinServiceImplementation implements  BootcoinService{

    public static final String topic = "mybootcointopic";

    @Autowired
    private KafkaTemplate<String, BootcoinOperationDTO> kafkaTemp;

    @Autowired
    private BootcoinRepository bootcoinRepository;

    @Override
    public Flux<BootcoinEntity> getAll() {
        return bootcoinRepository.findAll().switchIfEmpty(Mono.error(new CustomNotFoundException("Bootcoins not found")));
    }
    @Override
    public Mono<BootcoinEntity> getOne(String documentNumber) {
        return bootcoinRepository.findAll().filter(x -> x.getDocumentNumber() != null && x.getDocumentNumber().equals(documentNumber)).next();
    }

    @Override
    public Mono<BootcoinEntity> save(BootcoinEntity colEnt) {
        colEnt.setCreateDate(new Date());
        return bootcoinRepository.save(colEnt);
    }

    @Override
    public Mono<BootcoinEntity> update(String documentNumber, String email) {
        return getOne(documentNumber).flatMap(c -> {
            c.setEmail(email);
            c.setModifyDate(new Date());
            return bootcoinRepository.save(c);
        }).switchIfEmpty(Mono.error(new CustomNotFoundException("Bootcoin not found")));
    }

    @Override
    public Mono<Void> delete(String documentNumber) {
        return getOne(documentNumber)
                .switchIfEmpty(Mono.error(new CustomNotFoundException("Bootcoin not found")))
                .flatMap(c -> {
                    return bootcoinRepository.delete(c);
                });
    }
    @Override
    public Mono<BootcoinEntity> register(BootcoinEntity colEnt)
    {
        if(colEnt.getDocumentNumber() == null || colEnt.getMobileNumber() == null  || colEnt.getEmail() == null)
        {
            return Mono.error(new CustomNotFoundException("The following fields are mandatory: Document Number, Mobile Number and Email."));
        }
        colEnt.setCreateDate(new Date());
        return bootcoinRepository.findAll().filter(x -> x.getDocumentNumber() != null && x.getDocumentNumber().equals(colEnt.getDocumentNumber())
                && x.getMobileNumber() != null && x.getMobileNumber().equals(colEnt.getMobileNumber())).next()
                .switchIfEmpty(bootcoinRepository.save(colEnt));
    }
    @Override
    public Mono<BootcoinOperationDTO> requestPurchase(String documentNumber, String paymentMethod, double amount)
    {
        return getOne(documentNumber).flatMap(c -> {
            BootcoinOperationDTO dto = new BootcoinOperationDTO(c.getDocumentNumber(),paymentMethod, c.getMobileNumber(), c.getAccountNumber(),null,amount,"REQUESTED");
            publishToTopic(dto);
            return Mono.just(dto);
        }).switchIfEmpty(Mono.error(new CustomNotFoundException("The client does not have a Bootcoin wallet registered.")));
    }
    @Override
    public void publishToTopic(BootcoinOperationDTO entity) {
        this.kafkaTemp.send(topic, entity);
    }
}
