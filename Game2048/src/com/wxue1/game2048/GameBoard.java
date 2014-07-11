package com.wxue1.game2048;

import java.util.HashMap;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameBoard extends RelativeLayout {
	private GameEngine game = GameEngine.getInstance();
	private RectF[][] rects;
	private TextView[][] cells;
	private RelativeLayout.LayoutParams[][] para;
	private RectF bg;
	private HashMap<String, Integer> colormap;
	private Paint painter;
	
	public GameBoard(Context context){
		this(context,null);
	}
	
	public GameBoard(Context context, AttributeSet attrs){
		this(context,attrs,0);
	}
	
	public GameBoard(Context context, AttributeSet attrs, int defStyle){
		super(context,attrs,defStyle);
		setWillNotDraw(false);
		game.initial();
		
		int degree = game.getDegree();
		
		bg = new RectF(0,0,0,0);
		
		//init rects
		rects = new RectF[degree][degree];
		
		//init cells
		cells = new TextView[degree][degree];
		
		//int para
		para = new RelativeLayout.LayoutParams[degree][degree];
		
		for(int i = 0; i<degree; i++){
			for(int j = 0; j<degree; j++){
				rects[i][j] = new RectF(0,0,0,0);
				para[i][j] = new RelativeLayout.LayoutParams(0,0);
				cells[i][j] = new TextView(context);
				cells[i][j].setTextColor(Color.BLACK);
				cells[i][j].setText("");
				cells[i][j].setTextSize(30);
				cells[i][j].setGravity(Gravity.CENTER);
				final int ci = i;
				final int cj = j;
				cells[i][j].addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						cells[ci][cj].setBackgroundResource(colormap.get(s.toString()));
					}
				});
				addView(cells[i][j]);
				
			}
		}
		
		//init colormap
		//{"夏","商","周","秦","汉","三国","晋","南北朝","隋","唐","五代","宋","辽","西夏","金","元","明","清","民国","共和国"};
		colormap = new HashMap<String,Integer>();
		colormap.put("夏", R.drawable.xia);
		colormap.put("商", R.drawable.shang);
		colormap.put("周", R.drawable.zhou);
		colormap.put("秦", R.drawable.qin);
		colormap.put("汉", R.drawable.han);
		colormap.put("三国", R.drawable.sanguo);
		colormap.put("晋", R.drawable.jin);
		colormap.put("南北朝", R.drawable.nanbei);
		colormap.put("隋", R.drawable.sui);
		colormap.put("唐", R.drawable.tang);
		colormap.put("五代", R.drawable.wudai);
		colormap.put("宋", R.drawable.song);
		colormap.put("辽", R.drawable.liao);
		colormap.put("西夏", R.drawable.xixia);
		colormap.put("金", R.drawable.jin1);
		colormap.put("元", R.drawable.yuan);
		colormap.put("明", R.drawable.ming);
		colormap.put("清", R.drawable.qing);
		colormap.put("民国", R.drawable.minguo);
		colormap.put("共和国", R.drawable.gonghe);
		colormap.put("", R.drawable.kong);
		
		//init painter
		painter = new Paint();
		
		//init board
		update();
		
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		painter.setColor(Color.BLACK);
		float r = rects[0][0].width()/10;
		canvas.drawRoundRect(bg, r, r, painter);
		painter.setColor(Color.DKGRAY);
		for(int i = 0; i<rects.length; i++){
			for(int j = 0; j<rects.length; j++){
				canvas.drawRoundRect(rects[i][j],r,r, painter);
			}
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int width = getMeasuredWidth();
		int degree = rects.length;
		int interval = (int)(width/degree*0.1);
		int cellsize = (int)((width-interval*(degree+1))/degree);
		
		for(int i = 0; i<degree; i++){
			for(int j = 0; j<degree; j++){
				int l,u;
				if(i == 0){
					u = interval;
				}else{
					u = (int)rects[i-1][j].bottom+interval;
				}
				
				if(j == 0){
					l = interval;
				}else{
					l = (int)rects[i][j-1].right+interval;
				}
				
				rects[i][j].left = l;
				rects[i][j].right = l+cellsize;
				rects[i][j].top = u;
				rects[i][j].bottom = u+cellsize;
				
				para[i][j].width = (int)cellsize;
				para[i][j].height = (int)cellsize;
				para[i][j].setMargins((int)rects[i][j].left, (int)rects[i][j].top, (int)rects[i][j].right, (int)rects[i][j].bottom);
				cells[i][j].setLayoutParams(para[i][j]);
			}
		}
		
		bg.left = 0;
		bg.right = width;
		bg.top = 0;
		bg.bottom = width;
		
		setMeasuredDimension(width, width);
	}
	
	private void update(){
		for(int i = 0; i<rects.length; i++){
			for(int j = 0; j<rects.length; j++){
				int n = game.getBlock(i, j);
				String s = game.getSymbol(n);
				if(s.length() == 1){
					cells[i][j].setTextSize(30);
				}else if(s.length() == 2){
					cells[i][j].setTextSize(25);
				}else {
					cells[i][j].setTextSize(20);
				}
				cells[i][j].setText(game.getSymbol(n));
			}
		}
		requestLayout();
	}
	
	public void init(){
		game.initial();
		update();
	}
	
	public int moveLeft(){
		int result = game.move("LEFT");
		update();
		return result;
	}
	
	public int moveRight(){
		int result = game.move("RIGHT");
		update();
		return result;	
	}
	
	public int moveUp(){
		int result = game.move("UP");
		update();
		return result;
	}
	
	public int moveDown(){
		int result = game.move("DOWN");
		update();
		return result;
	}
	
	public String getBest(){
		return game.getSymbol(game.getBestScore());
	}
}
