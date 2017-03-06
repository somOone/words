package com.banerjee.spellingbee.service;

import com.banerjee.spellingbee.dto.WordDTO;
import com.banerjee.spellingbee.util.DocumentUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.BSON;
import org.bson.BsonType;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by somobanerjee on 3/4/17.
 */
@Service
public class WordReaderService {
    @Autowired
    MongoDatabase mongoDatabase;

    public WordDTO getNextWord() throws Exception {
        Double rand = Math.random();
        MongoCollection<Document> wordCollection = mongoDatabase.getCollection("word");
        //db.getCollection('word').aggregate([{ $match : {"entry_list.entry": { "$ne": null }}}, {$sample: { size: 1 }}])
        //List<Document> rvs = wordCollection.find(Filters.ne("entry_list.entry", null))
            //.into(new ArrayList<Document>());
        List<Document> rvs = wordCollection.find(Filters.ne("entry_list.entry", null))
            .into(new ArrayList<Document>());
        int randIndex = (int)(Math.random() * (rvs.size() - 1));
        Document wordDoc = rvs.get(randIndex);
        WordDTO wordDTO = generateWord(wordDoc);
        return wordDTO;
    }

    private WordDTO generateWord(Document wordDoc) throws Exception {
        WordDTO retval = new WordDTO();
        Document entryList = (Document) wordDoc.get("entry_list");
        Document entry = null;
        try {
            ArrayList<Document> entries = (ArrayList<Document>) entryList.get("entry");
            entry = entries.get(0);
        } catch (Exception e) {
            entry = (Document) entryList.get("entry");
        }
        String value = (String) entry.get("id");
        MongoCollection<Document> cleanedWordCollection = mongoDatabase.getCollection("wordDTO");
        List<Document>cleanedWords = cleanedWordCollection.find(Filters.eq("value", value))
            .into(new ArrayList<Document>());
        if(cleanedWords != null && !cleanedWords.isEmpty() && cleanedWords.size() == 1) {
            return prepareCleanedWord(cleanedWords.get(0));
        } else {
            deleteDups(cleanedWords);
        }
        retval.setValue((String) entry.get("id"));
        retval.setType((String) entry.get("fl"));
        Document sound = (Document) entry.get("sound");
        String audio = (String) sound.get("wav");
        retval.setAudioUrl("http://media.merriam-webster.com/soundc11/" + audio.substring(0, 1) + "/" + audio);
        try {
            saveCleanedWord(retval);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retval;
    }

    private void deleteDups(List<Document> cleanedWords) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("wordDTO");
        for(Document cleanedWord : cleanedWords) {
            collection.deleteOne(cleanedWord);
        }
    }

    private WordDTO prepareCleanedWord(Document wordDTODoc) throws Exception {
        return DocumentUtil.convertDocumentToPojo(wordDTODoc);
    }

    private void saveCleanedWord(WordDTO wordDTO) throws Exception {
        MongoCollection<Document> collection = mongoDatabase.getCollection("wordDTO");
        collection.insertOne(DocumentUtil.convertPojoToDocument(wordDTO));
    }
}
