/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.controllers;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.uv.spendify.dtos.ingresos.IngresoNuevo;
import org.uv.spendify.dtos.ingresos.IngresoRegistrado;
import org.uv.spendify.services.IngresoService;

/**
 *
 * @author juan
 */
@RestController
@CrossOrigin(origins="*", methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/incomes")
public class IngresoController {
    private final IngresoService service;
    
    public IngresoController(IngresoService service){
        this.service=service;
    }
    
    @PostMapping("/saveIncome")
    public ResponseEntity<IngresoRegistrado> saveIncome(@RequestBody IngresoNuevo newIncome){
        IngresoRegistrado registeredIncome=service.saveIncome(newIncome);
        URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(registeredIncome.getId_ingreso()).toUri();
        
        return ResponseEntity.created(ubication).body(registeredIncome);
    }
    
    @PutMapping("/updateIncome/{id}")
    public ResponseEntity<Void> updateIncome(@RequestBody IngresoRegistrado updatedIncome, @PathVariable("id") long id){
        IngresoRegistrado income=service.updateIncome(updatedIncome, id);
        if(income!=null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/deleteIncome/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable("id") long id){
        boolean pase=service.deleteIncome(id);
        if(pase){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/deleteAllIncomesByUser")
    public ResponseEntity<Void> deleteAllIncomesByUser(){
        boolean pase=service.deleteAllIncomesByUser();
        if(pase){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/allIncomesByUser")
    public ResponseEntity<List<IngresoRegistrado>> getAllIncomesByUser(){
        List<IngresoRegistrado> incomes=service.getAllIncomesByUser();
        return ResponseEntity.ok().body(incomes);
    }
    
    @GetMapping("/")
    public ResponseEntity<BigDecimal> sumOfIncomesByUser(){
        BigDecimal incomes=service.sumOfIncomesByUser();
        return ResponseEntity.ok().body(incomes);
    }
    
}
