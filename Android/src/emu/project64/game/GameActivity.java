/****************************************************************************
*                                                                           *
* Project 64 - A Nintendo 64 emulator.                                      *
* http://www.pj64-emu.com/                                                  *
* Copyright (C) 2012 Project64. All rights reserved.                        *
*                                                                           *
* License:                                                                  *
* GNU/GPLv2 http://www.gnu.org/licenses/gpl-2.0.html                        *
*                                                                           *
****************************************************************************/
package emu.project64.game;

import emu.project64.jni.NativeExports;
import emu.project64.jni.SystemEvent;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class GameActivity extends Activity
{
    private GameLifecycleHandler mLifecycleHandler;
    @SuppressWarnings("unused")
    private GameMenuHandler mMenuHandler;
    
    @Override
    public void onWindowFocusChanged( boolean hasFocus )
    {
        super.onWindowFocusChanged( hasFocus );
        mLifecycleHandler.onWindowFocusChanged( hasFocus );
    }
    
    @Override
    public void onBackPressed() 
    {
        NativeExports.ExternalEvent( SystemEvent.SysEvent_PauseCPU_AppLostActive.getValue());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirm Exit\n\nDo you want to quit the game?")
               .setPositiveButton("Quit Game", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       NativeExports.CloseSystem();
                       finish();
                   }
               })
               .setNegativeButton("Play On", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       NativeExports.ExternalEvent( SystemEvent.SysEvent_ResumeCPU_AppGainedActive.getValue());
                   }
               })
               .show();
    }
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {       
        mLifecycleHandler = new GameLifecycleHandler( this );
        mLifecycleHandler.onCreateBegin( savedInstanceState );
        super.onCreate( savedInstanceState );
        mLifecycleHandler.onCreateEnd( savedInstanceState );
        
        mMenuHandler = new GameMenuHandler( this, mLifecycleHandler );
    }
    
    @Override
    protected void onStart()
    {
        super.onStart();
        mLifecycleHandler.onStart();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        mLifecycleHandler.onResume();
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        mLifecycleHandler.onPause();
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
        mLifecycleHandler.onStop();
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mLifecycleHandler.onDestroy();
    }
}
