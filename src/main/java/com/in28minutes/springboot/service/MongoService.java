package com.in28minutes.springboot.service;

import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MongoService {

    private final MongoClient mongoClient;

    public MongoService(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public List<String> listDatabases() {
        List<String> dbNames = new ArrayList<>();
        for (Document db : mongoClient.listDatabases()) {
            dbNames.add(db.getString("name"));
        }
        return dbNames;
    }

    public List<String> listCollections(String databaseName) {
        List<String> collections = new ArrayList<>();
        mongoClient.getDatabase(databaseName)
                .listCollectionNames()
                .forEach(collections::add);
        return collections;
    }

    public List<Map<String, Object>> getDocuments(String databaseName, String collectionName) {
        return getDocuments(databaseName, collectionName, 50);
    }

    public List<Map<String, Object>> getDocuments(String databaseName, String collectionName, int limit) {
        List<Map<String, Object>> results = new ArrayList<>();
        mongoClient.getDatabase(databaseName)
                .getCollection(collectionName)
                .find()
                .limit(limit)
                .forEach(doc -> results.add(documentToMap(doc)));
        return results;
    }

    private Map<String, Object> documentToMap(Document doc) {
        Map<String, Object> map = new java.util.LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : doc.entrySet()) {
            if (entry.getValue() instanceof Document nestedDoc) {
                map.put(entry.getKey(), documentToMap(nestedDoc));
            } else {
                map.put(entry.getKey(), entry.getValue() != null ? entry.getValue().toString() : null);
            }
        }
        return map;
    }
}
