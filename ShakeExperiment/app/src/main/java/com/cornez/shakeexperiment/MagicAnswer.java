package com.cornez.shakeexperiment;

import android.content.Context;

public class MagicAnswer {
    private String[] allAnswers;

    public MagicAnswer(Context context) {
        //COLLECT ALL THE POSSIBLE ANSWERS FROM THE ARRAY IN STRINGS.XML
        allAnswers = context.getResources().getStringArray(R.array.magic_answer_list);
    }

    public String getRandomAnswer() {
        int i = (int) Math.ceil(Math.random() * (allAnswers.length-1));
        return allAnswers[i];
    }

}
