package com.vikas.todoapp.activities;
import android.app.*;
import android.content.*;
import com.vikas.todoapp.Model.*;
import java.io.*;
/**import com.google.android.gms.ads.MobileAds;**/

public class AIDEApplication extends Application
{
	private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

	@Override
	public void onCreate() {
		/**MobileAds.initialize(this, "$appid$");**/
		MyViewModel.startWith(this);
		
		//TinyDancer.create().show(this);

		//alternatively
//		TinyDancer.create()
//			.redFlagPercentage(.1f) // set red indicator for 10%....different from default
//			.startingXPosition(200)
//			.startingYPosition(600)
//			.show(this);

		//you can add a callback to get frame times and the calculated
		//number of dropped frames within that window
//		TinyDancer.create()
//			.addFrameDataCallback(new FrameDataCallback() {
//				@Override
//				public void doFrame(long previousFrameNS, long currentFrameNS, int droppedFrames) {
//					//collect your stats here
//				}
//			})
//			.show(context);
			
		this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				Intent intent = new Intent(getApplicationContext(), DebugActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

				intent.putExtra("error", getStackTrace(ex));

				PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 11111, intent, PendingIntent.FLAG_ONE_SHOT);


				AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, pendingIntent);

				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(2);

				uncaughtExceptionHandler.uncaughtException(thread, ex);
			}
		});
		super.onCreate();

	}


	private String getStackTrace(Throwable th){
		Exception e = new Exception(th);
		StringWriter result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		while(th != null){
			th.printStackTrace(printWriter);
			th = th.getCause();
		}
		String r = result.toString();
		
		//Uncomment below lines to write logs to local storage when your app crashes
		//Make sure you request storage permissions on devices with API 23+
		
		
		//result = new StringWriter();
		//printWriter = new PrintWriter(result);
		//e.printStackTrace(printWriter);
		//String r2 = result.toString();
		//FileUtil.writeFile(FileUtil.getExternalStorageDir() + "/logcat.txt", r2.toString());
		
		return r;
	}
}
