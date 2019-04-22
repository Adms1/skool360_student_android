package com.anandniketanbhadaj.skool360student.Utility;

import com.anandniketanbhadaj.skool360student.Activities.MyApp;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class AppConfiguration {

    //Local
    public static String DOMAIN_LOCAL = "http://192.168.1.14:8089/MobileApp_Service.asmx/";
    public static String GET_API_URL = "http://anandniketanbhadaj.org/appService/5b9a72856992e144c74fc836ed6e76a2/appsUrl";

    public static String LIVE_BASE_URL = "http://192.168.1.14:8089/";

    // public static String DOMAIN_LIVE = "http://192.168.1.11:8086/MobileApp_Service.asmx/";//use for office only
//   public static String DOMAIN_LIVE = "http://192.168.1.187:8089/MobileApp_Service.asmx/";//client for office only
    //public static String DOMAIN_LIVE = "http://103.24.183.28:8085/MobileApp_Service.asmx/";//use for client

    //public static String LIVE_BASE_URL = Utility.getPref(MyApp.getAppContext(), "live_base_url");
//    public static String LIVE_BASE_URL = "http://192.168.1.22:8089/";
    public static String DOMAIN_LIVE = "";//use for client
    public static String BASEURL = LIVE_BASE_URL + "MobileApp_Service.asmx/";
    //public static String IMAGE_LIVE = "http://192.168.1.11:8086/SKOOL360-Category-Images-Android/Student/";
    //  public static String IMAGE_LIVE="http://192.168.1.187:8089/SKOOL360-Category-Images-Android/Student/";

    //change by antra - 15/03/2019
//    public static String IMAGE_LIVE = LIVE_BASE_URL+"SKOOL360-Category-Images-Android/Student/";
    public static String IMAGE_LIVE = LIVE_BASE_URL + "SKOOL360-Category-Images-Android/Student/";

    // public static String GALLARY_LIVE = "http://192.168.1.11:8086/";
//  public static String GALLARY_LIVE="http://192.168.1.187:8089/";
    public static String GALLARY_LIVE = LIVE_BASE_URL;

    //Webservice name
    public static String StudentLogin = "StudentLogin";
    public static String GetUserProfile = "GetUserProfile";
    public static String ChangePassword = "ChangePassword";
    public static String GetClasswork = "GetClasswork";
    public static String GetHomework = "GetHomework";
    public static String GetAttendence = "GetAttendence";
    public static String GetTimetable = "GetTimetable";
    public static String GetTimetableNew = "GetTimetableNew";

    public static String AddAppointmentRequest = "AddAppointmentRequest";
    public static String GetTerm = "GetTerm";
    public static String GetImprest = "GetImprest";
    public static String GetEvent = "GetEvent";
    public static String GetAnnouncement = "GetAnnouncement";
    public static String GetCanteenMenu = "GetCanteenMenu";
    public static String GetCircular = "GetCircular";
    public static String GetTestDetail = "GetTestDetail";
    public static String AddDeviceDetail = "AddDeviceDetail";
    public static String GetStudentResult = "GetStudentResult";
    public static String GetReportcard = "GetReportCard";
    public static String GetResultPermission = "GetResultPermission";
    public static String GetFeesStatus = "GetFeesStatus";
    public static String GetPaymentLedger = "PaymentLedger";
    public static String GetPrincipalMessage = "GetPrincipalMessage";
    public static String PTMTeacherStudentGetDetail = "PTMTeacherStudentGetDetail";
    public static String PTMTeacherStudentInsertDetail = "PTMTeacherStudentInsertDetail";
    public static String PTMDeleteMeeting = "PTMDeleteMeeting";
    public static String PTMStudentWiseTeacher = "PTMStudentWiseTeacher";
    // public static String GetCircularDetail = "GetCircularDetail"; Add Navin 14-08-2018
    public static String GetCircularDetail = "GetCircularDetailStandard";
    public static String DeviceVersion = "GetLatestVersion";//DeviceVersion
    public static String GetGallery = "GetGallery";
    public static String InsertStudentLeaveRequest = "InsertStudentLeaveRequest";
    public static String GetStudentLeaveRequest = "GetStudentLeaveRequest";
    public static String CreateParentsSuggestion = "CreateParentsSuggestion";
    public static String GetHoliday = "GetHoliday";
    public static String DeleteDeviceDetail = "DeleteDeviceDetail";
    public static String ForgetIDPassword = "ForgetIDPassword";
    public static String GetSuggestion = "GetSuggestion";
    public static String GetStandardSection = "GetStandardSection";
    public static String StudentList = "StudentList";

    public static String parentLogin = "ParentLogin";
    public static String parentChagePassword = "ParentChangePassword";

    public static String Notification;
    public static String UserImage = "";
    public static String UserName = "";
    public static String UserGrade = "";
    public static String UserGrNo = "";
    public static String UserAttendance = "";
    public static String UserTeacherName = "";
    public static String UserDropTime = "";
    public static String UserPickTime = "";
    public static boolean firsttimeback;
    public static int position;
    public static String dataNOtification = "";
    public static String messageNotification = "";

    static Domain domain = Domain.LOCAL;

    public static String getUrl(String methodName) {
        String url = "";

        switch (domain) {
            case LIVE:

//                LIVE_BASE_URL = LIVE_BASE_URL;
                LIVE_BASE_URL = Utility.getPref(MyApp.getAppContext(), "live_base_url");

                IMAGE_LIVE = LIVE_BASE_URL + "SKOOL360-Category-Images-Android/Student/";

                GALLARY_LIVE = LIVE_BASE_URL;

                AppConfiguration.DOMAIN_LIVE = LIVE_BASE_URL + "MobileApp_Service.asmx/";

                url = DOMAIN_LIVE + methodName;

                break;

            case LOCAL:

                url = DOMAIN_LOCAL + methodName;

                IMAGE_LIVE = "http://192.168.1.14:8089/SKOOL360-Category-Images/Student/";
//
                GALLARY_LIVE = "http://192.168.1.14:8089/";

                break;
            default:
                break;
        }
        return url;

    }


//    @Override
//    public void getURL(String baseurl) {
//
//        DOMAIN_LIVE = baseurl+"MobileApp_Service.asmx/";
//    }

    public enum Domain {
        LIVE, LOCAL
    }
}
