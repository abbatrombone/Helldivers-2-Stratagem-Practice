package me.abbatrombone.traz;

import com.opencsv.bean.CsvBindByName;

public class keybindsPOJO {
    @CsvBindByName
    //@CsvBindByPosition(position = 0)
    private String key;
    @CsvBindByName
    //@CsvBindByPosition(position = 1)
    private String bind;

    @CsvBindByName
    //@CsvBindByPosition(position = 2)
    private int keycode;

    public String getKey() {return key;}
    public void setKey(String key) {this.key = key;}

    public String getBind() {return bind;}
    public void setBind(String bind) {this.bind = bind;}

    public int getkeycode() {return keycode;}
    public void setkeycode(int keycode) {this.keycode = keycode;}

    public String toString() {return String.format("%s,%s", key, bind);
    }
}
