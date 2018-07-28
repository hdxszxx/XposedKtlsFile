package com.example.macfan.ktlsfile;

import android.app.Application;
import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by MacFan on 2018/7/27.
 */

public class Main implements IXposedHookLoadPackage{

    private String hookClass = "com.ktls.fileinfo.ku.ax";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                final ClassLoader cl= ((Context) param.args[0]).getClassLoader();
                Class<?> hookclass = null;
                try {
                    hookclass=cl.loadClass(hookClass);
                    if (hookclass!=null){
                        XposedHelpers.findAndHookMethod(hookclass, "a",int.class, new XC_MethodReplacement() {
                            @Override
                            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                                return true;
                            }

                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
