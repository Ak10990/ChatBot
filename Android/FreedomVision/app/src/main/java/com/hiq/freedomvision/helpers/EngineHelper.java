package com.hiq.freedomvision.helpers;

import java.util.HashMap;

/**
 * Created by akanksha on 22/10/16.
 */

public class EngineHelper {

    private int id;
    private String possibleQuery;
    private HashMap<String, String> possibleAnswers;

    public EngineHelper(String possibleQuery, HashMap<String, String> possibleAnswers) {
        this.possibleQuery = possibleQuery;
        this.possibleAnswers = possibleAnswers;
    }
}
