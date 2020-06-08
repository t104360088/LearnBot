package com.example.mymaterial.eliza;

public interface Talker {
    String talk(String msg);
    boolean finished();
    String getInitial();
}
