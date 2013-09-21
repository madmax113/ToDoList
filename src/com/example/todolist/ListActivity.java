package com.example.todolist;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;

public class ListActivity extends Activity {

	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();	
        itemsAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_checked, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    
    private void readItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try{
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	}
    	catch(IOException e){
    		items = new ArrayList<String>();    	
    		e.printStackTrace();
    	}  	
    }

    private void writeItems(){
      	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try{
    		FileUtils.writeLines(todoFile, items);
    		
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    private void setupListViewListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
    		@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
    			items.remove(arg2);
    			itemsAdapter.notifyDataSetChanged();
    			writeItems();
    			return true;
			}
    	
    	});
    	
    	lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				CheckedTextView textview = (CheckedTextView) arg1;
				if(textview.isChecked()){
					textview.setChecked(false);
					textview.setTextColor(Color.BLACK);
				}
				else
				{	textview.setChecked(true);
					textview.setTextColor(Color.GRAY);

				}
			}
		});
    
    }


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }
    
    public void onAdd(View v) {
    	String fieldValue = etNewItem.getText().toString();
    	itemsAdapter.add(fieldValue);
    	etNewItem.setText("");
    	writeItems();

    }
    
}
