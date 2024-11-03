// PasswordStrength.java
package com.example.myapplication.ui.check;

import androidx.annotation.ColorRes;

public class PasswordStrength {
    private String label;
    private int score;
    private int colorRes;

    public PasswordStrength(String label, int score, int colorRes) {
        this.label = label;
        this.score = score;
        this.colorRes = colorRes;
    }

    public String getLabel() {
        return label;
    }

    public int getScore() {
        return score;
    }

    @ColorRes
    public int getColorRes() {
        return colorRes;
    }
}
