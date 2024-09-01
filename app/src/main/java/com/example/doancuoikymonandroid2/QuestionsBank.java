package com.example.doancuoikymonandroid2;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionsBank {

    private static List<QuestionsList> ietlsQuestions(){

        List<QuestionsList> questionsLists = new ArrayList<>();
        QuestionsList question1 = new QuestionsList("Tara was too _____ to speak when she heard the shocking news.", "ecstatic", "calm", "proud", "stunned", "stunned", "");
        QuestionsList question2 = new QuestionsList("Although the rest of the family had strong opinions about the matter, Maud felt quite _____.", "calm", "relieved", "indifferent", "hysterical", "calm", "");
        QuestionsList question3 = new QuestionsList("Everything had gone exactly as planned, and I felt very _____.", "satisfied", "overwhelmed", "bewildered", "terrified", "satisfied", "");
        QuestionsList question4 = new QuestionsList("Maria was _____ by her husband’s insensitive comments about her weight.", "relaxed", "exhilarated", "anxious", "offended", "offended", "");
        QuestionsList question5 = new QuestionsList("I opened my wife’s diary because I was _____ to find out what she’d written about me.", "calm", "curious", "proud", "furious", "curious", "");
        QuestionsList question6 = new QuestionsList("After purchasing a new phone, my account was ______.", "prodigal", "broke", "in the red", "bankrupt", "in the red", "");
        QuestionsList question7 = new QuestionsList("The ring is absolutely ______! It's not even made of real silver.", "penniless", "worthless", "cheap", "frugal", "worthless", "");
        QuestionsList question8 = new QuestionsList("I can't afford to go out this weekend. I'm completely ______.", "cheap", "prodigal", "broke", "good value", "broke", "");
        QuestionsList question9 = new QuestionsList("He’s obviously _____ her. He talks about her all the time.", "fallen for", "making up with", "dumped", "flirting", "fallen for", "");
        QuestionsList question10 = new QuestionsList("The beach cleanup initiative focused on removing _____, which is harmful to marine life.", "waste management", "pollution control", "plastic waste", "rising sea levels", "plastic waste", "");

        QuestionsList question11 = new QuestionsList("What's the opposite of 'arduous'?", "Difficult", "Tough", "Laborious", "Happy", "Happy", "");
        QuestionsList question12 = new QuestionsList("Choose the word that best relates to 'adroit'", "Clumsy", "Awkward", "Inept", "Skilled", "Clumsy", "");
        QuestionsList question13 = new QuestionsList("Which word is synonymous with 'equivocate'?", "Elaborate", "Mislead", "Clarify", "Obfuscate", "Mislead", "");
        QuestionsList question14 = new QuestionsList("What's the closest meaning to 'prolific'?", "Scarcity", "Abundant", "Inactive", "Unproductive", "Inactive", "");
        QuestionsList question15 = new QuestionsList("What's the opposite of 'gregarious'?", "Sociable", "Outgoing", "Introverted", "Friendly", "Sociable", "");
        QuestionsList question16 = new QuestionsList("Which word is similar to 'ostensible'?", "Evident", "Hidden", "Obvious", "Apparent", "Apparent", "");
        QuestionsList question17 = new QuestionsList("Which word does not match 'acerbic'?", "Biting", "Caustic", "Sharp", "Sweet", "Caustic", "");
        QuestionsList question18 = new QuestionsList("What's the synonym for 'ubiquitous'?", "Rare", "Limited", "Scarce", "Pervasive", "Pervasive", "");
        QuestionsList question19 = new QuestionsList("What word is related to 'juxtapose'?", "Separate", "Contrast", "Compare", "Differentiate", "Separate", "");
        QuestionsList question20 = new QuestionsList("Choose the word that doesn't correspond to 'ubiquitous'", "Everywhere", "Omnipresent", "Limited", "Prevalent", "Omnipresent", "");

        QuestionsList question21 = new QuestionsList("What's the closest synonym to 'laconic'?", "Brief", "Verbose", "Wordy", "Loquacious", "Wordy", "");
        QuestionsList question22 = new QuestionsList("What's the opposite of 'meticulous'?", "Careful", "Precise", "Negligent", "Thorough", "Careful", "");
        QuestionsList question23 = new QuestionsList("What's another word for 'alleviate'?", "Worsen", "Aggravate", "Intensify", "Relieve", "Aggravate", "");
        QuestionsList question24 = new QuestionsList("Which word is synonymous with 'candid'?", "Secretive", "Open", "Sly", "Deceptive", "Open", "");
        QuestionsList question25 = new QuestionsList("Choose the word that doesn't fit with 'concur'", "Agree", "Accord", "Disagree", "Assent", "Disagree", "");
        QuestionsList question26 = new QuestionsList("What's the opposite of 'indolent'?", "Lazy", "Industrious", "Sluggish", "Idle", "Lazy", "");
        QuestionsList question27 = new QuestionsList("What word relates to 'exacerbate'?", "Aggravate", "Improve", "Ameliorate", "Alleviate", "Aggravate", "");
        QuestionsList question28 = new QuestionsList("Which word is similar to 'ephemeral'?", "Lasting", "Transient", "Permanent", "Enduring", "Enduring", "");
        QuestionsList question29 = new QuestionsList("What's another word for 'resilient'?", "Fragile", "Flexible", "Strong", "Vulnerable", "Flexible", "");
        QuestionsList question30 = new QuestionsList("Choose the word that doesn't match 'zeal'", "Enthusiasm", "Passion", "Apathy", "Ardor", "Apathy", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);
        questionsLists.add(question6);
        questionsLists.add(question7);
        questionsLists.add(question8);
        questionsLists.add(question9);
        questionsLists.add(question10);
        questionsLists.add(question11);
        questionsLists.add(question12);
        questionsLists.add(question13);
        questionsLists.add(question14);
        questionsLists.add(question15);
        questionsLists.add(question16);
        questionsLists.add(question17);
        questionsLists.add(question18);
        questionsLists.add(question19);
        questionsLists.add(question20);
        questionsLists.add(question21);
        questionsLists.add(question22);
        questionsLists.add(question23);
        questionsLists.add(question24);
        questionsLists.add(question25);
        questionsLists.add(question26);
        questionsLists.add(question27);
        questionsLists.add(question28);
        questionsLists.add(question29);
        questionsLists.add(question30);

        return  questionsLists;
    }

    private static List<QuestionsList> toeicQuestions(){
        List<QuestionsList> questionsLists = new ArrayList<>();
        QuestionsList question1 = new QuestionsList("Tara was too _____ to speak when she heard the shocking news.", "ecstatic", "calm", "proud", "stunned", "stunned", "");
        QuestionsList question2 = new QuestionsList("Although the rest of the family had strong opinions about the matter, Maud felt quite _____.", "calm", "relieved", "indifferent", "hysterical", "calm", "");
        QuestionsList question3 = new QuestionsList("Everything had gone exactly as planned, and I felt very _____.", "satisfied", "overwhelmed", "bewildered", "terrified", "satisfied", "");
        QuestionsList question4 = new QuestionsList("Maria was _____ by her husband’s insensitive comments about her weight.", "relaxed", "exhilarated", "anxious", "offended", "offended", "");
        QuestionsList question5 = new QuestionsList("I opened my wife’s diary because I was _____ to find out what she’d written about me.", "calm", "curious", "proud", "furious", "curious", "");
        QuestionsList question6 = new QuestionsList("After purchasing a new phone, my account was ______.", "prodigal", "broke", "in the red", "bankrupt", "in the red", "");
        QuestionsList question7 = new QuestionsList("The ring is absolutely ______! It's not even made of real silver.", "penniless", "worthless", "cheap", "frugal", "worthless", "");
        QuestionsList question8 = new QuestionsList("I can't afford to go out this weekend. I'm completely ______.", "cheap", "prodigal", "broke", "good value", "broke", "");
        QuestionsList question9 = new QuestionsList("He’s obviously _____ her. He talks about her all the time.", "fallen for", "making up with", "dumped", "flirting", "fallen for", "");
        QuestionsList question10 = new QuestionsList("The beach cleanup initiative focused on removing _____, which is harmful to marine life.", "waste management", "pollution control", "plastic waste", "rising sea levels", "plastic waste", "");

        QuestionsList question11 = new QuestionsList("What's the opposite of 'arduous'?", "Difficult", "Tough", "Laborious", "Happy", "Happy", "");
        QuestionsList question12 = new QuestionsList("Choose the word that best relates to 'adroit'", "Clumsy", "Awkward", "Inept", "Skilled", "Clumsy", "");
        QuestionsList question13 = new QuestionsList("Which word is synonymous with 'equivocate'?", "Elaborate", "Mislead", "Clarify", "Obfuscate", "Mislead", "");
        QuestionsList question14 = new QuestionsList("What's the closest meaning to 'prolific'?", "Scarcity", "Abundant", "Inactive", "Unproductive", "Inactive", "");
        QuestionsList question15 = new QuestionsList("What's the opposite of 'gregarious'?", "Sociable", "Outgoing", "Introverted", "Friendly", "Sociable", "");
        QuestionsList question16 = new QuestionsList("Which word is similar to 'ostensible'?", "Evident", "Hidden", "Obvious", "Apparent", "Apparent", "");
        QuestionsList question17 = new QuestionsList("Which word does not match 'acerbic'?", "Biting", "Caustic", "Sharp", "Sweet", "Caustic", "");
        QuestionsList question18 = new QuestionsList("What's the synonym for 'ubiquitous'?", "Rare", "Limited", "Scarce", "Pervasive", "Pervasive", "");
        QuestionsList question19 = new QuestionsList("What word is related to 'juxtapose'?", "Separate", "Contrast", "Compare", "Differentiate", "Separate", "");
        QuestionsList question20 = new QuestionsList("Choose the word that doesn't correspond to 'ubiquitous'", "Everywhere", "Omnipresent", "Limited", "Prevalent", "Omnipresent", "");

        QuestionsList question21 = new QuestionsList("What's the closest synonym to 'laconic'?", "Brief", "Verbose", "Wordy", "Loquacious", "Wordy", "");
        QuestionsList question22 = new QuestionsList("What's the opposite of 'meticulous'?", "Careful", "Precise", "Negligent", "Thorough", "Careful", "");
        QuestionsList question23 = new QuestionsList("What's another word for 'alleviate'?", "Worsen", "Aggravate", "Intensify", "Relieve", "Aggravate", "");
        QuestionsList question24 = new QuestionsList("Which word is synonymous with 'candid'?", "Secretive", "Open", "Sly", "Deceptive", "Open", "");
        QuestionsList question25 = new QuestionsList("Choose the word that doesn't fit with 'concur'", "Agree", "Accord", "Disagree", "Assent", "Disagree", "");
        QuestionsList question26 = new QuestionsList("What's the opposite of 'indolent'?", "Lazy", "Industrious", "Sluggish", "Idle", "Lazy", "");
        QuestionsList question27 = new QuestionsList("What word relates to 'exacerbate'?", "Aggravate", "Improve", "Ameliorate", "Alleviate", "Aggravate", "");
        QuestionsList question28 = new QuestionsList("Which word is similar to 'ephemeral'?", "Lasting", "Transient", "Permanent", "Enduring", "Enduring", "");
        QuestionsList question29 = new QuestionsList("What's another word for 'resilient'?", "Fragile", "Flexible", "Strong", "Vulnerable", "Flexible", "");
        QuestionsList question30 = new QuestionsList("Choose the word that doesn't match 'zeal'", "Enthusiasm", "Passion", "Apathy", "Ardor", "Apathy", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);
        questionsLists.add(question6);
        questionsLists.add(question7);
        questionsLists.add(question8);
        questionsLists.add(question9);
        questionsLists.add(question10);
        questionsLists.add(question11);
        questionsLists.add(question12);
        questionsLists.add(question13);
        questionsLists.add(question14);
        questionsLists.add(question15);
        questionsLists.add(question16);
        questionsLists.add(question17);
        questionsLists.add(question18);
        questionsLists.add(question19);
        questionsLists.add(question20);
        questionsLists.add(question21);
        questionsLists.add(question22);
        questionsLists.add(question23);
        questionsLists.add(question24);
        questionsLists.add(question25);
        questionsLists.add(question26);
        questionsLists.add(question27);
        questionsLists.add(question28);
        questionsLists.add(question29);
        questionsLists.add(question30);

        return  questionsLists;
    }

    private static List<QuestionsList> cambridgeQuestions(){

        List<QuestionsList> questionsLists = new ArrayList<>();
        QuestionsList question1 = new QuestionsList("Tara was too _____ to speak when she heard the shocking news.", "ecstatic", "calm", "proud", "stunned", "stunned", "");
        QuestionsList question2 = new QuestionsList("Although the rest of the family had strong opinions about the matter, Maud felt quite _____.", "calm", "relieved", "indifferent", "hysterical", "calm", "");
        QuestionsList question3 = new QuestionsList("Everything had gone exactly as planned, and I felt very _____.", "satisfied", "overwhelmed", "bewildered", "terrified", "satisfied", "");
        QuestionsList question4 = new QuestionsList("Maria was _____ by her husband’s insensitive comments about her weight.", "relaxed", "exhilarated", "anxious", "offended", "offended", "");
        QuestionsList question5 = new QuestionsList("I opened my wife’s diary because I was _____ to find out what she’d written about me.", "calm", "curious", "proud", "furious", "curious", "");
        QuestionsList question6 = new QuestionsList("After purchasing a new phone, my account was ______.", "prodigal", "broke", "in the red", "bankrupt", "in the red", "");
        QuestionsList question7 = new QuestionsList("The ring is absolutely ______! It's not even made of real silver.", "penniless", "worthless", "cheap", "frugal", "worthless", "");
        QuestionsList question8 = new QuestionsList("I can't afford to go out this weekend. I'm completely ______.", "cheap", "prodigal", "broke", "good value", "broke", "");
        QuestionsList question9 = new QuestionsList("He’s obviously _____ her. He talks about her all the time.", "fallen for", "making up with", "dumped", "flirting", "fallen for", "");
        QuestionsList question10 = new QuestionsList("The beach cleanup initiative focused on removing _____, which is harmful to marine life.", "waste management", "pollution control", "plastic waste", "rising sea levels", "plastic waste", "");

        QuestionsList question11 = new QuestionsList("What's the opposite of 'arduous'?", "Difficult", "Tough", "Laborious", "Happy", "Happy", "");
        QuestionsList question12 = new QuestionsList("Choose the word that best relates to 'adroit'", "Clumsy", "Awkward", "Inept", "Skilled", "Clumsy", "");
        QuestionsList question13 = new QuestionsList("Which word is synonymous with 'equivocate'?", "Elaborate", "Mislead", "Clarify", "Obfuscate", "Mislead", "");
        QuestionsList question14 = new QuestionsList("What's the closest meaning to 'prolific'?", "Scarcity", "Abundant", "Inactive", "Unproductive", "Inactive", "");
        QuestionsList question15 = new QuestionsList("What's the opposite of 'gregarious'?", "Sociable", "Outgoing", "Introverted", "Friendly", "Sociable", "");
        QuestionsList question16 = new QuestionsList("Which word is similar to 'ostensible'?", "Evident", "Hidden", "Obvious", "Apparent", "Apparent", "");
        QuestionsList question17 = new QuestionsList("Which word does not match 'acerbic'?", "Biting", "Caustic", "Sharp", "Sweet", "Caustic", "");
        QuestionsList question18 = new QuestionsList("What's the synonym for 'ubiquitous'?", "Rare", "Limited", "Scarce", "Pervasive", "Pervasive", "");
        QuestionsList question19 = new QuestionsList("What word is related to 'juxtapose'?", "Separate", "Contrast", "Compare", "Differentiate", "Separate", "");
        QuestionsList question20 = new QuestionsList("Choose the word that doesn't correspond to 'ubiquitous'", "Everywhere", "Omnipresent", "Limited", "Prevalent", "Omnipresent", "");

        QuestionsList question21 = new QuestionsList("What's the closest synonym to 'laconic'?", "Brief", "Verbose", "Wordy", "Loquacious", "Wordy", "");
        QuestionsList question22 = new QuestionsList("What's the opposite of 'meticulous'?", "Careful", "Precise", "Negligent", "Thorough", "Careful", "");
        QuestionsList question23 = new QuestionsList("What's another word for 'alleviate'?", "Worsen", "Aggravate", "Intensify", "Relieve", "Aggravate", "");
        QuestionsList question24 = new QuestionsList("Which word is synonymous with 'candid'?", "Secretive", "Open", "Sly", "Deceptive", "Open", "");
        QuestionsList question25 = new QuestionsList("Choose the word that doesn't fit with 'concur'", "Agree", "Accord", "Disagree", "Assent", "Disagree", "");
        QuestionsList question26 = new QuestionsList("What's the opposite of 'indolent'?", "Lazy", "Industrious", "Sluggish", "Idle", "Lazy", "");
        QuestionsList question27 = new QuestionsList("What word relates to 'exacerbate'?", "Aggravate", "Improve", "Ameliorate", "Alleviate", "Aggravate", "");
        QuestionsList question28 = new QuestionsList("Which word is similar to 'ephemeral'?", "Lasting", "Transient", "Permanent", "Enduring", "Enduring", "");
        QuestionsList question29 = new QuestionsList("What's another word for 'resilient'?", "Fragile", "Flexible", "Strong", "Vulnerable", "Flexible", "");
        QuestionsList question30 = new QuestionsList("Choose the word that doesn't match 'zeal'", "Enthusiasm", "Passion", "Apathy", "Ardor", "Apathy", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);
        questionsLists.add(question6);
        questionsLists.add(question7);
        questionsLists.add(question8);
        questionsLists.add(question9);
        questionsLists.add(question10);
        questionsLists.add(question11);
        questionsLists.add(question12);
        questionsLists.add(question13);
        questionsLists.add(question14);
        questionsLists.add(question15);
        questionsLists.add(question16);
        questionsLists.add(question17);
        questionsLists.add(question18);
        questionsLists.add(question19);
        questionsLists.add(question20);
        questionsLists.add(question21);
        questionsLists.add(question22);
        questionsLists.add(question23);
        questionsLists.add(question24);
        questionsLists.add(question25);
        questionsLists.add(question26);
        questionsLists.add(question27);
        questionsLists.add(question28);
        questionsLists.add(question29);
        questionsLists.add(question30);

        return  questionsLists;
    }

    private static List<QuestionsList> cefrQuestions(){

        List<QuestionsList> questionsLists = new ArrayList<>();
        QuestionsList question1 = new QuestionsList("Tara was too _____ to speak when she heard the shocking news.", "ecstatic", "calm", "proud", "stunned", "stunned", "");
        QuestionsList question2 = new QuestionsList("Although the rest of the family had strong opinions about the matter, Maud felt quite _____.", "calm", "relieved", "indifferent", "hysterical", "calm", "");
        QuestionsList question3 = new QuestionsList("Everything had gone exactly as planned, and I felt very _____.", "satisfied", "overwhelmed", "bewildered", "terrified", "satisfied", "");
        QuestionsList question4 = new QuestionsList("Maria was _____ by her husband’s insensitive comments about her weight.", "relaxed", "exhilarated", "anxious", "offended", "offended", "");
        QuestionsList question5 = new QuestionsList("I opened my wife’s diary because I was _____ to find out what she’d written about me.", "calm", "curious", "proud", "furious", "curious", "");
        QuestionsList question6 = new QuestionsList("After purchasing a new phone, my account was ______.", "prodigal", "broke", "in the red", "bankrupt", "in the red", "");
        QuestionsList question7 = new QuestionsList("The ring is absolutely ______! It's not even made of real silver.", "penniless", "worthless", "cheap", "frugal", "worthless", "");
        QuestionsList question8 = new QuestionsList("I can't afford to go out this weekend. I'm completely ______.", "cheap", "prodigal", "broke", "good value", "broke", "");
        QuestionsList question9 = new QuestionsList("He’s obviously _____ her. He talks about her all the time.", "fallen for", "making up with", "dumped", "flirting", "fallen for", "");
        QuestionsList question10 = new QuestionsList("The beach cleanup initiative focused on removing _____, which is harmful to marine life.", "waste management", "pollution control", "plastic waste", "rising sea levels", "plastic waste", "");

        QuestionsList question11 = new QuestionsList("What's the opposite of 'arduous'?", "Difficult", "Tough", "Laborious", "Happy", "Happy", "");
        QuestionsList question12 = new QuestionsList("Choose the word that best relates to 'adroit'", "Clumsy", "Awkward", "Inept", "Skilled", "Clumsy", "");
        QuestionsList question13 = new QuestionsList("Which word is synonymous with 'equivocate'?", "Elaborate", "Mislead", "Clarify", "Obfuscate", "Mislead", "");
        QuestionsList question14 = new QuestionsList("What's the closest meaning to 'prolific'?", "Scarcity", "Abundant", "Inactive", "Unproductive", "Inactive", "");
        QuestionsList question15 = new QuestionsList("What's the opposite of 'gregarious'?", "Sociable", "Outgoing", "Introverted", "Friendly", "Sociable", "");
        QuestionsList question16 = new QuestionsList("Which word is similar to 'ostensible'?", "Evident", "Hidden", "Obvious", "Apparent", "Apparent", "");
        QuestionsList question17 = new QuestionsList("Which word does not match 'acerbic'?", "Biting", "Caustic", "Sharp", "Sweet", "Caustic", "");
        QuestionsList question18 = new QuestionsList("What's the synonym for 'ubiquitous'?", "Rare", "Limited", "Scarce", "Pervasive", "Pervasive", "");
        QuestionsList question19 = new QuestionsList("What word is related to 'juxtapose'?", "Separate", "Contrast", "Compare", "Differentiate", "Separate", "");
        QuestionsList question20 = new QuestionsList("Choose the word that doesn't correspond to 'ubiquitous'", "Everywhere", "Omnipresent", "Limited", "Prevalent", "Omnipresent", "");

        QuestionsList question21 = new QuestionsList("What's the closest synonym to 'laconic'?", "Brief", "Verbose", "Wordy", "Loquacious", "Wordy", "");
        QuestionsList question22 = new QuestionsList("What's the opposite of 'meticulous'?", "Careful", "Precise", "Negligent", "Thorough", "Careful", "");
        QuestionsList question23 = new QuestionsList("What's another word for 'alleviate'?", "Worsen", "Aggravate", "Intensify", "Relieve", "Aggravate", "");
        QuestionsList question24 = new QuestionsList("Which word is synonymous with 'candid'?", "Secretive", "Open", "Sly", "Deceptive", "Open", "");
        QuestionsList question25 = new QuestionsList("Choose the word that doesn't fit with 'concur'", "Agree", "Accord", "Disagree", "Assent", "Disagree", "");
        QuestionsList question26 = new QuestionsList("What's the opposite of 'indolent'?", "Lazy", "Industrious", "Sluggish", "Idle", "Lazy", "");
        QuestionsList question27 = new QuestionsList("What word relates to 'exacerbate'?", "Aggravate", "Improve", "Ameliorate", "Alleviate", "Aggravate", "");
        QuestionsList question28 = new QuestionsList("Which word is similar to 'ephemeral'?", "Lasting", "Transient", "Permanent", "Enduring", "Enduring", "");
        QuestionsList question29 = new QuestionsList("What's another word for 'resilient'?", "Fragile", "Flexible", "Strong", "Vulnerable", "Flexible", "");
        QuestionsList question30 = new QuestionsList("Choose the word that doesn't match 'zeal'", "Enthusiasm", "Passion", "Apathy", "Ardor", "Apathy", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);
        questionsLists.add(question6);
        questionsLists.add(question7);
        questionsLists.add(question8);
        questionsLists.add(question9);
        questionsLists.add(question10);
        questionsLists.add(question11);
        questionsLists.add(question12);
        questionsLists.add(question13);
        questionsLists.add(question14);
        questionsLists.add(question15);
        questionsLists.add(question16);
        questionsLists.add(question17);
        questionsLists.add(question18);
        questionsLists.add(question19);
        questionsLists.add(question20);
        questionsLists.add(question21);
        questionsLists.add(question22);
        questionsLists.add(question23);
        questionsLists.add(question24);
        questionsLists.add(question25);
        questionsLists.add(question26);
        questionsLists.add(question27);
        questionsLists.add(question28);
        questionsLists.add(question29);
        questionsLists.add(question30);

        return  questionsLists;
    }
    public static List<QuestionsList> getQuestions(@NonNull String selectedLevelName) {
        List<QuestionsList> selectedQuestions;

        switch (selectedLevelName) {
            case "ielts":
                selectedQuestions = ietlsQuestions();
                break;
            case "toeic":
                selectedQuestions = toeicQuestions();
                break;
            case "cambridge":
                selectedQuestions = cambridgeQuestions();
                break;
            default:
                selectedQuestions = cefrQuestions();
                break;
        }

        // Xáo trộn danh sách câu hỏi
        Collections.shuffle(selectedQuestions);

        // Lấy 10 câu hỏi đầu tiên từ danh sách đã xáo trộn
        return selectedQuestions.subList(0, 10);
    }
}
