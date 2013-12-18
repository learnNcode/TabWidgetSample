/*
 * Copyright 2013 - learnNcode (learnncode@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.learnncode.tabsample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.learnncode.tabsample.fragment.TabOneFrgment;
import com.learnncode.tabsample.fragment.TabTwoFrgment;

public class BaseActivity extends FragmentActivity {

	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);

		mTabHost  = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
		mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(getResources().getString(R.string.tab_one)), TabOneFrgment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(getResources().getString(R.string.tab_two)), TabTwoFrgment.class, null);

		mTabHost.setCurrentTabByTag("tag2");

		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {

			TextView textView = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
			if(textView.getLayoutParams() instanceof RelativeLayout.LayoutParams){

				RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) textView.getLayoutParams();
				params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				params.addRule(RelativeLayout.CENTER_VERTICAL);
				params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
				params.width  = RelativeLayout.LayoutParams.WRAP_CONTENT;
				mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title).setLayoutParams(params);

			}else if(textView.getLayoutParams() instanceof LinearLayout.LayoutParams){
				LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) textView.getLayoutParams();
				params.gravity = Gravity.CENTER;
				mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title).setLayoutParams(params);
			}
		}

		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {

				android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
				TabOneFrgment tabOneFrgment = (TabOneFrgment) fragmentManager.findFragmentByTag("tab1");
				TabTwoFrgment tabTwoFrgment = (TabTwoFrgment) fragmentManager.findFragmentByTag("tab2");
				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


				if(tabId.equalsIgnoreCase("tab1")){
					if(tabOneFrgment != null){   
						if(tabTwoFrgment != null){
							fragmentTransaction.hide(tabTwoFrgment);
						}
						fragmentTransaction.show(tabOneFrgment); 
					}
				}else{ 
					if(tabTwoFrgment != null){
						if(tabOneFrgment != null){
							fragmentTransaction.hide(tabOneFrgment);
						}
						fragmentTransaction.show(tabTwoFrgment);   
					}
				}
				fragmentTransaction.commit();         
			}
		});
	}
}
