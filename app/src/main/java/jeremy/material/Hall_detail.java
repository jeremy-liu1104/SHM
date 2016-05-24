package jeremy.material;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Hall_detail extends ActionBarActivity {
    private TextView itemname;
    private TextView type;
    private TextView price;
    private TextView description;
    private TextView contact;
    private TextView email;
    private TextView address;
    private TextView date;
    private ItemDbAdapter mDb;
    private ImageView pic;
    private Button share;
    private Button back;
    private String username;
    private String from;
    String image1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hall_detail);
        Intent intent = getIntent();
        final String id = intent.getStringExtra("clickid");
        username = intent.getStringExtra("username");
        from = intent.getStringExtra("from");

        itemname = (TextView)findViewById(R.id.text_itemname_id2);
        date = (TextView)findViewById(R.id.textview_itemdate2);
        type = (TextView)findViewById(R.id.text_itemtype_id2);
        price = (TextView)findViewById(R.id.textview_itemprice2);
        description = (TextView)findViewById(R.id.textView_itemdescription2);
        contact = (TextView)findViewById(R.id.textview_itemcontact2);
        email = (TextView)findViewById(R.id.textview_itememail2);
        address = (TextView)findViewById(R.id.textview_itemaddress2);
        pic = (ImageView)findViewById(R.id.image_item2);


        share = (Button)findViewById(R.id.login_edit_btn_id3);
        back = (Button)findViewById(R.id.login_edit_btn_id4);
        readInfo(id);
        //itemname.setText(username+"\n"+from+"\n"+id);
        pic.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = LayoutInflater.from(Hall_detail.this);
                        View imgEntryView = inflater.inflate(R.layout.full_image, null); // 加载自定义的布局文件
                        final AlertDialog dialog = new AlertDialog.Builder(Hall_detail.this).create();
                        ImageView img = (ImageView)imgEntryView.findViewById(R.id.fullimage);
                        img.setBackground(Drawable.createFromPath(image1));
                        dialog.setView(imgEntryView); // 自定义dialog
                        dialog.show();
// 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
                        imgEntryView.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View paramView) {
                                dialog.cancel();
                            }
                        });
                    }
                }
        );
        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "Back", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );
        share.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!username.matches(from)){
                                            new AlertDialog.Builder(Hall_detail.this)
                    /* 弹出窗口的最上头文字 */
                        .setTitle("WARNING")
                    /* 设置弹出窗口的图式 */
                        .setIcon(android.R.drawable.ic_dialog_info)
                    /* 设置弹出窗口的信息 */
                        .setMessage("This stuff does not belong to you.Are you sure to share it to your friends?")
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialoginterface, int i) {

                                       //留给微信API分享
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialoginterface, int i) {
                                        // 什么也没做

                                    }
                                }).show();
                        }
                        else
                        //如果身份相同，就直接跳往分享界面
                            finish();
                    }
                }
        );
    }
    public  void readInfo(String id){
        mDb = new ItemDbAdapter(this);
        mDb.open();
        Cursor cursor = mDb.getAllNotesByid(id);
        while(cursor.moveToNext()){
            String name1 = cursor.getString(cursor.getColumnIndex("itemname"));
            String date1 = cursor.getString(cursor.getColumnIndex("date"));
            String type1 = cursor.getString(cursor.getColumnIndex("type"));
            String description1 = cursor.getString(cursor.getColumnIndex("description"));
            //String item1 = cursor.getString(cursor.getColumnIndex("item"));
            String price1 = cursor.getString(cursor.getColumnIndex("price"));
            String email1 = cursor.getString(cursor.getColumnIndex("email"));
            String contact1 = cursor.getString(cursor.getColumnIndex("contact"));
            String address1 = cursor.getString(cursor.getColumnIndex("address"));
            image1 = cursor.getString(cursor.getColumnIndex("image"));
            itemname.setText(name1);
            price.setText(price1);
            type.setText(type1);
            email.setText(email1);
            contact.setText(contact1);
            address.setText(address1);
            date.setText(date1);
            description.setText(description1);
            pic.setBackground(Drawable.createFromPath(image1));
        }
        cursor.close();
        mDb.closeclose();
    }
}
