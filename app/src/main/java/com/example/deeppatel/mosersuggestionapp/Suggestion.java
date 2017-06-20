package com.example.deeppatel.mosersuggestionapp;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by deep.patel on 6/18/17.
 */

public class Suggestion {

    public String suggestion_id;
    public String input;
    public String creator;
    public Date created;
    public Map<String, Boolean> votes = new HashMap();

    public Suggestion() {}

    public Suggestion(String id, String text, String uid) {
        suggestion_id = id;
        input = text;
        creator = uid;
        created = new Date();
    }

    // voting

    @Exclude
    public boolean isCreator(String uid) {
        return (uid.equals(creator));
    }

    @Exclude
    // returns true if passed in user upvoted the suggestion
    public boolean upVoted(String uid) {
        Iterator i = votes.entrySet().iterator();

        while(i.hasNext()) {
            Map.Entry<String, Boolean> pair = (Map.Entry)i.next();
            if (pair.getKey().equals(uid))
                return pair.getValue();
        }

        return false;
    }

    @Exclude
    // call vote(true) to upvote, vote(false) to remove upvote
    public void vote(String uid, boolean direction) {
        if (isCreator(uid)) return;
        votes.put(uid, direction);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("suggestion_id", suggestion_id);
        result.put("input", input);
        result.put("creator", creator);
        result.put("created", created);
        result.put("votes", votes);
        return result;
    }
}
