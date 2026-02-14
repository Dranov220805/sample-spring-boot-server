package com.in28minutes.springboot.controller;

import com.in28minutes.springboot.service.MongoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MongoController {

    private final MongoService mongoService;

    @Value("${spring.data.mongodb.database:test}")
    private String defaultDatabase;

    public MongoController(MongoService mongoService) {
        this.mongoService = mongoService;
    }

    @GetMapping("/databases")
    public List<String> listDatabases() {
        return mongoService.listDatabases();
    }

    @GetMapping("/databases/{databaseName}/collections")
    public List<String> listCollections(@PathVariable String databaseName) {
        return mongoService.listCollections(databaseName);
    }

    @GetMapping("/documents")
    public ResponseEntity<?> getDocuments(
            @RequestParam(required = false) String database,
            @RequestParam String collection,
            @RequestParam(defaultValue = "20") int limit) {
        try {
            String db = (database != null && !database.isBlank()) ? database : defaultDatabase;
            var docs = mongoService.getDocuments(db, collection, Math.min(limit, 200));
            return ResponseEntity.ok(docs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
