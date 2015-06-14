package org.hq.hdtzsc.goods;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.dd.CircularProgressButton;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.base.BaseActivity;
import org.hq.hdtzsc.bean.Goods;
import org.hq.hdtzsc.bean.UserBean;
import org.hq.hdtzsc.bean.goodsSort;
import org.hq.hdtzsc.bean.goodsSortChild;
import org.hq.hdtzsc.sort.ChildSortAdapter;
import org.hq.hdtzsc.utils.ToastFactory;
import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;
import org.rc.rclibrary.widget.NoScrollGridView;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-11 18:22
 */
public class UploadGoodsActivity extends BaseActivity implements CropHandler{

    /**
     * 商品名称输入栏
     */
    private MaterialEditText metGoodsName;
    /**
     * 商品价格输入栏
     */
    private MaterialEditText metGoodsPrice;
    /**
     * 商品分类输入栏
     */
    private MaterialEditText metGetGoodsSort;
    /**
     * 商品分类
     */
    private NoScrollGridView gvGoodsSort;
    /**
     * 商品子分类数据适配器
     */
    private ChildSortAdapter childSortAdapter;
    /**
     * 商品父分类数据适配器
     */
    private ParentSortAdapter parentSortAdapter;
    /**
     * 确认上传按钮
     */
    private ButtonRectangle btnConfirmUpload;
    /**
     * 上传商品图片1按钮
     */
    private CircularProgressButton btnGoodsImage1;
    /**
     * 上传商品图片2按钮
     */
    private CircularProgressButton btnGoodsImage2;
    /**
     * 上传商品图片3按钮
     */
    private CircularProgressButton btnGoodsImage3;
    /**
     * 上传商品图片4按钮
     */
    private CircularProgressButton btnGoodsImage4;
    /**
     * 加载Dialog
     */
    private SweetAlertDialog pDialog;
    /**
     * 上传Dialog
     */
    private SweetAlertDialog uploadDialog;
    private CropParams cropParams;
    /**
     * 上传信息成功后保存的商品ID
     */
    private String strGoodsId;
    private goodsSort goodsSort;
    private goodsSortChild goodsSortChild;
    /**
     * 需要修改信息的商品
     */
    private Goods mGoods;


    private final Uri IMAGE1_URI = Uri.fromFile(Environment.getExternalStorageDirectory())
            .buildUpon().appendPath("image1.jpg").build();

    private final Uri IMAGE2_URI = Uri.fromFile(Environment.getExternalStorageDirectory())
            .buildUpon().appendPath("image2.jpg").build();

    private final Uri IMAGE3_URI = Uri.fromFile(Environment.getExternalStorageDirectory())
            .buildUpon().appendPath("image3.jpg").build();

