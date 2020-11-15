package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.dto.ProviderDTO;
import com.integracion.bankapi.service.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//Para prueba de archivo despues se elimina
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/providers")
public class ProviderController {

    private ProviderService service;

    public ProviderController(ProviderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createProvider(@RequestBody ProviderDTO provider){

        ProviderDTO createdProvider = service.create(provider);

        return new ResponseEntity<ProviderDTO>(createdProvider, HttpStatus.CREATED);
    }
/*
    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody ProviderDTO providerEdit){

        ProviderDTO editProvider = service.edit(providerEdit);
        if (editProvider == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(editProvider);
        }
    }
*/
    @GetMapping("/{idProvider}")
    public ResponseEntity<?> getClientById(@PathVariable Integer idProvider){

        ProviderDTO provider = service.getProviderById(idProvider);
        if (provider == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(provider);
        }
    }

    //PARA PRUEBAS
    @DeleteMapping("/{idProvider}")
    public ResponseEntity<?> deleteProvider(@PathVariable Integer idProvider){

        service.delete(idProvider);
        return  new ResponseEntity<>(HttpStatus.OK);

    }


    @PostMapping("/uploadfile")
//    public ResponseEntity<?> uploadFile(@RequestBody ProviderFileDTO providerFile){
        public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("providerCode") String providerFile) {


        //ProviderDTO createdProvider = service.create(provider);

        String a = providerFile;


        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty(providerFile);
                File dir = new File(rootPath + File.separator);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + providerFile+"-disponible.txt");
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                System.out.println("Server File Location=" + serverFile.getAbsolutePath());

                return null;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
        //return new ResponseEntity<ProviderFileDTO>(providerFile, HttpStatus.CREATED);
    }
}