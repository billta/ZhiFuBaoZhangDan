package com.ld.zhifubaozhangdan;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TaskModelActivity extends Activity implements UpLoadPinnedHeaderListView.OnLoadingMoreLinstener{
//OnLoadingMoreLinstener
	private TextView Header1Text;
	private ImageView Header1Img;

	private List<TaskModelBean> taskList;

	List<String> group;

	String child[] = { "研发组 模板1", "研发组 模板1", "研发组 模板1", "研发组 模板1", "公司 模板2",
			"公司 模板2", "公司 模板2", "公司 模板2", "销售部 模板3", "销售部 模板3", "销售部 模板3",
			"销售部 模板3", "产品部 模板4", "产品部 模板4", "产品部 模板4", "产品部 模板4"};

	TaskModelBean modelBean;

	int sectionNow = 10000;
	int positionNow = 10000;

	//设置自定义listview
	UpLoadPinnedHeaderListView pinnedlistView;
	TestSectionedAdapter sectionedAdapter;

	
	private RelativeLayout moredata;
	private View progressBarView;
	private TextView progressBarTextView;
	private AnimationDrawable loadingAnimation; 
	private boolean isLoading = false;
	private LayoutInflater inflater;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oa_nzl_main_view_taskmodel);
		group = new ArrayList<String>();
		group.add("研发组 模板");
		group.add("公司 模板");
		group.add("销售部 模板");
		group.add("产品部 模板");

		taskList = new ArrayList<TaskModelBean>();

		for (int i = 0; i < group.size(); i++) {
			modelBean = new TaskModelBean();
			modelBean.setGroupName(group.get(i));
			List<String> test = new ArrayList<String>();
			for (int j = 0; j < child.length; j++) {

				if (group.get(i).substring(0, 1)
						.equals(child[j].substring(0, 1))) {
					test.add(child[j]);
					modelBean.setItemName(test);
				}

			}
			taskList.add(modelBean);
		}

		initView();

	}

	public void initView() {

		
		pinnedlistView = (UpLoadPinnedHeaderListView) findViewById(R.id.pinnedListView);
		LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout header1 = (LinearLayout) inflator.inflate(
				R.layout.oa_nzl_main_view_task_model_list_item_view, null);
		Header1Text = (TextView) header1.findViewById(R.id.task_model_text);
		Header1Text.setText("普通任务");

		Header1Img = (ImageView) header1.findViewById(R.id.task_model_img);
		pinnedlistView.addHeaderView(header1);
		
		
		

		inflater = LayoutInflater.from(this);
		moredata = (RelativeLayout) inflater.inflate(R.layout.pay_vip_up_moredata, null);

		progressBarView = (View) moredata
				.findViewById(R.id.loadmore_foot_progressbar);
		progressBarTextView = (TextView) moredata
				.findViewById(R.id.loadmore_foot_text);
		loadingAnimation = (AnimationDrawable) progressBarView.getBackground();
		
		pinnedlistView.addFooterView(moredata);
		
		
		sectionedAdapter = new TestSectionedAdapter(this, taskList, group,Header1Img);
		pinnedlistView.setAdapter(sectionedAdapter);
		
		pinnedlistView.setLoadingMoreListener(this);
		
		Header1Text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Header1Img.setVisibility(View.VISIBLE);
				sectionedAdapter.reference(sectionNow, positionNow);
			}
		});
		
	}

	@Override
	public void OnLoadingMore() {
		// TODO Auto-generated method stub
		progressBarView.setVisibility(View.VISIBLE);
		progressBarTextView.setVisibility(View.VISIBLE);

		loadingAnimation.start();

		if (!isLoading) {
			isLoading = true;
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					
					
						for (int i = 0; i < group.size(); i++) {
							modelBean = new TaskModelBean();
							modelBean.setGroupName(group.get(i));
							List<String> test = new ArrayList<String>();
							for (int j = 0; j < child.length; j++) {

								if (group.get(i).substring(0, 1)
										.equals(child[j].substring(0, 1))) {
									test.add(child[j]);
									modelBean.setItemName(test);
								}

							}
							taskList.add(modelBean);
						
					}
					loadingFinished();
				}
			}, 1200);
		}
	}
	
	public void loadingFinished() {

		if (null != loadingAnimation && loadingAnimation.isRunning()) {
			loadingAnimation.stop();
		}
		progressBarView.setVisibility(View.GONE);
		progressBarTextView.setVisibility(View.GONE);
		isLoading = false;
		sectionedAdapter.notifyDataSetChanged();
	}
}
