package com.hjn.chapter3_mymessenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateMessageActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
    }
    /*
     * @version1.0 在一个应用内传递意图“intent”
    // Call onSendMessage() when the button is clicked
    public void onSendMessage(View view) {
        EditText messageView = (EditText) findViewById(R.id.message);
        String messageText = messageView.getText().toString(); // 忘记加上toString()方法，将editable类转为Stringl类
        // 创建一个显示意图
        Intent intent = new Intent(this, ReceiveMessageActivity.class);
        intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE, messageText);
        startActivity(intent);
    }
     */

    /*
     * @version1.1 在不同应用间传递意图“chosenIntent”
     */
    // Call onSendMessage() when the button is clicked
    public void onSendMessage(View view) {
        EditText messageView = (EditText) findViewById(R.id.message);
        String messageText = messageView.getText().toString(); // 忘记加上toString()方法，将editable类转为Stringl类

        // 创建隐式意图来指定一个动作（因为不知道要让Android具体运行哪个类）
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, messageText);

        // 得到选择器标题
        String chooserTitle = getString(R.string.chooser);
        // 显示一个选择器对话框（确保用户每次单击send Message按钮时都能选择活动）
        Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
        startActivity(chosenIntent);
    }
}
