package com.sergey.zhuravlev.pgu.schedule.preprocessor;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.IOException;
import java.io.InputStream;

public class WordLoader {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public XWPFDocument loadDocument(InputStream inputStream) throws IOException {
        XWPFDocument document = new XWPFDocument(inputStream);
        document.close();
        return document;

    }

}
