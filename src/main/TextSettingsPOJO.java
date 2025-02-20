package me.abbatrombone.traz;

import com.opencsv.bean.CsvBindByName;

public class TextSettingsPOJO {

    @CsvBindByName
    //@CsvBindByPosition(position = 0)
    private String textColor;

    @CsvBindByName
    //@CsvBindByPosition(position = 1)
    private int textSize;

    @CsvBindByName
    //@CsvBindByPosition(position = 2)
    private int textboxSize;

    @CsvBindByName
    //@CsvBindByPosition(position = 3)
    private String textboxColor;

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextboxSize() {
        return textboxSize;
    }

    public void setTextboxSize(int textboxSize) {
        this.textboxSize = textboxSize;
    }

    public String getTextboxColor() {
        return textboxColor;
    }

    public void setTextboxColor(String textboxColor) {
        this.textboxColor = textboxColor;
    }
}
