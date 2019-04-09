package com.anandniketanbhadaj.skool360student.Utility;

import com.anandniketanbhadaj.skool360student.Models.AttendanceModel;
import com.anandniketanbhadaj.skool360student.Models.CircularModel;
import com.anandniketanbhadaj.skool360student.Models.ClassWorkModel;
//import com.anandniketanbhadaj.skool360.Models.ExamSyllabus.FinalArray;
import com.anandniketanbhadaj.skool360student.Models.HomeWorkModel;
import com.anandniketanbhadaj.skool360student.Models.PaymentLedgerModel;
import com.anandniketanbhadaj.skool360student.Models.ReportCardModel;
import com.anandniketanbhadaj.skool360student.Models.ResultModel;
import com.anandniketanbhadaj.skool360student.Models.StudProfileModel;
import com.anandniketanbhadaj.skool360student.Models.TermModel;
import com.anandniketanbhadaj.skool360student.Models.TimetableModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class ParseJSON {

    public static HashMap<String, String> parseLoginJson(String responseString) {
        HashMap<String, String> result = new HashMap<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            result.put("Success",reader.getString("Success"));
            result.put("Status",reader.getString("Status"));
            if (data_load_basket.toString().equals("True")) {
                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");

                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    result.put("StudentID", jsonChildNode.getString("StudentID"));
                    result.put("FamilyID", jsonChildNode.getString("FamilyID"));
                    result.put("StandardID", jsonChildNode.getString("StandardID"));
                    result.put("ClassID", jsonChildNode.getString("ClassID"));
                    result.put("TermID", jsonChildNode.getString("TermID"));
                    result.put("RegisterStatus",jsonChildNode.getString("RegisterStatus"));
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Boolean parseChangePwdJson(String responseString) {
        Boolean result = false;

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            if (data_load_basket.toString().equals("True")) {
                result = true;
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Boolean parseAppointmentJson(String responseString) {
        Boolean result = false;

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            if (data_load_basket.toString().equals("True")) {
                result = true;
            } else {
                result = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<StudProfileModel> parseUserProfileJson(String responseString) {
        ArrayList<StudProfileModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            if (data_load_basket.toString().equals("True")) {
                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");

                StudProfileModel studProfileModel = null;
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    studProfileModel = new StudProfileModel();
                    studProfileModel.setStudentName(jsonChildNode.getString("StudentName"));
                    studProfileModel.setStudentDOB(jsonChildNode.getString("StudentDOB"));
                    studProfileModel.setStudentAge(jsonChildNode.getString("StudentAge"));
                    studProfileModel.setStudentGender(jsonChildNode.getString("StudentGender"));
                    studProfileModel.setBloodGroup(jsonChildNode.getString("BloodGroup"));
                    studProfileModel.setBirthPlace(jsonChildNode.getString("BirthPlace"));
                    studProfileModel.setCaste(jsonChildNode.getString("Caste"));
                    studProfileModel.setHouse(jsonChildNode.getString("House"));
                    studProfileModel.setStudentImage(jsonChildNode.getString("StudentImage"));
                    studProfileModel.setFatherName(jsonChildNode.getString("FatherName"));
                    studProfileModel.setFatherPhone(jsonChildNode.getString("FatherPhone"));
                    studProfileModel.setFatherEmail(jsonChildNode.getString("FatherEmail"));
                    studProfileModel.setMotherName(jsonChildNode.getString("MotherName"));
                    studProfileModel.setMotherMobile(jsonChildNode.getString("MotherMobile"));
                    studProfileModel.setMotherEmail(jsonChildNode.getString("MotherEmail"));
                    studProfileModel.setAddress(jsonChildNode.getString("Address"));
                    studProfileModel.setCity(jsonChildNode.getString("City"));
                    studProfileModel.setSMSNumber(jsonChildNode.getString("SMSNumber"));
                    studProfileModel.setTransport_KM(jsonChildNode.getString("Transport_KM"));
                    studProfileModel.setTransport_PicupTime(jsonChildNode.getString("Transport_PicupTime"));
                    studProfileModel.setTransport_DropTime(jsonChildNode.getString("Transport_DropTime"));
                    studProfileModel.setBusNo(jsonChildNode.getString("Bus No"));
                    studProfileModel.setRouteName(jsonChildNode.getString("Route Name"));
                    studProfileModel.setPickupPointName(jsonChildNode.getString("PickupPoint Name"));
                    studProfileModel.setDropPointName(jsonChildNode.getString("DropPoint Name"));
                    studProfileModel.setGRNO(jsonChildNode.getString("GRNO"));
                    studProfileModel.setStandard(jsonChildNode.getString("Standard"));
                    studProfileModel.setStudClass(jsonChildNode.getString("Class"));
                    studProfileModel.setAddmissionDate(jsonChildNode.getString("AddmissionDate"));
                    studProfileModel.setUserName(jsonChildNode.getString("UserName"));
                    studProfileModel.setPassword(jsonChildNode.getString("Password"));
                    studProfileModel.setTeacherName(jsonChildNode.getString("ClassTeacher"));
//                    studProfileModel.setTodayAttendance(jsonChildNode.getString("TodayAttendance"));
                    result.add(studProfileModel);
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<ClassWorkModel> parseStudClassworkJson(String responseString) {
        ArrayList<ClassWorkModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            if (data_load_basket.toString().equals("True")) {
                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                ClassWorkModel classWorkModel = null;

                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    classWorkModel = new ClassWorkModel();
                    classWorkModel.setClassWorkDate(jsonChildNode.getString("ClassWorkDate"));

                    ClassWorkModel.ClassWorkData classWorkData = null;
                    ArrayList<ClassWorkModel.ClassWorkData> classWorkDatas = new ArrayList<>();
                    JSONArray jsonMainNode1 = jsonChildNode.optJSONArray("Data");
                    for (int j = 0; j < jsonMainNode1.length(); j++) {
                        JSONObject jsonChildNode1 = jsonMainNode1.getJSONObject(j);
                        classWorkData = classWorkModel.new ClassWorkData();
                        classWorkData.setSubject(jsonChildNode1.getString("Subject"));
                        classWorkData.setClasswork(jsonChildNode1.getString("Classwork"));
                        classWorkData.setProxyStatus(jsonChildNode1.getString("ProxyStatus"));

                        classWorkDatas.add(classWorkData);
                    }
                    classWorkModel.setClassWorkDatas(classWorkDatas);
                    result.add(classWorkModel);
                }

            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<ResultModel> parseUnitTestJson(String responseString) {
        ArrayList<ResultModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            ResultModel resultModel = null;

            if (data_load_basket.toString().equals("True")) {


                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                for (int a = 0; a < jsonMainNode.length(); a++) {
                    resultModel = new ResultModel();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(a);
                    resultModel.setTestName(jsonChildNode.getString("TestName"));
                    resultModel.setTotal_Marks(jsonChildNode.getString("Total Marks"));
                    resultModel.setTotalMarksGained(jsonChildNode.getString("Total MarksGained"));
                    resultModel.setTotalPercentage(jsonChildNode.getString("Total Percentage"));

                    ResultModel.Data data = null;
                    ArrayList<ResultModel.Data> dataArrayList = new ArrayList<>();
                    JSONArray jsonChildMainNode = jsonChildNode.optJSONArray("Data");
                    for (int i = 0; i < jsonChildMainNode.length(); i++) {
                        data = resultModel.new Data();
                        JSONObject jsonChildNode1 = jsonChildMainNode.getJSONObject(i);
                        data.setSubjectName(jsonChildNode1.getString("SubjectName"));
                        data.setTestMark(jsonChildNode1.getString("TestMark"));
                        data.setMarkGained(jsonChildNode1.getString("MarkGained"));
                        data.setPercentage(jsonChildNode1.getString("Percentage"));
                        data.setDate(jsonChildNode1.getString("Date"));
                        dataArrayList.add(data);
                    }
                    resultModel.setDataArrayList(dataArrayList);
                    result.add(resultModel);
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public static ArrayList<PaymentLedgerModel> parsePaymentLedgerJson(String responseString) {
        ArrayList<PaymentLedgerModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            PaymentLedgerModel paymentLedgerModel = null;

            if (data_load_basket.toString().equals("True")) {


                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                for (int a = 0; a < jsonMainNode.length(); a++) {
                    paymentLedgerModel = new PaymentLedgerModel();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(a);
                    paymentLedgerModel.setPaymentType(jsonChildNode.getString("Payment Type"));
                    paymentLedgerModel.setDate(jsonChildNode.getString("Date"));
                    paymentLedgerModel.setTerm(jsonChildNode.getString("Term"));
                    paymentLedgerModel.setAmount(jsonChildNode.getString("Amount"));
                    paymentLedgerModel.setURL(jsonChildNode.getString("URL"));

                    result.add(paymentLedgerModel);
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
//    public static ArrayList<PaymentLedgerModel> parsePaymentLedgerJson(String responseString) {
//        ArrayList<PaymentLedgerModel> result = new ArrayList<>();
//
//        try {
//            JSONObject reader = new JSONObject(responseString);
//            String data_load_basket = reader.getString("Success");
//            PaymentLedgerModel paymentLedgerModel = null;
//
//            if (data_load_basket.toString().equals("True")) {
//
//
//                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
//                for (int a = 0; a < jsonMainNode.length(); a++) {
//                    paymentLedgerModel = new PaymentLedgerModel();
//                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(a);
//                    paymentLedgerModel.setPayDate(jsonChildNode.getString("PayDate"));
//                    paymentLedgerModel.setPaid(jsonChildNode.getString("Paid"));
//
//                    PaymentLedgerModel.Data data = null;
//                    ArrayList<PaymentLedgerModel.Data> dataArrayList = new ArrayList<>();
//                    JSONArray jsonChildMainNode = jsonChildNode.optJSONArray("Data");
//                    for (int i = 0; i < jsonChildMainNode.length(); i++) {
//                        data = paymentLedgerModel.new Data();
//                        JSONObject jsonChildNode1 = jsonChildMainNode.getJSONObject(i);
//                        data.setTerm(jsonChildNode1.getString("Term"));
//                        data.setTermDetail(jsonChildNode1.getString("TermDetail"));
//                        data.setGRNO(jsonChildNode1.getString("GRNO"));
//                        data.setPayMode(jsonChildNode1.getString("PayMode"));
//                        data.setPaidFee(jsonChildNode1.getString("PaidFee"));
//                        data.setReceiptNo(jsonChildNode1.getString("ReceiptNo"));
////                        data.setAdmissionFee(jsonChildNode1.getString("AdmissionFee"));
//                        data.setCautionFee(jsonChildNode1.getString("CautionFee"));
//                        data.setPreviousFees(jsonChildNode1.getString("PreviousFees"));
//                        data.setTuitionFee(jsonChildNode1.getString("TuitionFee"));
//                        data.setTransport(jsonChildNode1.getString("Transport"));
//                        data.setImprestFee(jsonChildNode1.getString("ImprestFee"));
//                        data.setLatesFee(jsonChildNode1.getString("LatesFee"));
//                        data.setDiscountFee(jsonChildNode1.getString("DiscountFee"));
//                        data.setPaidFee(jsonChildNode1.getString("PayPaidFees"));
//                        data.setCurrentOutstandingFees(jsonChildNode1.getString("CurrentOutstandingFees"));
//                        data.setBankName(jsonChildNode1.getString("Bank Name"));
//                        data.setChequeNumber(jsonChildNode1.getString("Cheque Number"));
//                        dataArrayList.add(data);
//                    }
//                    paymentLedgerModel.setDataArrayList(dataArrayList);
//                    result.add(paymentLedgerModel);
//                }
//            } else {
//                //invalid login
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }

    public static ArrayList<HomeWorkModel> parseStudHomeworkJson(String responseString) {
        ArrayList<HomeWorkModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            if (data_load_basket.toString().equals("True")) {
                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                HomeWorkModel homeWorkModel = null;

                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    homeWorkModel = new HomeWorkModel();
                    homeWorkModel.setHomeWorkDate(jsonChildNode.getString("HomeWorkDate"));

                    HomeWorkModel.HomeWorkData homeWorkData = null;
                    ArrayList<HomeWorkModel.HomeWorkData> homeWorkDatas = new ArrayList<>();
                    JSONArray jsonMainNode1 = jsonChildNode.optJSONArray("Data");
                    for (int j = 0; j < jsonMainNode1.length(); j++) {
                        JSONObject jsonChildNode1 = jsonMainNode1.getJSONObject(j);
                        homeWorkData = homeWorkModel.new HomeWorkData();
                        homeWorkData.setSubject(jsonChildNode1.getString("Subject"));
                        homeWorkData.setHomework(jsonChildNode1.getString("HomeWork"));
                        homeWorkData.setChapterName(jsonChildNode1.getString("ProxyStatus"));
                        homeWorkData.setFont(jsonChildNode1.getString("Font"));
                        homeWorkData.setHomeWorkStatus(jsonChildNode1.getString("HomeWorkStatus"));
//                        homeWorkData.setObjective(jsonChildNode1.getString("Objective"));
//                        homeWorkData.setAssessmentQue(jsonChildNode1.getString("AssessmentQue"));

                        homeWorkDatas.add(homeWorkData);
                    }
                    homeWorkModel.setHomeWorkDatas(homeWorkDatas);
                    result.add(homeWorkModel);
                }

            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<AttendanceModel> parseAttendanceJson(String responseString) {
        ArrayList<AttendanceModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            AttendanceModel attendanceModel = new AttendanceModel();


            if (data_load_basket.toString().equals("True")) {
                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                JSONArray jsonMainNode1=reader.optJSONArray("HolidayArray");
                AttendanceModel.Attendance attendance = null;
                ArrayList<AttendanceModel.Attendance> attendances = new ArrayList<>();
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    attendance = attendanceModel.new Attendance();
                    attendance.setAttendanceDate(jsonChildNode.getString("AttendanceDate"));
                    attendance.setComment(jsonChildNode.getString("Comment"));
                    attendance.setAttendenceStatus(jsonChildNode.getString("AttendenceStatus"));

                    attendances.add(attendance);
                }
                attendanceModel.setEventsList(attendances);
                result.add(attendanceModel);
                attendanceModel.setTotalAbsent(reader.getString("TotalAbsent"));
                attendanceModel.setTotalPresent(reader.getString("TotalPresent"));
                attendanceModel.setHolidayCount(reader.getString("HolidayCount"));
                attendanceModel.setTotalHolidayCount(reader.getString("TotalHolidayCount"));

                AttendanceModel.HolidayAtt holidayatt = null;
                ArrayList<AttendanceModel.HolidayAtt> holidayatts = new ArrayList<>();
                for (int i = 0; i < jsonMainNode1.length(); i++) {
                    JSONObject jsonChildNode1 = jsonMainNode1.getJSONObject(i);
                    holidayatt = attendanceModel.new HolidayAtt();
                    holidayatt.setHolidayName(jsonChildNode1.getString("HolidayName"));
                    holidayatt.setDate(jsonChildNode1.getString("Date"));
                    holidayatt.setCount(jsonChildNode1.getString("Count"));

                    holidayatts.add(holidayatt);
                }
                attendanceModel.setHolidayAtt(holidayatts);
                result.add(attendanceModel);
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<TimetableModel> parseTimeTableJson(String responseString) {
        ArrayList<TimetableModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            TimetableModel timetableModel = new TimetableModel();

            if (data_load_basket.toString().equals("True")) {
                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                TimetableModel.Timetable timetable = null;
                ArrayList<TimetableModel.Timetable> timetables = new ArrayList<>();
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    timetable = timetableModel.new Timetable();
                    timetable.setDay(jsonChildNode.getString("Day"));

                    JSONArray jsonMainNode1 = jsonChildNode.optJSONArray("Data");
                    TimetableModel.Timetable.TimetableData timetableData = null;
                    ArrayList<TimetableModel.Timetable.TimetableData> timetablesData = new ArrayList<>();
                    for (int j = 0; j < jsonMainNode1.length(); j++) {
                        JSONObject jsonChildNode1 = jsonMainNode1.getJSONObject(j);
                        timetableData = timetable.new TimetableData();
                        timetableData.setLecture(jsonChildNode1.getString("Lecture"));
                        timetableData.setSubject(jsonChildNode1.getString("Subject"));
                        timetableData.setTeacher(jsonChildNode1.getString("Teacher"));

                        timetablesData.add(timetableData);
                    }
                    timetable.setTimetableDatas(timetablesData);
                    timetables.add(timetable);
                }
                timetableModel.setTimetables(timetables);
                result.add(timetableModel);
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<TermModel> getTermData(String responseString) {
        ArrayList<TermModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            if (data_load_basket.toString().equals("True")) {
                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");

                TermModel termModel = null;
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    termModel = new TermModel();
                    termModel.setTermId(jsonChildNode.getString("TermId"));
                    termModel.setTerm(jsonChildNode.getString("Term"));

                    result.add(termModel);
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<CircularModel> parseCircularJson(String responseString) {
        ArrayList<CircularModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            if (data_load_basket.toString().equals("True")) {
                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");

                CircularModel circularModel = null;
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    circularModel = new CircularModel();
                    circularModel.setDate(jsonChildNode.getString("Date"));
                    circularModel.setSubject(jsonChildNode.getString("Subject"));
                    circularModel.setDiscription(jsonChildNode.getString("Discription"));
                    circularModel.setCircularPDF(jsonChildNode.getString("CircularPDF"));

                    result.add(circularModel);
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<ReportCardModel> parseReportCardJson(String responseString) {
        ArrayList<ReportCardModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            ReportCardModel reportCardModel = null;

            if (data_load_basket.toString().equals("True")) {
                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                for (int a = 0; a < jsonMainNode.length(); a++) {
                    reportCardModel = new ReportCardModel();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(a);
                    reportCardModel.setURL(jsonChildNode.getString("URL"));
                    result.add(reportCardModel);
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<ReportCardModel> parseReportCardPermissionJson(String responseString) {
        ArrayList<ReportCardModel> result = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(responseString);
            String data_load_basket = reader.getString("Success");
            ReportCardModel reportCardModel = null;

            if (data_load_basket.toString().equals("True")) {
                JSONArray jsonMainNode = reader.optJSONArray("FinalArray");
                for (int a = 0; a < jsonMainNode.length(); a++) {
                    reportCardModel = new ReportCardModel();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(a);
                    reportCardModel.setStatus(jsonChildNode.getString("Status"));
                    result.add(reportCardModel);
                }
            } else {
                //invalid login
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
