package com.wxue1.game2048;


import java.util.*;

import android.util.SparseArray;

public class GameEngine {
	private static GameEngine _instance = null;
	private int[][] board;
	private int[][] boardcopy;
	private int gridDegree = 4;
	private int targetNum = 2048;
	private int currentScore = 0;
	private int bestScore = 2;
	private int remain = 16;
	private SparseArray<String> map = new SparseArray<String>();
	
	private GameEngine(){
		board = new int[gridDegree][gridDegree];
		boardcopy = new int[gridDegree][gridDegree];
		remain = gridDegree*gridDegree;
		String[] dynasties = {"夏","商","周","秦","汉","三国","晋","南北朝","隋","唐","五代","宋","辽","西夏","金","元","明","清","民国","共和国"};
		int c = 2;
		for(int i = 0; i<dynasties.length;i++){
			map.put(c, dynasties[i]);
			c *= 2;
		}
		map.put(0, "");
		setLevel(dynasties.length);
		loadProfile();
	}
	
	private void loadProfile(){
		
	}
	
	public static synchronized GameEngine getInstance(){
		if(_instance == null){
			_instance = new GameEngine();
		}
		return _instance;
	}
	
	public void initial(){
		board = new int[gridDegree][gridDegree];
		boardcopy = new int[gridDegree][gridDegree];
		remain = gridDegree*gridDegree;
		for(int i = 0; i<gridDegree; i++){
			for(int j = 0; j<gridDegree; j++){
				board[i][j] = 0;
			}
		}
		addRandomBlock();
		currentScore = 0;
	}
	
	public int getCurrentScore(){
		return currentScore;
	}
	
	public int getBestScore(){
		return bestScore;
	}
	
	public String blockValue(int i, int j){
		return map.get(board[i][j]);
	}
	
	public void setDegree(int d){
		gridDegree = d;
	}
	
	public void setLevel(int l){
		targetNum = 1;
		for(int i = 0; i<l; i++){
			targetNum *= 2;
		}
	}
	
	
	public void moveLeft(){
		for(int i = 0; i<gridDegree; i++){
			boardcopy[i] = board[i].clone();
		}
		for(int i = 0; i<gridDegree;i++){
			int j = 0;
			int idx= 0;
			while(j<gridDegree){
				if(board[i][j] == 0){
					j++;
				}else{
					int next = j+1;
					
					while(next<gridDegree && board[i][next] == 0){
						next++;
					}
					
					if(next<gridDegree && board[i][j] == board[i][next]){
						board[i][idx] = 2*board[i][j];
						if(board[i][idx]>currentScore){
							currentScore = board[i][idx];
						}
						remain++;
						j = next+1;
					}else{
						board[i][idx] = board[i][j];
						j = next;
					}

					idx++;
				}
			}
			for(;idx<gridDegree;idx++){
				board[i][idx] = 0;
			}
		}
		if(!isSameBoard(board, boardcopy)){
			addRandomBlock();
		}
		if(currentScore>bestScore){
			bestScore = currentScore;
		}
	}
	
	public void moveRight(){
		for(int i = 0; i<gridDegree; i++){
			boardcopy[i] = board[i].clone();
		}
		for(int i = 0; i<gridDegree;i++){
			int j = gridDegree-1;
			int idx= gridDegree-1;
			while(j>=0){
				if(board[i][j] == 0){
					j--;
				}else{
					int next = j-1;
					
					while(next>=0 && board[i][next] == 0){
						next--;
					}
					
					if(next>=0 && board[i][j] == board[i][next]){
						board[i][idx] = 2*board[i][j];
						if(board[i][j]>currentScore){
							currentScore = board[i][j];
						}
						remain++;
						j = next-1;
					}else{
						board[i][idx] = board[i][j];
						j = next;
					}

					idx--;
				}
			}
			for(;idx>=0;idx--){
				board[i][idx] = 0;
			}
		}
		if(!isSameBoard(board, boardcopy)){
			addRandomBlock();
		}
		if(currentScore>bestScore){
			bestScore = currentScore;
		}
	}
	
