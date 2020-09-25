package com.suek.ex37internalstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et= findViewById(R.id.et);
        tv= findViewById(R.id.tv);
    }



    //파일 저장하기
    public void clickSave(View view) {
        //파일에 저장할 데이터를 EditText 에서 얻어오기
        String data= et.getText().toString();   //et 아 getText()(ex;apple)를 해서 toString 문자열로 바꿔서 String data(TextView="Show Data") 에 넣어라.
                                                //그럼 그 저장된 데이터(apple)은 Device File Explor 에서 (data.txt)를 찾을 수 잇음
        et.setText("");   //EditText 의 빈문자를 줘서 글씨를 지워줌. 안그러면 EditText 에 apple 이라고 쓰고 Save 저장버튼을 눌러도
                          // 텍스트가 없어지지않고 사용자가 직접 지워줘야함..;;

        //액티비티에 내부메모리(Internal)에 File 을 저장할 수 있도록..
        //OutputStream 을 열 수 있는 기능메소드가 존재함.
        try {
            FileOutputStream fos= openFileOutput("data.txt",MODE_APPEND);  //append- 덧붙이다
            PrintWriter writer= new PrintWriter(fos);   //바이트 스트림을 문자스트림으로 변경 (fos. 대신에 PrintWriter 을 써줌)/ writer 는 문자단위
            writer.println(data);      //한줄에 하나
            writer.flush();
            writer.close();

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    //파일 가져오기- 읽어들여오기
    public void clickLoad(View view) {
        try {
            FileInputStream fis= openFileInput("data.txt");
            InputStreamReader isr= new InputStreamReader(fis);    //fis.reader() 라고 하면 바이트라서..ex)97.102.34..
            BufferedReader reader= new BufferedReader(isr);       //reader. 라고하면 'a','p' 이렇게 한글자씩 줘서 '한줄씩' 읽기(BufferedReader)로 써줌

            //String line= reader.readLine() + "\n";    //한줄씩 읽음

            StringBuffer buffer= new StringBuffer();  //문자열을 계속 누적시켜주는 StringBuffer

            while(true){
                String line= reader.readLine();
                if(line==null) break;
                buffer.append(line +"\n");
            }
            tv.setText(buffer.toString());     //누적되어있는 것을 문자열로


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
