package com.sergey.zhuravlev.pgu.schedule.parser;

import android.util.Log;

import com.sergey.zhuravlev.pgu.schedule.exception.ParseScheduleException;
import com.sergey.zhuravlev.pgu.schedule.model.Classwork;
import com.sergey.zhuravlev.pgu.schedule.model.ClassworkPeriod;
import com.sergey.zhuravlev.pgu.schedule.model.DayOfWeek;
import com.sergey.zhuravlev.pgu.schedule.model.Group;
import com.sergey.zhuravlev.pgu.schedule.model.Schedule;
import com.sergey.zhuravlev.pgu.schedule.utils.Utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleParser {

    private static final Pattern classwork = Pattern.compile("(^.*)\\s([А-Яа-я]+\\s[А-Я]\\.[А-Я]\\.)\\s+ауд\\.?([0-9]{3}н?)");

    private static final String russianAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String numbers = "1234567890";

    public static Schedule parse(XWPFDocument document) throws ParseScheduleException {
        List<Classwork> rawSchedule = null;
        for (XWPFTable table : document.getTables()) {
            try {
                rawSchedule = parseTable(table);
            } catch (ParseScheduleException e) {
                continue;
            }
            if (rawSchedule.size() > 0)
                break;
        }
        if (rawSchedule == null)
            throw new ParseScheduleException("Can't found target schedule in doc");
        return new Schedule(rawSchedule);
    }

    private static List<Classwork> parseTable(XWPFTable rawTable) throws ParseScheduleException {
        List<Classwork> rawSchedule = new ArrayList<>();
        DayOfWeek dayOfWeek = null;
        Group group = null;

        for (XWPFTableRow xwpfTableRow : rawTable.getRows()) {
            ClassworkPeriod classworkPeriod = null;
            for (XWPFTableCell xwpfTableCell : xwpfTableRow.getTableCells()) {
                Log.d("Parser", "TcPrChange = " + xwpfTableCell.getCTTc().getTcPr().isSetTcPrChange());
                Log.d("Parser", "CELL " + xwpfTableCell.getText());
                if (isGroup(xwpfTableCell.getText())) {
                    group = getGroup(xwpfTableCell.getText());
                    Log.d("Parser", "\tGROUP " + dayOfWeek);
                } else if (isDayOfWeek(xwpfTableCell.getText())) {
                    dayOfWeek = getDayOfWeek(xwpfTableCell.getText());
                    Log.d("Parser", "\tDAY_OF_WEEK " + dayOfWeek);
                } else if (isClassworkPeriod(xwpfTableCell.getText())) {
                    classworkPeriod = getClassworkPeriod(xwpfTableCell.getText());
                    Log.d("Parser", "\tPERIOD " + classworkPeriod);
                } else if (dayOfWeek != null && classworkPeriod != null && haveText(xwpfTableCell.getText())) {
                    Matcher matcher = classwork.matcher(xwpfTableCell.getText());
                    if (matcher.find()) {
                        rawSchedule.add(new Classwork(dayOfWeek, classworkPeriod, group, getClassworkTitle(matcher), getTeacher(matcher), getAudience(matcher)));
                    } else {
                        rawSchedule.add(new Classwork(dayOfWeek, classworkPeriod, group, xwpfTableCell.getText(), null, null));
                        Log.d("Parser", "\tCLASSWORK " + xwpfTableCell.getText());
                    }
                }
            }
        }
        return rawSchedule;
    }

    private static boolean haveText(String text) {
        if (text.isEmpty()) return false;

        boolean haveText = false;
        for (char c : text.toCharArray()) {
            if (c != ' ')
                haveText = true;
        }
        return haveText;
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

    private static String getClassworkTitle(Matcher matcher) {
        Log.d("Parser", "ClassworkTitle " + matcher.group(0));
        return matcher.group(1);
    }

    private static String getTeacher(Matcher matcher) {
        Log.d("Parser", "Teacher " + matcher.group(0));
        return matcher.group(2);
    }

    private static String getAudience(Matcher matcher) {
        Log.d("Parser", "Audience " + matcher.group(0));
        return matcher.group(3);
    }

}