	public void moveUp(){
		for(int i = 0; i<gridDegree; i++){
			boardcopy[i] = board[i].clone();
		}
		for(int i = 0; i<gridDegree;i++){
			int j = 0;
			int idx= 0;
			while(j<gridDegree){
				if(board[j][i] == 0){
					j++;
				}else{
					int next = j+1;
					
					while(next<gridDegree && board[next][i] == 0){
						next++;
					}
					
					if(next<gridDegree && board[j][i] == board[next][i]){
						board[idx][i] = 2*board[j][i];
						if(board[idx][i]>currentScore){
							currentScore = board[idx][i];
						}
						remain++;
						j = next+1;
					}else{
						board[idx][i] = board[j][i];
						j = next;
					}
					
					idx++;
				}
			}
			for(;idx<gridDegree;idx++){
				board[idx][i] = 0;
			}
		}
		if(!isSameBoard(board, boardcopy)){
			addRandomBlock();
		}
		if(currentScore>bestScore){
			bestScore = currentScore;
		}
	}
	
	public void moveDown(){
		for(int i = 0; i<gridDegree; i++){
			boardcopy[i] = board[i].clone();
		}
		for(int i = 0; i<gridDegree;i++){
			int j = gridDegree-1;
			int idx= gridDegree-1;
			while(j>=0){
				if(board[j][i] == 0){
					j--;
				}else{
					int next = j-1;
					
					while(next>=0 && board[next][i] == 0){
						next--;
					}
					
					if(next>=0 && board[j][i] == board[next][i]){
						board[idx][i] = 2*board[j][i];
						if(board[idx][i]>currentScore){
							currentScore = board[idx][i];
						}
						remain++;
						j = next-1;
					}else{
						board[idx][i] = board[j][i];
						j = next;
					}

					idx--;
				}
			}
			for(;idx>=0;idx--){
				board[idx][i] = 0;
			}
		}
		if(!isSameBoard(board, boardcopy)){
			addRandomBlock();
		}
		if(currentScore>bestScore){
			bestScore = currentScore;
		}
	}
	
	public void addRandomBlock(){
		Random r = new Random();
		int row1 = r.nextInt(gridDegree);
		int col1 = r.nextInt(gridDegree);
		
		while(remain>0 && board[row1][col1] != 0){
			row1 = r.nextInt(gridDegree);
			col1 = r.nextInt(gridDegree);
		}
		
		board[row1][col1] = 2;
		remain--;
	}
	
	public boolean checkAvaliable(){
		for(int i = 0; i<gridDegree; i++){
			for(int j = 0; j<gridDegree; j++){
				if(i>0 && board[i][j] == board[i-1][j]){
					return true;
				}
				if(j>0 && board[i][j] == board[i][j-1]){
					return true;
				}
			}
		}
		return false;
	}
	
	public String getSymbol(int n){
		return map.get(n);
	}
	
	public int getDegree(){
		return gridDegree;
	}
	
	public void printBoard(){
		for(int i = 0; i<board.length; i++){
			for(int j = 0; j<board.length; j++){
				System.out.print(board[i][j]+ " ");
			}
			System.out.println();
		}
	}
	
	private boolean isSameBoard(int[][] a, int[][] b){
		if(a.length != b.length){
			return false;
		}
		if(a[0].length != b[0].length){
			return false;
		}
		
		for(int i = 0; i<a.length; i++){
			for(int j = 0; j<a[0].length; j++){
				if(a[i][j] != b[i][j]){
					return false;
				}
			}
		}
		return true;
	}
	
	public int getBlock(int i, int j){
		return board[i][j];
	}
	
	public int move(String s){
		if(s.compareTo("LEFT") == 0){
			moveLeft();
		}else if(s.compareTo("RIGHT") == 0){
			moveRight();
		}else if(s.compareTo("UP") == 0){
			moveUp();
		}else if(s.compareTo("DOWN") == 0){
			moveDown();
		}
		
		if(currentScore == targetNum){
			return 1;
		}
		if(remain == 0){
			return checkAvaliable() ? 0:-1;
		}
		
		return 0;
	}
}
