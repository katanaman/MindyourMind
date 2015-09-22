package uk.ac.qub.mindyourmind.activities;
import java.util.Random;

import com.kristijandraca.backgroundmaillibrary.BackgroundMail;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class ResetPasswordActivity {

	public final String TAG = "resetPassword";

	String toAddress, message;
	String subject = "MindYourMind password reset request";
	String emailAccount = "MindYourMindPassReset@gmail.com";
	String emailAccountPasword = "MindYourMind15";
	int MAX_LENGTH = 6;
	Context context;

	public ResetPasswordActivity(){
	}
	public ResetPasswordActivity(Context context, String toAddress){
		this.context = context;
		this.toAddress = toAddress;
	}

	public String sendPasswordReset(){ 
		String newPassword = randomPassword();
		String message = "For the email address : \n\n"+ toAddress+"\n\nNew password :\n\n"+newPassword;
		
		Log.d(TAG, message);
		
		BackgroundMail bm = new BackgroundMail(context);
		bm.setGmailUserName(emailAccount);
		bm.setGmailPassword(emailAccountPasword);
		bm.setMailTo(toAddress);
		bm.setFormSubject(subject);
		bm.setFormBody(message);
		bm.setSendingMessageSuccess("Password reset has been sent");
		bm.setProcessVisibility(true);
		bm.send();
		
		return newPassword;
	}

	private static String randomPassword() {
		char[] validChars = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
		StringBuilder sb1 = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < 6; i++)
		{
			char c1 = validChars[rand.nextInt(validChars.length)];
			sb1.append(c1);
		}
		String random_string = sb1.toString(); 
		Log.d(random_string, "new Password : "+random_string);
		return random_string;
	}
}
