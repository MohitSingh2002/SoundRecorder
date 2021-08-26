package com.mohitsingh.soundrecorder.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public String getRecordingFileName(long milliseconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mma", Locale.getDefault());
        return simpleDateFormat.format(new Date(milliseconds));
    }

}
