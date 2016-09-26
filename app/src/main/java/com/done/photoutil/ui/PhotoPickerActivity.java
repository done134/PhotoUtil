package com.done.photoutil.ui;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.done.photoutil.R;
import com.done.photoutil.adapter.ImageListAdapter;
import com.done.photoutil.common.ImageFolder;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PhotoPickerActivity extends AppCompatActivity {

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();
    /**
     * 总图片数
     */
    int totalCount = 0;
    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFolder> mImageFolders = new ArrayList<ImageFolder>();

    RecyclerView imageList;
    ImageListAdapter imageAdapter;
    ProgressDialog mPressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker);

        initView();

        getImages();
    }

    private void initView() {
        imageList = (RecyclerView) findViewById(R.id.image_list);
        imageAdapter = new ImageListAdapter(this);
       /* imageAdapter.setOnItemClickListener(new ImageListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });*/
    }


    /**
     * 利用ContentProvider 扫描手机中的图片，此方法运行在子线程中， 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    public void getImages() {
        //判断SD卡是否正常挂载
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        mPressDialog = new ProgressDialog(this);
        mPressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String firstImage = null;
                Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = PhotoPickerActivity.this.getContentResolver();

                //查询jpeg、png和gif的图片
                Cursor mCursor = mContentResolver.query(imageUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png", "image/gif"},
                        MediaStore.Images.Media.DATE_MODIFIED);
                Log.e("TAG", String.valueOf(mCursor.getCount()));

                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    Log.e("TAG", path);
                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    //获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;

                    String dirPath = parentFile.getPath();
                    ImageFolder imageFolder = new ImageFolder();
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~)
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    }else {
                        mDirPaths.add(dirPath);
                        imageFolder.setFirstImagePath(path);
                        imageFolder.setFolderPath(dirPath);
                    }

                    if(parentFile.list()==null)continue;
                    int picSize = parentFile.list(new FilenameFilter()
                    {
                        @Override
                        public boolean accept(File dir, String filename)
                        {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg")
                                    || filename.endsWith(".gif"))
                                return true;
                            return false;
                        }
                    }).length;
                    totalCount += picSize;
                    imageFolder.setImageSum(picSize);
                    mImageFolders.add(imageFolder);
                }
                mCursor.close();
                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
            }
        }).start();
        mHandler.sendMessage(Message.obtain());
    }

    //图片扫描完成后用来处理的handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            data2View();
            if (mPressDialog != null && mPressDialog.isShowing()) {
                mPressDialog.dismiss();
            }
        }
    };


    /**
     * 将数据显示在view上
     */
    private void data2View() {
        //Do Nothing Now.
    }



}
