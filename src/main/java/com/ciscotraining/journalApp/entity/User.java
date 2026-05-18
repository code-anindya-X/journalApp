package com.ciscotraining.journalApp.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String userName;
    private String email;
    private boolean sentimentAnalysis;
    private String password;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles;

    public User() {
    }

    public User(ObjectId id, String userName, String email, boolean sentimentAnalysis, String password,
                List<JournalEntry> journalEntries, List<String> roles) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.sentimentAnalysis = sentimentAnalysis;
        this.password = password;
        this.journalEntries = journalEntries;
        this.roles = roles;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSentimentAnalysis() {
        return sentimentAnalysis;
    }

    public void setSentimentAnalysis(boolean sentimentAnalysis) {
        this.sentimentAnalysis = sentimentAnalysis;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<JournalEntry> getJournalEntries() {
        return journalEntries;
    }

    public void setJournalEntries(List<JournalEntry> journalEntries) {
        this.journalEntries = journalEntries;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public static class UserBuilder {
        private ObjectId id;
        private String userName;
        private String email;
        private boolean sentimentAnalysis;
        private String password;
        private List<JournalEntry> journalEntries = new ArrayList<>();
        private List<String> roles;

        public UserBuilder id(ObjectId id) {
            this.id = id;
            return this;
        }

        public UserBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder sentimentAnalysis(boolean sentimentAnalysis) {
            this.sentimentAnalysis = sentimentAnalysis;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder journalEntries(List<JournalEntry> journalEntries) {
            this.journalEntries = journalEntries;
            return this;
        }

        public UserBuilder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public User build() {
            return new User(id, userName, email, sentimentAnalysis, password, journalEntries, roles);
        }
    }

}