    private final Uri IMAGE4_URI = Uri.fromFile(Environment.getExternalStorageDirectory())
            .buildUpon().appendPath("image4.jpg").build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_goods);
        setMyTitle(R.string.upload_goods);

        init();

        uploadDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                clearImage();
                Intent intent = CropHelper.buildCropFromGalleryIntent(cropParams);
                startActivityForResult(intent, CropHelper.REQUEST_CROP);
            }
        }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                clearImage();
                Intent intent = CropHelper.buildCaptureIntent(cropParams.uri);
                startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
            }
        });

        btnGoodsImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCrop(IMAGE1_URI);
            }
        });

        btnGoodsImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCrop(IMAGE2_URI);
            }
        });

        btnGoodsImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCrop(IMAGE3_URI);
            }
        });

        btnGoodsImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCrop(IMAGE4_URI);
            }
        });

        //点击输入分类栏获取分类信息
        metGetGoodsSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestGoodsSort();
            }
        });

        gvGoodsSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (gvGoodsSort.getAdapter() instanceof ParentSortAdapter) {
                    if (parentSortAdapter.getData().get(position).getHasChild()) {
                        requestChildGoodsSort(position);
                    } else {
                        metGetGoodsSort.setText(parentSortAdapter.getData().get(position)
                                .getSortName());
                        goodsSort = parentSortAdapter.getData().get(position);
                        gvGoodsSort.setAdapter(null);
                    }
                } else {
                    metGetGoodsSort.setText(childSortAdapter.getData().get(position)
                            .getGoodsSortChildName());
                    goodsSortChild = childSortAdapter.getData().get(position);
                    gvGoodsSort.setAdapter(null);
                }
            }
        });

        btnConfirmUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInput()) {
                    return;
                }
                uploadGoodsInfo();
            }
        });
    }

    private void init() {
        metGoodsName      = (MaterialEditText) findViewById(R.id.metGoodsName);
        metGoodsPrice     = (MaterialEditText) findViewById(R.id.metGoodsPrice);
        metGetGoodsSort   = (MaterialEditText) findViewById(R.id.metGetGoodsSort);
        gvGoodsSort       = (NoScrollGridView) findViewById(R.id.gvGoodsSort);
        btnGoodsImage1    = (CircularProgressButton) findViewById(R.id.btnGoodsImage1);
        btnGoodsImage2    = (CircularProgressButton) findViewById(R.id.btnGoodsImage2);
        btnGoodsImage3    = (CircularProgressButton) findViewById(R.id.btnGoodsImage3);
        btnGoodsImage4    = (CircularProgressButton) findViewById(R.id.btnGoodsImage4);
        parentSortAdapter = new ParentSortAdapter(this, R.layout.item_sort_list_child);
        childSortAdapter  = new ChildSortAdapter(this, R.layout.item_sort_list_child);
        btnConfirmUpload  = (ButtonRectangle) findViewById(R.id.btnConfirmUpload);

        uploadDialog      = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);

        mGoods = (Goods) getIntent().getSerializableExtra(GoodsDetailActivity.GOODS);

        isUpdate();

        //初始化裁剪图片配置
        cropParams = new CropParams();
        cropParams.outputX = 480;
        cropParams.outputY = 800;

        uploadDialog.setTitleText("").setContentText("选择获取图片方式")
                .setConfirmText("打开相册").setCancelText("打开相机").setCancelable(true);
    }

    /**
     * 判断是否是编辑商品模式
     */
    private void isUpdate() {
        if (mGoods != null) {
            strGoodsId     = mGoods.getObjectId();
            goodsSortChild = mGoods.getGoodsSort();
            goodsSortChild.setGoodsSortChildName(mGoods.getSortName());

            metGoodsName.setText(mGoods.getGoodsName());
            metGoodsPrice.setText(String.valueOf(mGoods.getGoodsPrice()));
            metGetGoodsSort.setText(mGoods.getSortName());
        }
    }

    /**
     * 打开裁剪图片界面
     * @param uri 要裁剪的图片的Uri
     */
    private void goToCrop(Uri uri) {
        if (strGoodsId == null) {
            ToastFactory.pleaseUploadGoodsInfo(this);
            return;
        }
        cropParams.uri = uri;
        uploadDialog.show();
    }

    /**
     * 清理图片缓存
     */
    private void clearImage() {
        if (btnGoodsImage1.getProgress() == 100) {
            CropHelper.clearCachedCropFile(IMAGE1_URI);
        }
        if (btnGoodsImage2.getProgress() == 100) {
            CropHelper.clearCachedCropFile(IMAGE2_URI);
        }
        if (btnGoodsImage3.getProgress() == 100) {
            CropHelper.clearCachedCropFile(IMAGE3_URI);
        }
        if (btnGoodsImage4.getProgress() == 100) {
            CropHelper.clearCachedCropFile(IMAGE4_URI);
        }
    }

    private boolean checkInput() {
        if (metGoodsName.isCharactersCountValid() && metGoodsPrice.isCharactersCountValid() &&
                metGetGoodsSort.isCharactersCountValid()) {
            metGoodsName.setError(null);
            metGoodsPrice.setError(null);
            metGetGoodsSort.setError(null);
            return true;
        }
        if (!metGoodsName.isCharactersCountValid()) {
            metGoodsName.setError(metGoodsName.getHint() + getString(R.string.error_must_not_empty));
        }
        if (!metGoodsPrice.isCharactersCountValid()) {
            metGoodsPrice.setError(metGoodsPrice.getHint() + getString(R.string.error_must_not_empty));
        }
        if (!metGetGoodsSort.isCharactersCountValid()) {
            metGetGoodsSort.setError(metGetGoodsSort.getHint() + getString(R.string.error_must_not_empty));
        }
        return false;
    }

    private void uploadGoodsInfo() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(getString(R.string.loading));
        pDialog.setCancelable(false);
        pDialog.show();

        gvGoodsSort.setAdapter(null);

        final Goods goods = new Goods();
        goods.setGoodsName(metGoodsName.getText().toString());
        goods.setGoodsPrice(Float.valueOf(metGoodsPrice.getText().toString()));
        goods.setGoodsSort(goodsSortChild);
        goods.setSortName(goodsSortChild.getGoodsSortChildName());
        goods.setGoodsParentSort(goodsSort);
        goods.setUserName(BmobUser.getCurrentUser(this, UserBean.class));

        if (strGoodsId == null) {
            goods.save(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    pDialog.dismiss();
                    strGoodsId = goods.getObjectId();
                    ToastFactory.uploadGoodsInfoSuccess(UploadGoodsActivity.this);
                }

                @Override
                public void onFailure(int i, String s) {
                    pDialog.dismiss();
                    ToastFactory.uploadGoodsInfoFail(UploadGoodsActivity.this);
                }
            });
        } else {
            goods.setObjectId(strGoodsId);
            goods.update(this, new UpdateListener() {
                @Override
                public void onSuccess() {
                    pDialog.dismiss();
                    ToastFactory.updateGoodsInfoSuccess(UploadGoodsActivity.this);
                }

                @Override
                public void onFailure(int i, String s) {
                    pDialog.dismiss();
                    ToastFactory.updateGoodsInfoFail(UploadGoodsActivity.this);
                }
            });
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        measureSortWidth();
    }

    private void measureSortWidth() {
        int width;
        width = gvGoodsSort.getMeasuredWidth() - (gvGoodsSort.getNumColumns() + 1) *
                getResources().getDimensionPixelOffset(R.dimen.child_sort_hor_spacing);
        gvGoodsSort.setColumnWidth(width);
    }

    private void requestGoodsSort() {
        BmobQuery<goodsSort> bmobQuery = new BmobQuery<>();
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        bmobQuery.setMaxCacheAge(5 * 60 * 1000);
        bmobQuery.setLimit(50);
        bmobQuery.order("-sortName");
        bmobQuery.findObjects(this, new FindListener<goodsSort>() {
            @Override
            public void onSuccess(List<goodsSort> list) {
                parentSortAdapter.refresh(list);
                gvGoodsSort.setAdapter(parentSortAdapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 根据父分类查找其所有子分类
     * @param position 父分类列表索引
     */
    private void requestChildGoodsSort(int position) {
        BmobQuery<goodsSortChild> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("parentSort", parentSortAdapter.getData().get(position)
                .getObjectId());
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        bmobQuery.setMaxCacheAge(5 * 60 * 1000);
        bmobQuery.setLimit(50);
        bmobQuery.findObjects(this, new FindListener<goodsSortChild>() {
            @Override
            public void onSuccess(List<goodsSortChild> list) {
                childSortAdapter.refresh(list);
                gvGoodsSort.setAdapter(childSortAdapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void onPhotoCropped(final Uri uri) {
        final CircularProgressButton btn;
        final Goods goods = new Goods();
        goods.setObjectId(strGoodsId);
        if (uri == IMAGE1_URI) {
            btn = btnGoodsImage1;
        }else if (uri == IMAGE2_URI) {
            btn = btnGoodsImage2;
        }else if (uri == IMAGE3_URI) {
            btn = btnGoodsImage3;
        }else if (uri == IMAGE4_URI) {
            btn = btnGoodsImage4;
        } else {
            btn = new CircularProgressButton(this);
        }
        btn.setProgress(1);
        btn.setIndeterminateProgressMode(true);
        BmobProFile.getInstance(this).upload(uri.getPath(), new UploadListener() {
            @Override
            public void onSuccess(String s, String s1) {
                String msg = null;
                try {
                    ApplicationInfo appInfo = getPackageManager()
                            .getApplicationInfo(getPackageName(),
                                    PackageManager.GET_META_DATA);
                    msg = appInfo.metaData.getString("access_key");
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                String URL = BmobProFile.getInstance(UploadGoodsActivity.this).signURL(s, s1, msg, 0, null);
                if (uri == IMAGE1_URI) {
                    goods.setGoodsImage1(URL);
                }else if (uri == IMAGE2_URI) {
                    goods.setGoodsImage2(URL);
                }else if (uri == IMAGE3_URI) {
                    goods.setGoodsImage3(URL);
                }else if (uri == IMAGE4_URI) {
                    goods.setGoodsImage4(URL);
                }

                goods.update(UploadGoodsActivity.this, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        btn.setProgress(100);
                        ToastFactory.uploadGoodsImageSuccess(UploadGoodsActivity.this);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        btn.setProgress(-1);
                        ToastFactory.uploadGoodsImageFail(UploadGoodsActivity.this);
                    }
                });

            }

            @Override
            public void onProgress(int i) {

            }

            @Override
            public void onError(int i, String s) {
                btn.setProgress(-1);
                ToastFactory.uploadGoodsImageFail(UploadGoodsActivity.this);
            }
        });
    }

    @Override
    public void onCropCancel() {

    }

    @Override
    public void onCropFailed(String s) {

    }

    @Override
    public CropParams getCropParams() {
        return cropParams;
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if (getCropParams() != null)
            CropHelper.clearCachedCropFile(getCropParams().uri);
        super.onDestroy();
    }
}
