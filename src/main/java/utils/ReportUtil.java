package utils;

import freemarker.template.utility.StringUtil;
import org.testng.Reporter;
import java.util.Calendar;

/**
 * Created by qingping.niu on 2017/11/21.
 */
public class ReportUtil {
    private static String reportName = "自动化测试报告";

    private static String splitTimeAndMsg = "===";
    public static void log(String msg) {
        long timeMillis = Calendar.getInstance().getTimeInMillis();
        Reporter.log(timeMillis + splitTimeAndMsg + msg, true);
    }

    public static String getReportName() {
        return reportName;
    }

    public static String getSpiltTimeAndMsg() {
        return splitTimeAndMsg;
    }

    public static void setReportName(String reportName) {
        if(utils.StringUtil.isNotEmpty(reportName)){
            ReportUtil.reportName = reportName;
        }
    }
}
