package com.sergey.zhuravlev.testegorka.preprocessor;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class WordProcessor {

    public static int hex2Rgb(String colorStr) {
        return Color.argb(255,
                Integer.valueOf(colorStr.substring(0, 2), 16),
                Integer.valueOf(colorStr.substring(2, 4), 16),
                Integer.valueOf(colorStr.substring(4, 6), 16));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public XWPFTable loadDocument(InputStream inputStream, int num) throws IOException {
        XWPFDocument document = new XWPFDocument(inputStream);
        List<XWPFTable> tables = document.getTables();
        document.close();
        return tables.get(num);

    }

}
