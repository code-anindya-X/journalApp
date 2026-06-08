package com.ciscotraining.journalApp.scheduler;

import com.ciscotraining.journalApp.cache.AppCache;
import com.ciscotraining.journalApp.entity.JournalEntry;
import com.ciscotraining.journalApp.entity.User;
import com.ciscotraining.journalApp.repository.UserRepositoryImpl;
import com.ciscotraining.journalApp.service.EmailService;
import com.ciscotraining.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache  appCache;

    @Scheduled(cron = "0 9 * * SUN")
    public void fetchUsersAndSendSaMail(){
        List<User> users = userRepository.getUsersForSA();
        for(User user:users){
            List<JournalEntry> journalEntries= user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getContent()).collect(Collectors.toList());
            String entry = String.join(", ", filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);

            emailService.sendEmail(user.getEmail(), "Your Weekly Sentiment Analysis", "Based on your journal entries from the past week, your overall sentiment is: " + sentiment);


        }

    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
