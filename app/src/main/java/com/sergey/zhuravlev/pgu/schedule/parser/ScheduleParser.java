package com.sergey.zhuravlev.pgu.schedule.parser;

import android.util.Log;

import com.sergey.zhuravlev.pgu.schedule.exception.ParseScheduleException;
import com.sergey.zhuravlev.pgu.schedule.model.ClassworkPeriod;
import com.sergey.zhuravlev.pgu.schedule.model.DayOfWeek;
import com.sergey.zhuravlev.pgu.schedule.model.Group;
import com.sergey.zhuravlev.pgu.schedule.model.Schedule;
import com.sergey.zhuravlev.pgu.schedule.utils.Utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class ScheduleParser {

    private static final String russianAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String numbers = "1234567890";

    public static Schedule parse(XWPFDocument document) {
        Schedule schedule = null;
        for (XWPFTable table : document.getTables()) {
            try {
                schedule = parseTable(table);
            } catch (ParseScheduleException e) {
                continue;
            }
            if (schedule.getRawSchedule().size() > 0)
                break;
        }
        return schedule;
    }

    private static Schedule parseTable(XWPFTable rawTable) throws ParseScheduleException {
        Schedule schedule = new Schedule();
        DayOfWeek dayOfWeek = null;
        Group group = null;

        for (XWPFTableRow xwpfTableRow : rawTable.getRows()) {
            ClassworkPeriod classworkPeriod = null;
            for (XWPFTableCell xwpfTableCell : xwpfTableRow.getTableCells()) {
                Log.d("Test", "TcPrChange = " + xwpfTableCell.getCTTc().getTcPr().isSetTcPrChange());
                Log.d("Test", "CELL " + xwpfTableCell.getText());
                if (isGroup(xwpfTableCell.getText())) {
                    group = getGroup(xwpfTableCell.getText());
                    Log.d("Test", "\tGROUP " + dayOfWeek);
                } else if (isDayOfWeek(xwpfTableCell.getText())) {
                    dayOfWeek = getDayOfWeek(xwpfTableCell.getText());
                    Log.d("Test", "\tDAY_OF_WEEK " + dayOfWeek);
                } else if (isClassworkPeriod(xwpfTableCell.getText())) {
                    classworkPeriod = getClassworkPeriod(xwpfTableCell.getText());
                    Log.d("Test", "\tPERIOD " + classworkPeriod);
                } else if (dayOfWeek != null && classworkPeriod != null) {
                    schedule.addClasswork(dayOfWeek, classworkPeriod, xwpfTableCell.getText(), group);
                    Log.d("Test", "\tCLASSWORK " + xwpfTableCell.getText());
                }
            }
        }
        return schedule;
    }

    private static boolean isDayOfWeek(String text) {
        try {
            getDayOfWeek(text);
        } catch (ParseScheduleException e) {
            return false;
        }
        return true;
    }

    private static DayOfWeek getDayOfWeek(String text) throws ParseScheduleException {
        switch (Utils.filterString(text, russianAlphabet)) {
            case "понедельник":
                return DayOfWeek.MONDAY;
            case "вторник":
                return DayOfWeek.TUESDAY;
            case "среда":
                return DayOfWeek.WEDNESDAY;
            case "четверг":
                return DayOfWeek.THURSDAY;
            case "пятница":
                return DayOfWeek.FRIDAY;
            case "суббота":
                return DayOfWeek.SATURDAY;
            default:
                throw new ParseScheduleException("DayOfWeek not parsable");
        }
    }

    private static boolean isClassworkPeriod(String text) {
        try {
            getClassworkPeriod(text);
        } catch (ParseScheduleException e) {
            return false;
        }
        return true;
    }

    private static ClassworkPeriod getClassworkPeriod(String text) throws ParseScheduleException {
        switch (Utils.filterString(text, numbers)) {
            case "8301000":
                return ClassworkPeriod.FIRST_CLASSWORK;
            case "10151145":
                return ClassworkPeriod.SECOND_CLASSWORK;
            case "12151345":
                return ClassworkPeriod.THIRD_CLASSWORK;
            case "14151545":
                return ClassworkPeriod.FOURTH_CLASSWORK;
            case "16001730":
                return ClassworkPeriod.FIFTH_CLASSWORK;
            case "17451915":
                return ClassworkPeriod.SIXTH_CLASSWORK;
            case "19252050":
                return ClassworkPeriod.SEVENTH_CLASSWORK;
            default:
                throw new ParseScheduleException("ClassworkPeriod not parsable");
        }

    }

    private static boolean isGroup(String text) {
        try {
            getGroup(text);
        } catch (ParseScheduleException e) {
            return false;
        }
        return true;
    }

    private static Group getGroup(String text) throws ParseScheduleException {
        int year;

        try {
            String yearString = Utils.filterString(text, numbers);
            year = Integer.valueOf(yearString);
        } catch (NumberFormatException error) {
            throw new ParseScheduleException("Group year not parsable");
        }

        String name = Utils.filterString(text, russianAlphabet);

        if (name.startsWith("арх")) {
            return new Group("архитектура", year);
        } else if (name.startsWith("гео")) {
            return new Group("геодезия", year);
        } else {
            throw new ParseScheduleException("Group not parsable");
        }

    }

}
