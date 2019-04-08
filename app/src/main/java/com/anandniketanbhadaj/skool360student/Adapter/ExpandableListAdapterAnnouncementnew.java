package com.anandniketanbhadaj.skool360student.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.ExamFinalArray;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterAnnouncementnew extends BaseExpandableListAdapter {

    File filepdf;
    String file1;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<ExamFinalArray>> _listDataChild;
    private ProgressDialog progressDialog = null;

    public ExpandableListAdapterAnnouncementnew(Context context, List<String> listDataHeader,
                                                HashMap<String, ArrayList<ExamFinalArray>> listDataChild) {

        _context = context;
        _listDataChild = listDataChild;
        _listDataHeader = listDataHeader;
    }

    @Override
    public ArrayList<ExamFinalArray> getChild(int groupPosition, int childPosititon) {
        return _listDataChild.get(_listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, final ViewGroup parent) {

        final ArrayList<ExamFinalArray> childData = getChild(groupPosition, childPosition);
        final TextView show_file;
        WebView description_title_txt;

        if (convertView == null) {
            LayoutInflater infalInflater = LayoutInflater.from(_context);
            convertView = infalInflater.inflate(R.layout.announcment_listitem1, null);
        }
        description_title_txt = (WebView) convertView.findViewById(R.id.description_title_txt);
        show_file = (TextView) convertView.findViewById(R.id.show_file);
        description_title_txt.loadData(childData.get(childPosition).getAnnoucementDescription(), "text/html", "UTF-8");
        WebSettings webSettings = description_title_txt.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.SMALLER);

        if (childData.get(childPosition).getAnnoucementDescription().equalsIgnoreCase("")) {
            show_file.setVisibility(View.GONE);
            description_title_txt.setVisibility(View.GONE);
        } else {
            show_file.setVisibility(View.GONE);
            description_title_txt.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View
            convertView, ViewGroup parent) {
        String[] headerTemp = getGroup(groupPosition).toString().split("\\|");

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.announcement_groupitem, null);
        }
        TextView txtAnnText, txtDate;
        LinearLayout llHeaderRow;
        ImageView imgBulletCircular;

        txtAnnText = (TextView) convertView.findViewById(R.id.txtAnnText);
        txtDate = (TextView) convertView.findViewById(R.id.txtDate);
        llHeaderRow = (LinearLayout) convertView.findViewById(R.id.llHeaderRow);
        imgBulletCircular = (ImageView) convertView.findViewById(R.id.imgBulletCircular);

        txtAnnText.setTypeface(null, Typeface.BOLD);
        txtAnnText.setText(headerTemp[1]);

        txtDate.setTypeface(null, Typeface.BOLD);
        txtDate.setText(headerTemp[0]);

        if (isExpanded) {
            txtAnnText.setBackgroundColor(Color.parseColor("#86c129"));
            txtDate.setBackgroundColor(Color.parseColor("#86c129"));
            imgBulletCircular.setImageResource(R.drawable.green_bulletpointwithgreenline);

            if (headerTemp[2].equalsIgnoreCase("")) {
                String extStorageDirectory = "";
                String saveFilePath = null;
                long currentTime = Calendar.getInstance().getTimeInMillis();
                Log.d("date", "" + currentTime);
                Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
                Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();
                final String fileName = headerTemp[3].substring(headerTemp[3].lastIndexOf('/') + 1);
                if (isSDSupportedDevice && isSDPresent) {
                    // yes SD-card is present
                    Utility.ping(_context, "present");
                    extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    saveFilePath = String.valueOf(new File(extStorageDirectory, Utility.parentFolderName + "/" + Utility.childAnnouncementFolderName + "/" + fileName).getPath());

                } else {
                    // Sorry
//                            Utility.ping(mContext, "notpresent");
//
                    File cDir = _context.getExternalFilesDir(null);
                    saveFilePath = String.valueOf(new File(cDir.getPath() + "/" + fileName));
                    Log.d("path", saveFilePath);

                }
//
                Log.d("path", extStorageDirectory);

                String fileURL =AppConfiguration.GALLARY_LIVE+headerTemp[3];
                Log.d("URL", fileURL);
                if (Utility.isNetworkConnected(_context)) {
                    progressDialog = new ProgressDialog(_context);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Ion.with(_context)
                            .load(fileURL)  // download url
                            .write(new File(saveFilePath))  // File no path
                            .setCallback(new FutureCallback<File>() {
                                //                                    @Override
                                public void onCompleted(Exception e, File file) {
                                    progressDialog.dismiss();
                                    if (file != null) {
                                        if (file.length() > 0) {
                                            //Utility.ping(_context, "Download complete.");
                                            file1 = file.getPath();
                                            filepdf = file.getAbsoluteFile();
                                            Log.d("file11", "" + filepdf);
                                            //  description_title_txt.setText(String.valueOf(filepdf));
                                            //  show_file.setVisibility(View.VISIBLE);

                                            if (file1 != null) {

                                                File file2 = new File(file1);
                                                Log.d("DownloadfilePath", "File to download = " + String.valueOf(file2));

                                                Intent intent;

                                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                                                    Uri uri = FileProvider.getUriForFile(_context, _context.getPackageName(), file2);
                                                    intent = new Intent(Intent.ACTION_VIEW);
                                                    intent.setData(uri);
                                                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                    _context.startActivity(intent);
                                                }
//                                                }
                                                else {
//
                                                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                                                    String ext = file2.getName().substring(file2.getName().indexOf(".") + 1);
                                                    String type = mime.getMimeTypeFromExtension(ext);
                                                    Log.d("type", type);
                                                    intent = new Intent(Intent.ACTION_VIEW);
                                                    intent.setDataAndType(Uri.fromFile(file), type);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                    _context.startActivity(intent);
                                                }
                                            }
                                        } else {
                                            Utility.ping(_context, "Something error");
                                            // show_file.setVisibility(View.GONE);
                                        }
                                    }
                                }

                            });
                } else {
                    Utility.ping(_context, "Network not available");
                }

            }
        } else {
            txtAnnText.setBackgroundColor(Color.parseColor("#1791d8"));
            txtDate.setBackgroundColor(Color.parseColor("#1791d8"));
            imgBulletCircular.setImageResource(R.drawable.blue_bulletpoint_withline);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}


