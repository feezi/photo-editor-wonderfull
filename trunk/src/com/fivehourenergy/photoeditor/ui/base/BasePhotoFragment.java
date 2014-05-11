package com.fivehourenergy.photoeditor.ui.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fivehourenergy.photoeditor.activity.MainActivity;
import com.fivehourenergy.photoeditor.data.DataController;
import com.fivehourenergy.photoeditor.data.PhotoEditorObserver;
import com.fivehourenergy.photoeditor.data.quickaction.ActionInput;
import com.fivehourenergy.photoeditor.data.quickaction.ActionMenuItem;
import com.fivehourenergy.photoeditor.data.quickaction.QuickActionMenu;
import com.fivehourenergy.photoeditor.data.quickaction.QuickActionMenu.OnActionMenuClickListener;
import com.fivehourenergy.photoeditor.ui.PhotoLibraryAdapter.GridViewType;
import com.fivehourenergy.photoeditor.ui.PhotoLibraryFragment.OnActionItemClick;
import com.fivehourenergy.photoeditor.ui.SlideShowFragment;
import com.fivehourenergy.photoeditor.ui.base.PhotoEditorHeaderBar.OnHeaderBarClickListener;
import com.fivehourenergy.photoeditor.widget.quickaction3d.QuickAction;

public abstract class BasePhotoFragment extends BaseFragment implements OnHeaderBarClickListener,PhotoEditorObserver{

	protected PhotoEditorHeaderBar mHeaderBar;
	
	@Override
	protected View onCreateHeaderView(LayoutInflater inflater,
			ViewGroup container) {
		if(mHeaderBar==null){
			mHeaderBar = new PhotoEditorHeaderBar(this);
		}
		if(getTitle()!=null) mHeaderBar.setTitle(getTitle());
		return mHeaderBar.onCreateHeaderView(inflater, container);
	}
	
	public MainActivity getMainActiviy(){
		return ((MainActivity)getActivity());
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void onUtilityButtonClick(View v) {
		final ActionInput input = new ActionInput();
		input.activity = getActivity();
		input.anchor = v;
		input.menuType = QuickActionMenu.NORMAL_TYPE;
		input.orientation = QuickAction.VERTICAL;
		input.listener = new OnActionMenuClickListener() {
			
			@Override
			public void onActionMenuClick(View v, ActionMenuItem actionMenuItem) {
				switch (actionMenuItem.getAction()) {
				case ACTION_SLIDE_SHOW:
					SlideShowFragment slideFrag = new SlideShowFragment();
					slideFrag.mCurrentIndex = 0;
					getMainActiviy().switchContent(slideFrag, true);
					getMainActiviy().disableSlideMenu();
					break;
				case ACTION_VIEW_GRID_NORMAL:
					DataController.getInstance().setCurrentGridType(GridViewType.NORMAL);
					update();
					break;
				case ACTION_VIEW_GRID_DATE:
					DataController.getInstance().setCurrentGridType(GridViewType.IMAGE_WITH_DATE);
					update();
					break;
				case ACTION_VIEW_CURVER:
					DataController.getInstance().setCurrentGridType(GridViewType.IMAGE_CURVER);
					update();
					break;
				case ACTION_VIEW_STAGGERED:
					DataController.getInstance().setCurrentGridType(GridViewType.STAGGED_LIST);
					update();
					break;
				default:
					break;
				}
			}
		};
		
		QuickActionMenu.getInstance().showActionMenu3D(input);
	}
	
	@Override
	public void onCameraButtonClick(View v) {
		
	}
	
	@Override
	public void onShareButtonClick(View v) {
		
	}
	
	@Override
	public void onShowMenuClick(View v) {
		getMainActiviy().toggleMenu();
		
	}
	
	protected abstract String getTitle();
}
