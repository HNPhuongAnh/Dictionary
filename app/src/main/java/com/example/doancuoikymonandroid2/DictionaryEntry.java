package com.example.doancuoikymonandroid2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DictionaryEntry {
    @SerializedName("word")
    private String word;

    @SerializedName("phonetic")
    private String phonetic;

    @SerializedName("phonetics")
    private List<Phonetic> phonetics;

    @SerializedName("origin")
    private String origin;

    @SerializedName("meanings")
    private List<Meaning> meanings;

    // getters và setters

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public List<Phonetic> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<Phonetic> phonetics) {
        this.phonetics = phonetics;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

    public static class Phonetic {
        @SerializedName("text")
        private String text;

        @SerializedName("audio")
        private String audio;

        // getters và setters

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getAudio() {
            return audio;
        }

        public void setAudio(String audio) {
            this.audio = audio;
        }
    }

    public static class Meaning {
        @SerializedName("partOfSpeech")
        private String partOfSpeech;

        @SerializedName("definitions")
        private List<Definition> definitions;

        // getters và setters

        public String getPartOfSpeech() {
            return partOfSpeech;
        }

        public void setPartOfSpeech(String partOfSpeech) {
            this.partOfSpeech = partOfSpeech;
        }

        public List<Definition> getDefinitions() {
            return definitions;
        }

        public void setDefinitions(List<Definition> definitions) {
            this.definitions = definitions;
        }

        public static class Definition {
            @SerializedName("definition")
            private String definition;

            @SerializedName("example")
            private String example;

            // getters và setters

            public String getDefinition() {
                return definition;
            }

            public void setDefinition(String definition) {
                this.definition = definition;
            }

            public String getExample() {
                return example;
            }

            public void setExample(String example) {
                this.example = example;
            }
        }
    }
}


