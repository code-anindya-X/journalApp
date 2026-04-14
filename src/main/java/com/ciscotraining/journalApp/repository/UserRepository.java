package com.ciscotraining.journalApp.repository;

import com.ciscotraining.journalApp.entity.JournalEntry;
import com.ciscotraining.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository <User, ObjectId> {
User findByUserName(String userName);

void deleteByUserName(String userName);

}
