package com.wxue1.game2048;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Path.FillType;
import android.support.v4.view.GestureDetectorCompat;
import android.text.BoringLayout;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	private GameBoard board;
	private GestureDetectorCompat mDetector;
	private int result = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RelativeLayout main = (RelativeLayout)findViewById(R.id.main);
		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
		board = new GameBoard(this);
		RelativeLayout.LayoutParams para = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,0);
		para.addRule(RelativeLayout.CENTER_IN_PARENT);
		board.setLayoutParams(para);
		((Button)findViewById(R.id.restart)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				board.init();
				result = 0;
			}
		});
		
		main.addView(board);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		this.mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	public class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			int move_thresh = board.getWidth()/10;
			if(result == 0){
				float diffX = e2.getX()-e1.getX();
				float diffY = e2.getY()-e1.getY();
				//GameBoard board = (GameBoard)findViewById(R.id.gameBoard);
				if(Math.abs(diffX)>Math.abs(diffY)){
					if(diffX>move_thresh){
						board.moveRight();
					}else if(diffX<-move_thresh){
						board.moveLeft();
					}
				}else{
					if(diffY>move_thresh){
						board.moveDown();
					}else if(diffY<-move_thresh){
						board.moveUp();
					}
				}
			}
			
			((TextView)findViewById(R.id.bestscore_value)).setText(board.getBest());
			
			if(result == 1){
				((TextView)findViewById(R.id.bestscore_value)).setText("中华人民共和国成立了！");
			}
			if(result == -1){
				((TextView)findViewById(R.id.bestscore_value)).setText("大侠请重新来过！");
			}
			return true;
		}
	}

}
