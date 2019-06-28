package com.cn;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by fql on 2018/12/16.
 * timer 定时任务执行输出you win！输入S后停止。（包含新创建的）
 *
 *
 * schedule(TimerTask task, Date time)：安排在指定的时间执行指定的任务。
 *
 * schedule(TimerTask task, Date firstTime, long period) ：安排指定的任务在指定的时间开始进行重复的固定延迟执行。
 *
 * schedule(TimerTask task, long delay) ：安排在指定延迟后执行指定的任务。
 *
 * schedule(TimerTask task, long delay, long period) ：安排指定的任务从指定的延迟后开始进行重复的固定延迟执行。
 */

public class TimerTest{

    public static void main(String[] args){
        Timer timer = new Timer();
        timer.schedule(new MyTask(), 1000, 2000);//在1秒后执行此任务,每次间隔2秒执行一次,如果传递一个Data参数,就可以在某个固定的时间执行这个任务.
        while(true){//这个是用来停止此任务的,否则就一直循环执行此任务
            try{
                int in = System.in.read();
                if(in == 's'){
                    timer.cancel();//使用这个方法退出任务
                    break;
                }
            } catch (IOException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    static class MyTask extends java.util.TimerTask{
    public void run(){
        System.out.println("you win!!");
    }
}
}
